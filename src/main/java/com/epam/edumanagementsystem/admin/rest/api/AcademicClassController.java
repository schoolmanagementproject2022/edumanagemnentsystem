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
        List<AcademicCourseDto> academicCourseSet = academicCourseService.findAll();
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        model.addAttribute("academicCourseSet", academicCourseSet);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));

        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model) {
        List<AcademicCourseDto> result = new ArrayList<>();
        List<Teacher> resultTeacher = new ArrayList<>();
        List<AcademicCourseDto> academicCourseSet = academicCourseService.findAll();
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        Set<AcademicCourse> allAcademicCoursesInAcademicClass = academicClassService.findAllAcademicCourses(name);

        model.addAttribute("academicCourseSet", academicCourseSet);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));
        academicClass.setClassNumber(name);
        if (academicClass.getAcademicCourseSet().size() == 0) {
            model.addAttribute("blank", "there is no selection");
            return "academicCourseForAcademicClass";
        }
        if (academicClass.getTeacher().size() == 0) {
            model.addAttribute("blank", "there is no selection");
            return "academicCourseForAcademicClass";
        }
        if (allAcademicCoursesInAcademicClass.size() == 0) {
            result.addAll(academicCourseSet);
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
