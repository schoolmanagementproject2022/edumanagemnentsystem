package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.Validation;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admins")
@Tag(name="Admins")
public class AdminController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public AdminController(PasswordEncoder bcryptPasswordEncoder,
                           AdminService adminService, UserService userService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping()
    @Operation(summary = "Gets all admins and shows them on super admin's dashboard")
    public String getAll(ModelMap modelMap) {
        modelMap.addAttribute("admin", new AdminDto());
        modelMap.addAttribute("admins", adminService.findAllAdmins());
        return "adminSection";
    }

    @PostMapping
    @Operation(summary = "Creates a new admin in popup")
    public String addAdmin(@ModelAttribute("admin") @Valid AdminDto adminDto,
                           BindingResult result, Model model) {

        model.addAttribute("admins", adminService.findAllAdmins());
        if (!result.hasFieldErrors("email")) {
            userService.checkDuplicationOfEmail(adminDto.getEmail(), model);
            EmailValidation.validate(adminDto.getEmail(), model);
        }
        Validation.validatePassword(adminDto.getPassword(), model);

        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")) {
            return "adminSection";
        }
        adminDto.setPassword(bcryptPasswordEncoder.encode(adminDto.getPassword()));
        adminService.addAdmin(adminDto);
        return "redirect:/admins";
    }
}
