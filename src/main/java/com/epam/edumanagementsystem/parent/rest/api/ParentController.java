package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        parentService.save(parentDto);
        return "redirect:/parents";
    }

    @GetMapping("/{id}/profile")
    public String openParentProfile(@PathVariable("id") Long id, Model model) {
        Parent parent = parentService.findById(id).get();
        model.addAttribute("parentDto", ParentMapper.toParentDto(parent));
        return "parentProfile";
    }

    @Transactional
    @PostMapping("/{id}/profile")
    public String edit(@Valid @ModelAttribute("parentDto") ParentDto parentDto, BindingResult bindingResult,
                       @PathVariable("id") Long id, Model model) {

        Parent parent = parentService.findById(id).get();

        if (parentDto.getEmail().equalsIgnoreCase(parent.getUser().getEmail())) {
            if (bindingResult.hasErrors()) {
                if (!bindingResult.hasFieldErrors("name") && !bindingResult.hasFieldErrors("surname")) {
                    parentService.updateParentNameAndSurnameById(parentDto.getName(), parentDto.getSurname(), parentDto.getId());
                    return "redirect:/parents/" + id + "/profile";
                }
                return "parentProfile";
            }
        }

        if (!parentDto.getEmail().equalsIgnoreCase(parent.getUser().getEmail())) {
            for (User user : userService.findAll()) {
                if (parentDto.getEmail().equalsIgnoreCase(user.getEmail())) {
                    model.addAttribute("duplicated", "A user with the specified email already exists");
                    return "parentProfile";
                }
            }
            if (bindingResult.hasErrors()) {
                if (!bindingResult.hasFieldErrors("name") && !bindingResult.hasFieldErrors("surname")) {
                    if (!bindingResult.hasFieldErrors("email")) {
                        if (!EmailValidation.validate(parentDto.getEmail())) {
                            model.addAttribute("invalid", "Email is invalid");
                            return "parentProfile";
                        }
                    } else if (!EmailValidation.validate(parentDto.getEmail())) {
                        return "parentProfile";
                    }
                } else if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname")) {
                    if (!bindingResult.hasFieldErrors("email")) {
                        if (!EmailValidation.validate(parentDto.getEmail())) {
                            model.addAttribute("invalid", "Email is invalid");
                            return "parentProfile";
                        }
                    } else if (!EmailValidation.validate(parentDto.getEmail())) {
                        return "parentProfile";
                    }
                }
                parentService.updateParentNameAndSurnameById(parentDto.getName(), parentDto.getSurname(), parentDto.getId());
                parent.getUser().setEmail(parentDto.getEmail());
                userService.save(parent.getUser());
                return "redirect:/parents/" + id + "/profile";
            }
        }
        parentService.updateParentNameAndSurnameById(parentDto.getName(), parentDto.getSurname(), parentDto.getId());
        return "redirect:/parents/" + id + "/profile";
    }
}

