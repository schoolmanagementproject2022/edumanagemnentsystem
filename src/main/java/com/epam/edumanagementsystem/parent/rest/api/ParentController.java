package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
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
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController( ParentService parentService) {

        this.parentService = parentService;
    }

    @GetMapping()
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.findAll());
        modelMap.addAttribute("parent", new Parent());
        return "parentSection";
    }

    @PostMapping()
    public String saveParent(@Valid @ModelAttribute(value = "parent") Parent parent, BindingResult bindingResult,
                             ModelMap modelMap) {

        modelMap.addAttribute("parents", parentService.findAll());
        if (parentService.findByEmail(parent.getEmail()).isPresent()) {
            modelMap.addAttribute("duplicated", "A user with the specified email already exists");
            return "parentSection";
        }
        if (bindingResult.hasErrors()) {
            if (!bindingResult.hasFieldErrors("email")) {
                if (!EmailValidation.validate(parent.getEmail())) {
                    modelMap.addAttribute("invalid", "Email is invalid");
                    return "parentSection";
                }
            }
            return "parentSection";
        } else if (!EmailValidation.validate(parent.getEmail())) {
            modelMap.addAttribute("invalid", "Email is invalid");
            return "parentSection";
        }
        parentService.save(parent);
        return "redirect:/parents";
    }

}
