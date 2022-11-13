package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
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
                             Model model) {
        model.addAttribute("parents", parentService.findAll());
        userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
        EmailValidation.validate(parentDto.getEmail(), model);
        PasswordValidation.validatePassword(parentDto.getPassword(), model);
        if (bindingResult.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")) {
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
        model.addAttribute("parentData", parent.getNameAndSurname());
        return "parentProfile";
    }

    @PostMapping("/{id}/profile")
    public String editParent(@Valid @ModelAttribute("parentDto") ParentDto parentDto, BindingResult bindingResult,
                             @PathVariable("id") Long id, Model model) {

        if (!parentDto.getEmail().equals(parentService.findById(id).get().getUser().getEmail())) {
            userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
        }
        EmailValidation.validate(parentDto.getEmail(), model);

        if (bindingResult.hasErrors() || model.containsAttribute("invalidEmail") || model.containsAttribute("duplicated")) {
            model.addAttribute("parentData", parentDto.getNameAndSurname());
            return "parentProfile";
        }
        parentService.updateParent(parentDto);
        return "redirect:/parents/" + id + "/profile";
    }
}

