package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String getAll(ModelMap modelMap) {
        List<TeacherDto> teachers = teacherService.findAll();
        List<Subject> all = subjectService.findAll();
        modelMap.addAttribute("subjects", all);
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("subject", new Subject());
        return "subjectSection";
    }

    @PostMapping
    public String createSubject(@ModelAttribute("subject") Subject subject, Model model) {
        List<Subject> all = subjectService.findAll();
        model.addAttribute("subjects", all);
        for (Subject subject1 : all) {
            if (subject1.getName().equals(subject.getName())) {
                model.addAttribute("duplicated", "A Subject with the same name already exists");
                return "subjectSection";
            }
        }
            subjectService.create(subject);
        return "redirect:/subjects";
    }
}
