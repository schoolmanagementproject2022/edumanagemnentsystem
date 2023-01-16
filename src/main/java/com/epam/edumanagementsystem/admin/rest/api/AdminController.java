package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.config.MessageByLang;
import com.epam.edumanagementsystem.util.AppConstants;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admins")
@Tag(name = "Admins")
public class AdminController {

    private static final String ADMIN_SECTION_HTML = "adminSection";
    private static final String ADMIN = "admin";

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Gets all admins and shows them on super admin`s dashboard")
    public String openAdmin(Model model) {
        model.addAttribute(ADMIN, new AdminDto());
        model.addAttribute("admins", adminService.findAll());
        return ADMIN_SECTION_HTML;
    }

    @PostMapping
    @Operation(summary = "Creates a new admin in popup")
    public String addAdmin(@ModelAttribute("admin") @Valid AdminDto adminDto,
                           BindingResult bindingResult, Model model) {

        checkEmailForCreate(adminDto, bindingResult, model);

        if (bindingResult.hasErrors()) {
            return ADMIN_SECTION_HTML;
        }
        adminService.save(adminDto);
        return "redirect:/admins";
    }

    private void checkEmailForCreate(AdminDto adminDto, BindingResult bindingResult, Model model) {
        if (UserDataValidation.existsEmail(adminDto.getEmail())) {
            bindingResult.addError(new ObjectError(ADMIN, "Duplicate email"));
            model.addAttribute(AppConstants.DUPLICATED, MessageByLang.getMessage("USER_WITH_EMAIL_EXISTS"));
        }
    }
}
