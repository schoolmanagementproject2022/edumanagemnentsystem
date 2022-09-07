package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping()
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.parents());
        modelMap.addAttribute("parent", new Parent());
        return "parents";
    }

    @PostMapping("/save")
    public String saveParent(@Valid @ModelAttribute(value = "parent") Parent parent, BindingResult bindingResult,
                             ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.parents());
        if (parentService.findByEmail(parent.getEmail()).isPresent()) {
            modelMap.addAttribute("duplicated", "A user with the specified email already exists");
            return "parents";
        }
        if (bindingResult.hasErrors()) {
            return "parents";
        }
        parentService.save(parent);
        return "redirect:/parents";
    }

}
