package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.InputFieldsValidation;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/parents")
@MultipartConfig(maxFileSize = 2 * 2048 * 2048, maxRequestSize = 2 * 2048 * 2048)
@ControllerAdvice
public class ParentController {
    private static final String INPUT_LENGTH_MESSAGE = "Symbols can't be more than 50";
    private static final String INVALID_EMAIL = "invalidEmail";
    private static final String NAME_SIZE = "nameSize";
    private static final String SURNAME_SIZE = "surnameSize";
    private static final String EMAIL_SIZE = "emailSize";
    private static final String REDIRECT_URL = "redirect:/parents/";
    private static final String PROFILE_URL = "/profile";


    private final PasswordEncoder bcryptPasswordEncoder;
    private final ParentService parentService;
    private final UserService userService;
    private final ImageService imageService;
    private final StudentService studentService;


    @Autowired
    public ParentController(PasswordEncoder bcryptPasswordEncoder, ParentService parentService,
                            UserService userService, ImageService imageService, StudentService studentService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.parentService = parentService;
        this.userService = userService;
        this.imageService = imageService;
        this.studentService = studentService;
    }

    @GetMapping()
    @Operation(summary = "Gets the list of parents and shows on admin's dashboard")
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.findAll());
        modelMap.addAttribute("parent", new ParentDto());
        modelMap.addAttribute("user", new User());
        return "parentSection";
    }

    @PostMapping()
    @Operation(summary = "Creates a new parent and saves in DB")
    public String saveParent(@Valid @ModelAttribute(value = "parent") ParentDto parentDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "picture", required = false) MultipartFile multipartFile,
                             @RequestParam(value = "status", required = false) String status,
                             Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            UserDataValidation.validateImage(multipartFile, model);
        }
        if (status.equals("validationFail")) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
        }

        model.addAttribute("parents", parentService.findAll());
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getName())) {
            model.addAttribute(NAME_SIZE, INPUT_LENGTH_MESSAGE);
        }
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getSurname())) {
            model.addAttribute(SURNAME_SIZE, INPUT_LENGTH_MESSAGE);
        }
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getEmail())) {
            model.addAttribute(EMAIL_SIZE, INPUT_LENGTH_MESSAGE);
        }

        if (!bindingResult.hasFieldErrors("email") && !model.containsAttribute(EMAIL_SIZE)) {
            userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
            if (UserDataValidation.validateEmail(parentDto.getEmail())) {
                model.addAttribute(INVALID_EMAIL, "Email is invalid");
            }
        }
        UserDataValidation.validatePassword(parentDto.getPassword(), model);
        if (bindingResult.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute(INVALID_EMAIL)
                || model.containsAttribute("duplicated") || model.containsAttribute(EMAIL_SIZE)
                || model.containsAttribute(NAME_SIZE)
                || model.containsAttribute(SURNAME_SIZE)
                || model.containsAttribute("size")
                || model.containsAttribute("formatValidationMessage")) {

            return "parentSection";
        }
        parentDto.setPassword(bcryptPasswordEncoder.encode(parentDto.getPassword()));
        Parent parent = parentService.save(parentDto);

        if (!multipartFile.isEmpty()) {
            parentService.addImage(parent, multipartFile);
        }
        return "redirect:/parents";
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "Shows selected parent's profile")
    public String openParentProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("parentDto", ParentMapper.toParentDto(parentService.findById(id).get()));
        model.addAttribute("parentData", parentService.findById(id).get().getFullName());
        return "parentProfile";
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Shows linked students of selected parent")
    public String openLinkedStudentForParent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("students", studentService.findStudentsByParentId(id));
        model.addAttribute("parent", parentService.findById(id));
        return "parentSectionForStudents";
    }

    @PostMapping("/{id}/profile")
    @Operation(summary = "Edits selected parent's profile")
    public String editParent(@Valid @ModelAttribute("parentDto") ParentDto parentDto, BindingResult bindingResult,
                             @PathVariable("id") Long id, Model model) {
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getName())) {
            model.addAttribute(NAME_SIZE, INPUT_LENGTH_MESSAGE);
        }
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getSurname())) {
            model.addAttribute(SURNAME_SIZE, INPUT_LENGTH_MESSAGE);
        }
        if (InputFieldsValidation.validateInputFieldSize(parentDto.getEmail())) {
            model.addAttribute(EMAIL_SIZE, INPUT_LENGTH_MESSAGE);
        }

        if (!bindingResult.hasFieldErrors("email") && !model.containsAttribute(EMAIL_SIZE)) {
            if (!parentDto.getEmail().equals(parentService.findById(id).get().getUser().getEmail())) {
                userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
            }
            if (UserDataValidation.validateEmail(parentDto.getEmail())) {
                model.addAttribute(INVALID_EMAIL, "Email is invalid");
            }
        }

        if (bindingResult.hasErrors() || model.containsAttribute(INVALID_EMAIL)
                || model.containsAttribute("duplicated") || model.containsAttribute(EMAIL_SIZE)
                || model.containsAttribute(NAME_SIZE) || model.containsAttribute(SURNAME_SIZE)) {
            model.addAttribute("parentData", parentService.findById(id).get().getFullName());
            return "parentProfile";
        }
        parentService.update(parentDto);
        return REDIRECT_URL + id + PROFILE_URL;
    }

    @PostMapping("/{id}/image/add")
    @Operation(summary = "Adds image to selected parent's profile")
    public String addImage(@PathVariable("id") Long id, @RequestParam("picture") MultipartFile multipartFile) {
        parentService.addImage(parentService.findById(id).get(), multipartFile);
        return REDIRECT_URL + id + PROFILE_URL;
    }

    @GetMapping("/{id}/image/delete")
    @Operation(summary = "Deletes image from selected parent's profile")
    public String deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(parentService.findById(id).get().getPicUrl());
        parentService.removeImage(id);
        return REDIRECT_URL + id + PROFILE_URL;
    }

}
