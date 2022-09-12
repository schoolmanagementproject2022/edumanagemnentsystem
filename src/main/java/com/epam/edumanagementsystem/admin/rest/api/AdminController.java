package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.EmailValidation;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    public String getAll(ModelMap modelMap) {
        List<AdminDto> allAdmins = adminService.findAllAdmins();
        modelMap.addAttribute("admin", new Admin());
        modelMap.addAttribute("admins", allAdmins);
        return "adminSection";
    }

    @PostMapping
    public String addAdmin(@ModelAttribute("admin") @Valid Admin admin, BindingResult result, ModelMap modelMap) {
        List<AdminDto> allAdmins = adminService.findAllAdmins();
        modelMap.addAttribute("admins", allAdmins);
        for (AdminDto admins : allAdmins) {
            if (admin.getEmail().equals(admins.getEmail())) {
                modelMap.addAttribute("duplicated", "A user with the specified email already exists");
                return "adminSection";
            }
        }

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("email")) {
                if (!EmailValidation.validate(admin.getEmail())) {
                    modelMap.addAttribute("invalid", "Email is invalid");
                    return "adminSection";
                }
            }
            return "adminSection";
        } else {
            adminService.addAdmin(admin);
            return "redirect:/admins";
        }
    }
}
