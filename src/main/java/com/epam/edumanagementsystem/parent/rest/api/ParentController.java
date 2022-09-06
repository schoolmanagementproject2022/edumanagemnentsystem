package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("parents")
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.parents());
        modelMap.addAttribute("parent", new Parent());
        return "parents";
    }

    @PostMapping("parent/save")
    public String saveParent(@Valid @ModelAttribute(value = "parent") ParentDto parentDto, BindingResult bindingResult,
                             ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.parents());
        if (parentService.findByEmail(parentDto.getEmail()).isPresent()) {
            modelMap.addAttribute("duplicated", "A user with the specified email already exists");
            return "parents";
        }
        if (bindingResult.hasErrors()) {
            return "parents";
        }
        parentService.save(parentDto);
        return "redirect:/parents";
    }

}
