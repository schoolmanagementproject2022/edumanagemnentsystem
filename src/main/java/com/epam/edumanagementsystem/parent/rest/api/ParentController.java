package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.config.MessageByLang;
import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.dto.ParentEditDto;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.util.AppConstants;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/parents")
@Tag(name = "Parents")
@MultipartConfig(maxFileSize = AppConstants.MAX_FILE_SIZE,
        maxRequestSize = AppConstants.MAX_REQUEST_SIZE)
public class ParentController {

    private static final String PARENT_SECTION_HTML = "parentSection";
    private static final String PARENT_PROFILE_HTML = "parentProfile";
    private static final String REDIRECT_TO_PARENTS = "redirect:/parents/";
    private static final String PROFILE_URL = "/profile";
    private static final String PARENT_SECTION_FOR_STUDENTS_HTML = "parentSectionForStudents";
    private static final String PARENT = "parent";
    private static final String PARENT_DATA = "parentData";

    private final ParentService parentService;
    private final ImageService imageService;
    private final StudentService studentService;


    @Autowired
    public ParentController(ParentService parentService, ImageService imageService,
                            StudentService studentService) {
        this.parentService = parentService;
        this.imageService = imageService;
        this.studentService = studentService;
    }

    @GetMapping()
    @Operation(summary = "Gets the list of parents and shows on admin's dashboard")
    public String openParentSection(Model model) {
        model.addAttribute("parents", parentService.findAll());
        model.addAttribute(PARENT, new ParentDto());
        return PARENT_SECTION_HTML;
    }

    @PostMapping()
    @Operation(summary = "Creates a new parent and saves in DB")
    public String saveParent(@Valid @ModelAttribute(value = "parent") ParentDto parentDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "picture", required = false) MultipartFile multipartFile,
                             @RequestParam(value = "status", required = false) String status,
                             Model model) throws IOException {

        model.addAttribute("parents", parentService.findAll());
        imageService.checkMultipartFile(multipartFile, status, model);
        checkEmailForCreate(parentDto, bindingResult, model);

        if (bindingResult.hasErrors()) {
            return PARENT_SECTION_HTML;
        }
        ParentDto savedParentDto = parentService.save(parentDto);
        if (!multipartFile.isEmpty()) {
            parentService.addImage(savedParentDto, multipartFile);
        }
        return REDIRECT_TO_PARENTS;
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "Shows selected parent's profile")
    public String openParentProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute(PARENT, parentService.findParentEditById(id));
        model.addAttribute(PARENT_DATA, parentService.findById(id).getFullName());
        return PARENT_PROFILE_HTML;
    }

    @PostMapping("/{id}/profile")
    @Operation(summary = "Edits selected parent's profile")
    public String editParent(@ModelAttribute("parent") @Valid ParentEditDto parentDto,
                             BindingResult bindingResult, @PathVariable("id") Long id, Model model) {

        checkEmailForEdit(parentDto, bindingResult, id, model);

        if (bindingResult.hasErrors()) {
            model.addAttribute(PARENT_DATA, parentService.findById(id).getFullName());
            return PARENT_PROFILE_HTML;
        }
        parentService.update(parentDto);
        return REDIRECT_TO_PARENTS + id + PROFILE_URL;
    }

    @PostMapping("/{id}/image/add")
    @Operation(summary = "Adds image to selected parent's profile")
    public String addImage(@PathVariable("id") Long id, @RequestParam("picture") MultipartFile multipartFile) {
        if (parentService.existsParentById(id)) {
            ParentDto parent = parentService.findById(id);
            if (parent.getPicUrl() != null) {
                imageService.deleteImage(parent.getPicUrl());
                parentService.addImage(parent, multipartFile);
            } else {
                parentService.addImage(parent, multipartFile);
            }
        }
        return REDIRECT_TO_PARENTS + id + PROFILE_URL;
    }

    @GetMapping("/{id}/image/delete")
    @Operation(summary = "Deletes image from selected parent's profile")
    public String deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(parentService.findById(id).getPicUrl());
        parentService.removeImage(id);
        return REDIRECT_TO_PARENTS + id + PROFILE_URL;
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Shows linked students of selected parent")
    public String openLinkedStudentForParent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("students", studentService.findStudentsByParentId(id));
        model.addAttribute(PARENT, parentService.findById(id));
        return PARENT_SECTION_FOR_STUDENTS_HTML;
    }

    private void checkEmailForCreate(ParentDto parentDto, BindingResult bindingResult, Model model) {
        if (UserDataValidation.existsEmail(parentDto.getEmail())) {
            bindingResult.addError(new ObjectError(PARENT, "Duplicate email"));
            model.addAttribute(AppConstants.DUPLICATED, MessageByLang.getMessage("USER_WITH_EMAIL_EXISTS"));
        }
    }

    private void checkEmailForEdit(ParentEditDto parentDto, BindingResult bindingResult,
                                   Long id, Model model) {
        if (!parentDto.getEmail().equalsIgnoreCase(parentService.findById(id).getEmail()) &&
                UserDataValidation.existsEmail(parentDto.getEmail())) {
            bindingResult.addError(new ObjectError(PARENT, "Duplicate email"));
            model.addAttribute(AppConstants.DUPLICATED, MessageByLang.getMessage("USER_WITH_EMAIL_EXISTS"));
        }
    }

}
