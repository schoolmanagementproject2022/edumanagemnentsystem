package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getAll(ModelMap modelMap) {
        List<TeacherDto> teachers = teacherService.findAll();
        List<SubjectDto> all = subjectService.findAll();
        modelMap.addAttribute("subjects", all);
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("subject", new Subject());
        return "subjectSection";
    }

    @PostMapping
    public String createSubject(@ModelAttribute("subject") @Valid Subject subject,
                                BindingResult bindingResult, Model model) {
        List<SubjectDto> all = subjectService.findAll();
        model.addAttribute("subjects", all);
        List<TeacherDto> allTeacher = teacherService.findAll();
        model.addAttribute("teachers", allTeacher);

        Character[] list = {'!', '#', '@', '#', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};
        for (Character character : list) {
            if (subject.getName().contains(character.toString())) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
                return "subjectSection";
            }
        }

        for (SubjectDto subject1 : all) {
            if (subject1.getName().equalsIgnoreCase(subject.getName())) {
                model.addAttribute("duplicated", "A Subject with the same name already exists");
                return "subjectSection";
            }
        }
        if (bindingResult.hasErrors()) {
            return "subjectSection";
        }
        String decoded = URLDecoder.decode(subject.getName(), StandardCharsets.UTF_8);
        subject.setName(decoded);
        subjectService.create(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{name}/teachers")
    public String openSubjectForTeacherCreation(@PathVariable("name") String name, Model model) {

        Set<Teacher> result = new HashSet<>();
        Set<Teacher> allTeachersInSubject = subjectService.findAllTeachers(name);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());
        model.addAttribute("teachers", allTeachersInSubject);
        model.addAttribute("existingSubject", subjectService.findSubjectBySubjectName(name));

        if (allTeachersInSubject.size() == 0) {
            result.addAll(allTeachers);
            model.addAttribute("teachersToSelect", result);
            return "subjectSectionForTeachers";
        } else if (allTeachersInSubject.size() == allTeachers.size()) {
            return "subjectSectionForTeachers";
        } else {
            for (Teacher teacher : allTeachers) {
                if (!allTeachersInSubject.contains(teacher)) {
                    result.add(teacher);
                }
            }
        }
        model.addAttribute("teachersToSelect", result);
        return "subjectSectionForTeachers";
    }

    @PostMapping("{name}/teachers")
    public String addNewTeacher(@ModelAttribute("existingSubject") Subject subject,
                                @PathVariable("name") String name, Model model) {

        Set<Teacher> result = new HashSet<>();
        Set<Teacher> allTeachersInSubject = subjectService.findAllTeachers(name);
        model.addAttribute("teachers", allTeachersInSubject);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());

        if (subject.getTeacherSet().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeachersInSubject.size() == 0) {
                result.addAll(allTeachers);
                model.addAttribute("teachersToSelect", result);
                return "subjectSectionForTeachers";
            } else if (allTeachersInSubject.size() == allTeachers.size()) {
                return "subjectSectionForTeachers";
            } else {
                for (Teacher teacher : allTeachers) {
                    if (!allTeachersInSubject.contains(teacher)) {
                        result.add(teacher);
                    }
                }
            }
            model.addAttribute("teachersToSelect", result);
            return "subjectSectionForTeachers";
        }
        subjectService.update(subject);
        return "redirect:/subjects/" + name + "/teachers";
    }
}