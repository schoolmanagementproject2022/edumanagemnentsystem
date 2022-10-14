package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/classes")
public class AcademicClassController {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;


    @Autowired
    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseService academicCourseService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
    }

    @GetMapping
    public String openAcademicCourse(Model model) {
        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        model.addAttribute("academicClasses", academicClassDtoList);

        model.addAttribute("academicClass", new AcademicClassDto());

        return "academicClassSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicClass") @Valid AcademicClass academicClass,
                         BindingResult result, Model model) {

        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        List<AcademicClass> academicClassList = AcademicClassMapper.academicClassessList(academicClassDtoList);
        model.addAttribute("academicClasses", academicClassDtoList);
        Character[] list = {'!', '#', '@', '#', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};
        for (Character character : list) {
            if (academicClass.getClassNumber().contains(character.toString())) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
                return "academicClassSection";
            }
        }

        for (AcademicClass existingListOfAcademicClass : academicClassList) {
            if (academicClass.getClassNumber().equals(existingListOfAcademicClass.getClassNumber())) {
                model.addAttribute("duplicated", "Class already exists");
                return "academicClassSection";
            }
        }
        if (result.hasErrors()) {
            return "academicClassSection";
        } else {
            String decoded = URLDecoder.decode(academicClass.getClassNumber(), StandardCharsets.UTF_8);
            academicClass.setClassNumber(decoded);
            academicClassService.create(academicClass);
            return "redirect:/classes";
        }
    }

    @GetMapping("/{name}/courses")
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model) {
        List<AcademicCourse> result = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("academicCourseSet", academicCoursesInClass);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingClass", new AcademicClass());
        model.addAttribute("allCourses", allCourses);
        if (academicCoursesInClass.size() == 0) {
            for (AcademicCourse course : allCourses) {
                if (course.getTeacher().size() > 0) {
                    result.add(course);
                }
            }
            model.addAttribute("coursesForSelect", result);
            return "academicCourseForAcademicClass";
        } else if (academicCoursesInClass.size() == allCourses.size()) {
            return "academicCourseForAcademicClass";
        } else {
            for (AcademicCourse course : allCourses) {
                if (!academicCoursesInClass.contains(course)) {
                    if (course.getTeacher().size() > 0) {
                        result.add(course);
                    }
                }
            }
        }
        model.addAttribute("coursesForSelect", result);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model) {
        List<AcademicCourse> result = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingClass", new AcademicClass());
        for (AcademicCourse course : allCourses) {
            if (!academicCoursesInClass.contains(course)) {
                if (course.getTeacher().size() > 0)
                    result.add(course);
            }
        }

        model.addAttribute("coursesForSelect", result);
        model.addAttribute("academicCourseSet", academicCoursesInClass);

        if (academicClass.getAcademicCourseSet().size() == 0 && academicClass.getTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            model.addAttribute("blankClass", "Please, select the required fields");
            return "academicCourseForAcademicClass";

        } else if (academicClass.getAcademicCourseSet().size() == 0) {
            model.addAttribute("blankClass", "Please, select the required fields");
            return "academicCourseForAcademicClass";
        } else if (academicClass.getTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            return "academicCourseForAcademicClass";
        } else {
            AcademicClass findedClass = academicClassService.findByName(name);
            findedClass.getAcademicCourseSet().addAll(academicClass.getAcademicCourseSet());
            findedClass.getTeacher().addAll(academicClass.getTeacher());
            academicClassService.update(findedClass);
            return "redirect:/classes/" + name + "/courses";
        }
    }

    @GetMapping("/{name}/classroom")
    public String classroomTeacherForAcademicClass(@PathVariable("name") String name, Model model) {
        AcademicClass academicClass = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClass.getTeacher());
        model.addAttribute("existingClassroomTeacher", new AcademicClass());
        if (academicClass.getClassroomTeacher() == null) {
            return "classroomTeacherSection";
        } else {
            model.addAttribute("classroomTeacher", academicClass.getClassroomTeacher());
            return "classroomTeacherSection";
        }
    }
    @PostMapping("{name}/classroom")
    public String addClassroomTeacherInAcademicClass(@ModelAttribute("existingClassroomTeacher") AcademicClass academicClass,
                                                     @PathVariable("name") String name,
                                                     Model model) {
        AcademicClass academicClassFindByName = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClassFindByName.getTeacher());
        model.addAttribute("existingClass", new AcademicClass());
        if (academicClass.getClassroomTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            return "classroomTeacherSection";
        }
        for (AcademicClassDto academicClassDto : academicClassService.findAll()) {
            if (academicClass.getClassroomTeacher()
                    .equals(academicClassDto.getClassroomTeacher())){
                model.addAttribute("duplicate", "This Teacher is already classroom teacher");
                return "classroomTeacherSection";
            }
        }

        academicClassFindByName.setClassroomTeacher(academicClass.getClassroomTeacher());
        academicClassService.update(academicClassFindByName);
        return "redirect:/classes/" + name + "/classroom";
    }

    @GetMapping("/{name}/teachers")
    public String teachersForAcademicClass(Model model, @PathVariable("name") String name) {
        AcademicClass academicClass = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClass.getTeacher());
        Set<Teacher> allTeachersByAcademicClass = academicClassService.findAllTeacher();
        model.addAttribute("allTeacherByAcademicClass", allTeachersByAcademicClass);

        return "teachersForAcademicClass";
    }

}