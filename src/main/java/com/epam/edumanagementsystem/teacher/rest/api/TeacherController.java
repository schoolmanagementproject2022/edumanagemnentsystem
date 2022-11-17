package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final TeacherService teacherService;
    private final UserService userService;
    private final ImageService imageService;
    private final String TEACHER_HTML = "teacherSection";

    private final String PROFILE = "teacherProfile";

    @Autowired
    public TeacherController(PasswordEncoder bcryptPasswordEncoder, TeacherService teacherService,
                             UserService userService, ImageService imageService, ImageService imageService1) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.teacherService = teacherService;
        this.userService = userService;
        this.imageService = imageService1;
    }

    @GetMapping
    public String openTeacherSection(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("teacher", new TeacherDto());
        return TEACHER_HTML;
    }

    @PostMapping
    public String createTeacher(@ModelAttribute("teacher") @Valid TeacherDto teacherDto,
                                BindingResult result,
                                Model model) throws IOException {
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

    @GetMapping("/{id}/profile")
    public String openTeacherProfile(@PathVariable("id") Long id, Model model) {
        TeacherDto existingTeacher = teacherService.findById(id);
        model.addAttribute("name_surname", TeacherMapper.
                toTeacher(existingTeacher,
                        userService.findByEmail(existingTeacher.getEmail())).getNameSurname());
        model.addAttribute("teacher", existingTeacher);
        return PROFILE;
    }

    @PostMapping("/{id}/profile")
    public String editTeacherPersonalInformation(@ModelAttribute("teacher") @Valid TeacherDto updatableTeacher,
                                                 BindingResult result, @PathVariable("id") Long id, Model model) {
        TeacherDto existingTeacher = teacherService.findById(id);
        model.addAttribute("name_surname", TeacherMapper.toTeacher(existingTeacher,
                        userService.findByEmail(existingTeacher.getEmail())).getNameSurname());
        if (!updatableTeacher.getEmail().equals(existingTeacher.getEmail())) {
            userService.checkDuplicationOfEmail(updatableTeacher.getEmail(), model);
        }
        EmailValidation.validate(updatableTeacher.getEmail(), model);

        if (result.hasErrors() || model.containsAttribute("invalidEmail") ||
                model.containsAttribute("duplicated")) {
            return PROFILE;
        }
        teacherService.updateFields(updatableTeacher);
        return "redirect:/teachers/" + id + "/profile";
    }

    @PostMapping("/{id}/image/add")
    public String addPic(@PathVariable("id") Long id, @RequestParam("picture") MultipartFile multipartFile) {
        TeacherDto teacherById = teacherService.findById(id);
        User userByEmail = userService.findByEmail(teacherById.getEmail());
        teacherService.addProfilePicture(TeacherMapper.toTeacher(teacherById, userByEmail), multipartFile);
        return "redirect:/teachers/" + id + "/profile";
    }

    @GetMapping("/{id}/image/delete")
    public String deletePic(@PathVariable("id") Long id) {
        TeacherDto teacherById = teacherService.findById(id);
        String picUrl = teacherById.getPicUrl();
        imageService.deleteImage(picUrl);
        teacherService.deletePic(teacherById.getId());
        return "redirect:/teachers/" + id + "/profile";
    }

}
