package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
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
import java.util.HashSet;
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
            String replace = academicClass.getClassNumber().replace(" ", "");
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
        Set<AcademicCourse> result = new HashSet<>();
        Set<Teacher> resultTeacher = new HashSet<>();
        List<AcademicCourse> academicCourseInAcademicClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> teacherInAcademicClass = academicClassService.findAllTeachers(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allAcademicCourse = academicCourseService.findAllCourse();
        model.addAttribute("academicCourseSet", academicCourseInAcademicClass);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));
        if (academicCourseInAcademicClass.size() == 0 && teacherInAcademicClass.size() == 0) {
            resultTeacher.addAll(allTeachersByAcademicCourse);
            result.addAll(allAcademicCourse);
            model.addAttribute("teacherToSelect", resultTeacher);
            model.addAttribute("academicCourseToSelect", result);
            return "academicCourseForAcademicClass";
        } else if (academicCourseInAcademicClass.size() == allAcademicCourse.size() && teacherInAcademicClass.size() == allTeachersByAcademicCourse.size()) {
            return "academicCourseForAcademicClass";
        } else {
            for (AcademicCourse academicCourse : allAcademicCourse) {
                if (!academicCourseInAcademicClass.contains(academicCourse)) {
                    result.add(academicCourse);
                }
            }
            for (Teacher teacher : allTeachersByAcademicCourse) {
                if (!teacherInAcademicClass.contains(teacher)) {
                    resultTeacher.add(teacher);
                }
            }
        }
        model.addAttribute("academicCourseToSelect", result);

        model.addAttribute("teachersToSelect", resultTeacher);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model) {
       academicClass.setClassNumber(name);
        List<AcademicCourse> result = new ArrayList<>();
        List<Teacher> resultTeacher = new ArrayList<>();
        List<AcademicCourse> academicCourseInAcademicClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> teacherInAcademicClass = academicClassService.findAllTeachers(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allAcademicCourse = academicCourseService.findAllCourse();
        model.addAttribute("academicCourseSet", academicCourseInAcademicClass);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));


        if (academicClass.getAcademicCourseSet().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (academicCourseInAcademicClass.size() == 0 && teacherInAcademicClass.size() == 0) {
                resultTeacher.addAll(allTeachersByAcademicCourse);
                result.addAll(allAcademicCourse);
                model.addAttribute("teacherToSelect", resultTeacher);
                model.addAttribute("academicCourseToSelect", result);
                return "academicCourseForAcademicClass";
            } else if (academicCourseInAcademicClass.size() == allAcademicCourse.size() && teacherInAcademicClass.size() == allTeachersByAcademicCourse.size()) {
                return "academicCourseForAcademicClass";
            } else {
                for (AcademicCourse academicCourse : allAcademicCourse) {
                    if (!academicCourseInAcademicClass.contains(academicCourse)) {
                        result.add(academicCourse);
                    }
                }
                for (Teacher teacher : allTeachersByAcademicCourse) {
                    if (!teacherInAcademicClass.contains(teacher)) {
                        resultTeacher.add(teacher);
                    }
                }
            }
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
