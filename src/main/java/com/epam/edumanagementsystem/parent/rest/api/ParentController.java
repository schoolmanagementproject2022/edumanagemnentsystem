package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
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

@Controller
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
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

        for (Parent parentLoop : parentService.findAll()) {
            if (parent.getEmail().equalsIgnoreCase(parentLoop.getEmail())) {
                modelMap.addAttribute("duplicated", "A user with the specified email already exists");
                return "parentSection";
            }
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
