package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Teachers")
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
    @Operation(summary = "Gets the list of teachers and shows on admin's dashboard")
    public String openTeacherSection(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("teacher", new TeacherDto());
        return TEACHER_HTML;
    }

    @PostMapping
    @Operation(summary = "Creates a new teacher and saves in DB")
    public String createTeacher(@ModelAttribute("teacher") @Valid TeacherDto teacherDto,
                                BindingResult result, Model model) {
        model.addAttribute("teachers", teacherService.findAll());

        if (userService.checkDuplicationOfEmail(teacherDto.getEmail())) {
            model.addAttribute("duplicated", "A user with the specified email already exists");
            return TEACHER_HTML;
        }

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("email")) {
                if (!EmailValidation.validate(teacherDto.getEmail())) {
                    model.addAttribute("invalid", "Email is invalid");
                }
            }
            return TEACHER_HTML;

        } else if (!EmailValidation.validate(teacherDto.getEmail())) {
            model.addAttribute("invalid", "Email is invalid");
            return TEACHER_HTML;
        }
        teacherDto.setPassword(bcryptPasswordEncoder.encode(teacherDto.getPassword()));
        teacherService.create(teacherDto);
        return "redirect:/teachers";
    }
}
