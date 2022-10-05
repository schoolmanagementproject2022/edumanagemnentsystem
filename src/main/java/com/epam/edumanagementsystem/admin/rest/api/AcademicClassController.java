package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
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

    private final TeacherService teacherService;

    @Autowired
    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseService academicCourseService, TeacherService teacherService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String openAcademicCourse(Model model) {
        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        for (AcademicClassDto academicClass:academicClassDtoList){
            if (academicClass.getClassNumber().contains("+")){
               String replace=academicClass.getClassNumber().replace("+"," ");
               academicClass.setClassNumber(replace);
               model.addAttribute("classes", academicClass);
            }
        }
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
        if (academicClass.getClassNumber().contains(" ")) {
            String replace = academicClass.getClassNumber().replace(" ", "+");
            academicClass.setClassNumber(replace);
        }

        for (AcademicClass aClass : academicClassList) {
            if (academicClass.getClassNumber().equals(aClass.getClassNumber())) {
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
        List<AcademicCourseDto> result = new ArrayList<>();
        List<Teacher> resultTeacher = new ArrayList<>();
        Set<Teacher> allTeachersInAcademicClass = academicClassService.findAllTeachers(name);
        model.addAttribute("teachers", allTeachersInAcademicClass);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());
        Set<AcademicCourse> allAcademicCoursesInAcademicClass = academicClassService.findAllAcademicCourses(name);
        List<AcademicCourseDto> allAcademicCourses = academicCourseService.findAll();
        model.addAttribute("courses", allAcademicCoursesInAcademicClass);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));

        if (allAcademicCoursesInAcademicClass.size() == 0 && allTeachersInAcademicClass.size() == 0) {
            result.addAll(allAcademicCourses);
            resultTeacher.addAll(allTeachers);
            model.addAttribute("teachersToSelect", resultTeacher);
            model.addAttribute("academicCoursesToSelect", result);
            return "academicCourseForAcademicClass";
        } else if (allAcademicCoursesInAcademicClass.size() == allAcademicCourses.size()
                && allTeachersInAcademicClass.size() == allTeachers.size()) {
            return "academicCourseForAcademicClass";
        } else {
            for (AcademicCourseDto academicCourse : allAcademicCourses) {
                if (!allAcademicCoursesInAcademicClass.contains(academicCourse)) {
                    result.add(academicCourse);
                }
            }
            for (Teacher teacher : allTeachers) {
                if (!allTeachersInAcademicClass.contains(teacher)) {
                    resultTeacher.add(teacher);
                }
            }
        }
        model.addAttribute("academicClass", new AcademicClass());
        model.addAttribute("teachersToSelect", resultTeacher);
        model.addAttribute("academicCoursesToSelect", result);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model) {
        academicClass.setClassNumber(name);
        List<AcademicCourseDto> result = new ArrayList<>();
        List<Teacher> resultTeacher = new ArrayList<>();
        Set<AcademicCourse> allAcademicCoursesInAcademicClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersInAcademicClass = academicClassService.findAllTeachers(name);
        model.addAttribute("courses", allAcademicCoursesInAcademicClass);
        model.addAttribute("teachers", allTeachersInAcademicClass);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());
        List<AcademicCourseDto> allAcademicCourses = academicCourseService.findAll();
        if (academicClass.getAcademicCourseSet().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allAcademicCoursesInAcademicClass.size() == 0) {
                result.addAll(allAcademicCourses);
                model.addAttribute("courseToSelect", result);
                return "academicCourseForAcademicClass";
            } else if (allAcademicCoursesInAcademicClass.size() == allAcademicCourses.size()) {
                return "academicCourseForAcademicClass";
            } else {
                for (AcademicCourseDto academicCourse : allAcademicCourses) {
                    if (!allAcademicCoursesInAcademicClass.contains(academicCourse)) {
                        result.add(academicCourse);
                    }
                }
            }
            model.addAttribute("courseToSelect", result);
            return "academicCourseForAcademicClass";
        }
        if (academicClass.getTeacher().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeachersInAcademicClass.size() == 0) {
                resultTeacher.addAll(allTeachers);
                model.addAttribute("teachersToSelect", resultTeacher);
                return "academicCourseForAcademicClass";
            } else if (allTeachersInAcademicClass.size() == allTeachers.size()) {
                return "academicCourseForAcademicClass";
            } else {
                for (Teacher teacher : allTeachers) {
                    if (!allTeachersInAcademicClass.contains(teacher)) {
                        resultTeacher.add(teacher);
                    }
                }
            }
            model.addAttribute("teachersToSelect", resultTeacher);
            return "academicCourseForAcademicClass";

        }
        academicClassService.update(academicClass);
        return "redirect:/classes/" + name + "/courses";

    }
    @GetMapping(value = "/teachersByCourse")
    @ResponseBody
    public Set<Teacher> getTeacher(@RequestParam String name) {
        AcademicCourse academicCourse = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        Set<Teacher> teacherSet = academicCourse.getTeacher();
        return teacherSet;
    }
}
