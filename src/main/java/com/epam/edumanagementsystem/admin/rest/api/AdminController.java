package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admins")
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
    public String getAll(ModelMap modelMap) {
        List<AdminDto> allAdmins = adminService.findAllAdmins();
        modelMap.addAttribute("admin", new AdminDto());
        modelMap.addAttribute("admins", allAdmins);
        return "adminSection";
    }

    @PostMapping
    public String addAdmin(@ModelAttribute("admin") @Valid AdminDto adminDto,
                           BindingResult result, ModelMap modelMap) {
        List<AdminDto> allAdmins = adminService.findAllAdmins();
        modelMap.addAttribute("admins", allAdmins);
        for (User user : userService.findAll()) {
            if (adminDto.getEmail().equalsIgnoreCase(user.getEmail())) {
                modelMap.addAttribute("duplicated", "A user with the specified email already exists");
                return "adminSection";
            }
        }

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("email")) {
                if (!EmailValidation.validate(adminDto.getEmail())) {
                    modelMap.addAttribute("invalid", "Email is invalid");
                    return "adminSection";
                }
            }
            return "adminSection";
        } else if (!EmailValidation.validate(adminDto.getEmail())) {
            modelMap.addAttribute("invalid", "Email is invalid");
            return "adminSection";
        }
        adminDto.setPassword(bcryptPasswordEncoder.encode(adminDto.getPassword()));
        adminService.addAdmin(adminDto, userService);
        return "redirect:/admins";
    }
}
