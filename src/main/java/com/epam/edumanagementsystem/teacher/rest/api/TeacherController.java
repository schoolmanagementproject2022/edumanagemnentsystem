package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final TeacherService teacherService;
    private final UserService userService;
    private final String TEACHER_HTML = "teacherSection";

    @Autowired
    public TeacherController(PasswordEncoder bcryptPasswordEncoder, TeacherService teacherService,
                             UserService userService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @GetMapping
    public String openTeacherSection(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("teacher", new TeacherDto());
        return TEACHER_HTML;
    }

    @PostMapping
    public String createTeacher(@ModelAttribute("teacher") @Valid TeacherDto teacherDto,
                                BindingResult result, Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        userService.checkDuplicationOfEmail(teacherDto.getEmail(), model);
        EmailValidation.validate(teacherDto.getEmail(), model);
        PasswordValidation.validatePassword(teacherDto.getPassword(), model);
        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")) {
            return TEACHER_HTML;
        }

        teacherDto.setPassword(bcryptPasswordEncoder.encode(teacherDto.getPassword()));
        teacherService.create(teacherDto);
        return "redirect:/teachers";
    }
}
