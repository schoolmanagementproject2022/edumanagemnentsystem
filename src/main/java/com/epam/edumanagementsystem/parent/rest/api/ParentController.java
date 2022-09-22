package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
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

@Controller
@RequestMapping("/parents")
public class ParentController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final ParentService parentService;
    private final UserService userService;

    @Autowired
    public ParentController(PasswordEncoder bcryptPasswordEncoder, ParentService parentService,
                            UserService userService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.parentService = parentService;
        this.userService = userService;
    }

    @GetMapping()
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.findAll());
        modelMap.addAttribute("parent", new ParentDto());
        modelMap.addAttribute("user", new User());
        return "parentSection";
    }

    @PostMapping()
    public String saveParent(@Valid @ModelAttribute(value = "parent") ParentDto parentDto, BindingResult bindingResult,
                             ModelMap modelMap) {

        modelMap.addAttribute("parents", parentService.findAll());

        for (User user : userService.findAll()) {
            if (parentDto.getEmail().equalsIgnoreCase(user.getEmail())) {
                modelMap.addAttribute("duplicated", "A user with the specified email already exists");
                return "parentSection";
            }
        }
        if (bindingResult.hasErrors()) {
            if (!bindingResult.hasFieldErrors("email")) {
                if (!EmailValidation.validate(parentDto.getEmail())) {
                    modelMap.addAttribute("invalid", "Email is invalid");
                    return "parentSection";
                }
            }
            return "parentSection";
        } else if (!EmailValidation.validate(parentDto.getEmail())) {
            modelMap.addAttribute("invalid", "Email is invalid");
            return "parentSection";
        }
        parentDto.setPassword(bcryptPasswordEncoder.encode(parentDto.getPassword()));
        parentService.save(parentDto, userService);
        return "redirect:/parents";
    }
}
