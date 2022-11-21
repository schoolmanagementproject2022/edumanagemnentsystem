package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/subjects")
@Tag(name = "Subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Gets the list of the subjects and shows them on admin's dashboard")
    public String getAll(ModelMap modelMap) {
        List<TeacherDto> teachers = teacherService.findAll();
        List<SubjectDto> all = subjectService.findAll();
        modelMap.addAttribute("subjects", all);
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("subject", new Subject());
        return "subjectSection";
    }

    @PostMapping
    @Operation(summary = "Creates a new subject in popup and saves it in DB")
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
    @Operation(summary = "Gets concrete subject page with their teachers list")
    public String openSubjectForTeacherCreation(@PathVariable("name") String name, Model model) {

        Set<TeacherDto> teachersToSelect = new LinkedHashSet<>();
        Set<TeacherDto> allTeachersInSubject = subjectService.findAllTeachers(name);
        List<TeacherDto> allTeachers = teacherService.findAll();
        model.addAttribute("teachers", allTeachersInSubject);
        model.addAttribute("existingSubject", subjectService.findSubjectBySubjectName(name));

        if (allTeachersInSubject.size() == 0) {
            teachersToSelect.addAll(allTeachers);
            model.addAttribute("teachersToSelect", teachersToSelect);
            return "subjectSectionForTeachers";
        } else if (allTeachersInSubject.size() == allTeachers.size()) {
            return "subjectSectionForTeachers";
        } else {
            for (TeacherDto teacher : allTeachers) {
                if (!allTeachersInSubject.contains(teacher)) {
                    teachersToSelect.add(teacher);
                }
            }
        }
        model.addAttribute("teachersToSelect", teachersToSelect);
        return "subjectSectionForTeachers";
    }

    @PostMapping("{name}/teachers")
    @Operation(summary = "Updates the list of teachers for a concrete subject")
    public String addNewTeacher(@ModelAttribute("existingSubject") Subject subject,
                                @PathVariable("name") String name, Model model) {

        Set<TeacherDto> teachersToSelect = new LinkedHashSet<>();
        Set<TeacherDto> allTeachersInSubject = subjectService.findAllTeachers(name);
        model.addAttribute("teachers", allTeachersInSubject);
        List<TeacherDto> allTeachers = teacherService.findAll();

        if (subject.getTeacherSet().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeachersInSubject.size() == 0) {
                teachersToSelect.addAll(allTeachers);
                model.addAttribute("teachersToSelect", teachersToSelect);
                return "subjectSectionForTeachers";
            } else if (allTeachersInSubject.size() == allTeachers.size()) {
                return "subjectSectionForTeachers";
            } else {
                for (TeacherDto teacher : allTeachers) {
                    if (!allTeachersInSubject.contains(teacher)) {
                        teachersToSelect.add(teacher);
                    }
                }
            }
            model.addAttribute("teachersToSelect", teachersToSelect);
            return "subjectSectionForTeachers";
        }
        subjectService.update(subject);
        return "redirect:/subjects/" + name + "/teachers";
    }
}