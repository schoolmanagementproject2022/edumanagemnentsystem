package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherSectionController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherSectionController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String openTeacherSection(Model model) {
        List<TeacherDto> all = teacherService.findAll();
        model.addAttribute("teachers", all);
        model.addAttribute("teacher", new TeacherDto());
        return "teacherSection";
    }

    @PostMapping
    public String createTeacher(@ModelAttribute("teacher") @Valid Teacher teacher,
                                BindingResult result, Model model) {
        List<TeacherDto> allTeachersDto = teacherService.findAll();
        model.addAttribute("teachers", allTeachersDto);

        List<Teacher> teachers = TeacherMapper.toListOfTeachers(allTeachersDto);
        for (Teacher teach : teachers) {
            if (teach.getEmail().equals(teacher.getEmail())) {
                model.addAttribute("duplicated", "A user with the specified email already exists");
                return "teacherSection";
            }
        }

        if (result.hasErrors()) {
            return "teacherSection";
        } else {
            teacherService.create(teacher);
            return "redirect:/teachers";
        }
    }
}
