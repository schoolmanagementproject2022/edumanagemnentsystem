package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.InputFieldsValidation;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admins")
@Tag(name = "Admins")
public class AdminController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final AdminService adminService;
    private final UserService userService;

    public AdminController(PasswordEncoder bcryptPasswordEncoder,
                           AdminService adminService, UserService userService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Gets all admins and shows them on super admin`s dashboard")
    public String getAll(Model model) {
        setAttributesOfAdminSection(model);
        return "adminSection";
    }

    @PostMapping
    @Operation(summary = "Creates a new admin in popup")
    public String addAdmin(@ModelAttribute("admin") @Valid AdminDto adminDto,
                           BindingResult result, Model model) {
        validateFields(adminDto, model);
        validateEmailAndPassword(result, adminDto, model);
        String checked = checkIfValidationIsFailed(result, model);
        if (!checked.isBlank()) {
            return checked;
        }
        adminDto.setPassword(bcryptPasswordEncoder.encode(adminDto.getPassword()));
        adminService.save(adminDto);
        return "redirect:/admins";
    }

    private void validateFields(AdminDto adminDto, Model model) {
        model.addAttribute("admins", adminService.findAll());
        if (InputFieldsValidation.validateInputFieldSize(adminDto.getUsername())) {
            model.addAttribute("nameSize", "Symbols can't be more than 50");
        }
        if (InputFieldsValidation.validateInputFieldSize(adminDto.getSurname())) {
            model.addAttribute("surnameSize", "Symbols can't be more than 50");
        }
        if (InputFieldsValidation.validateInputFieldSize(adminDto.getEmail())) {
            model.addAttribute("emailSize", "Symbols can't be more than 50");
        }
    }

    private void validateEmailAndPassword(BindingResult result, AdminDto adminDto, Model model) {
        if (!result.hasFieldErrors("email") && !model.containsAttribute("emailSize")) {
            userService.checkDuplicationOfEmail(adminDto.getEmail(), model);
            if (UserDataValidation.validateEmail(adminDto.getEmail())) {
                model.addAttribute("invalidEmail", "Email is invalid");
            }
        }
        UserDataValidation.validatePassword(adminDto.getPassword(), model);
    }

    private void setAttributesOfAdminSection(Model model) {
        model.addAttribute("admin", new AdminDto());
        model.addAttribute("admins", adminService.findAll());
    }

    private String checkIfValidationIsFailed(BindingResult result, Model model) {
        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")
                || model.containsAttribute("emailSize")
                || model.containsAttribute("nameSize")
                || model.containsAttribute("surnameSize")) {
            return "adminSection";
        }
        return "";
    }

}
