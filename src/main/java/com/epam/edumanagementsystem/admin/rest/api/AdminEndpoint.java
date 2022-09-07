package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDTO;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
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
@RequestMapping("/admin")
public class AdminEndpoint {
    private final AdminService adminService;

    public AdminEndpoint(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/add")
    public String addAdmin(ModelMap modelMap) {
        return "admin";
    }
    @PostMapping("/add")
    public String addAdmin(@ModelAttribute("admin") @Valid Admin admin, BindingResult result, ModelMap modelMap) {
        List<AdminDTO> all = adminService.findAllAdmins();
        modelMap.addAttribute("admins", all);
        if (result.hasErrors()) {
            return "redirect:/admin/add";
        }
        adminService.addAdmin(admin);
        return "redirect:/admin";
    }
    @GetMapping()
    public String getAll(ModelMap modelMap) {
        modelMap.addAttribute("admin", new Admin());
        List<AdminDTO> all = adminService.findAllAdmins();
        modelMap.addAttribute("admins", all);
        return "admin";
    }


}
