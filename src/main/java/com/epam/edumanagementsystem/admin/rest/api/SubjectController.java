package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.InputFieldsValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Operation(summary = "Gets the list of the subjects and shows them on admin`s dashboard")
    public String getAll(Model model) {
        setAttributesOfSubjectSection(model);
        return "subjectSection";
    }

    @PostMapping
    @Operation(summary = "Creates a new subject in popup and saves it in DB")
    public String createSubject(@ModelAttribute("subject") @Valid SubjectDto subjectDto,
                                BindingResult result, Model model) {
        setAttributesOfSubjectSection(model);

        if(result.hasErrors() || model.containsAttribute("nameSize") || model.containsAttribute("invalidURL")){
            return "subjectSection";
        }

        subjectService.checkSubjectDuplication(subjectDto, result, model);

        if (result.hasErrors()) {
            return "subjectSection";
        }
        subjectService.save(subjectDto);
        return "redirect:/subjects";
    }

    @GetMapping("/{name}/teachers")
    @Operation(summary = "Gets concrete subject page with their teachers list")
    public String openSubjectForTeacherCreation(@PathVariable("name") String subjectName, Model model) {

        Set<TeacherDto> teachersToSelect;
        Set<TeacherDto> allTeachersInSubject = subjectService.findAllTeachersInSubjectByName(subjectName);
        List<TeacherDto> allTeachers = teacherService.findAll();
        model.addAttribute("teachers", allTeachersInSubject);
        model.addAttribute("existingSubject", subjectService.findByName(subjectName));

        if (allTeachersInSubject.isEmpty()) {
            teachersToSelect = new LinkedHashSet<>(allTeachers);
            model.addAttribute("teachersToSelect", teachersToSelect);
            return "subjectSectionForTeachers";
        } else if (allTeachersInSubject.size() == allTeachers.size()) {
            return "subjectSectionForTeachers";
        } else {
            teachersToSelect = allTeachers.stream().filter(teacher -> !allTeachersInSubject.contains(teacher))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        model.addAttribute("teachersToSelect", teachersToSelect);
        return "subjectSectionForTeachers";
    }

    @PostMapping("{name}/teachers")
    @Operation(summary = "Updates the list of teachers for a concrete subject")
    public String addNewTeacher(@ModelAttribute("existingSubject") SubjectDto subjectDto,
                                @PathVariable("name") String name, Model model) {

        Set<TeacherDto> teachersToSelect;
        Set<TeacherDto> allTeachersInSubject = subjectService.findAllTeachersInSubjectByName(name);
        model.addAttribute("teachers", allTeachersInSubject);
        List<TeacherDto> allTeachers = teacherService.findAll();

        if (subjectDto.getTeacherSet().isEmpty()) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeachersInSubject.isEmpty()) {
                teachersToSelect = new LinkedHashSet<>(allTeachers);
                model.addAttribute("teachersToSelect", teachersToSelect);
                return "subjectSectionForTeachers";
            } else if (allTeachersInSubject.size() == allTeachers.size()) {
                return "subjectSectionForTeachers";
            } else {
                teachersToSelect = allTeachers.stream().filter(teacher -> !allTeachersInSubject
                        .contains(teacher)).collect(Collectors.toCollection(LinkedHashSet::new));
            }
            model.addAttribute("teachersToSelect", teachersToSelect);
            return "subjectSectionForTeachers";
        }
        subjectService.update(subjectDto);
        return "redirect:/subjects/" + name + "/teachers";
    }

    private void setAttributesOfSubjectSection(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("subject", new Subject());
    }


}