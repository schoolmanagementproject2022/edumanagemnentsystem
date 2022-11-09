package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final TeacherService teacherService;
    private final UserService userService;
    private final String TEACHER_HTML = "teacherSection";
    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);
    private final String UPLOADED_FOLDER = "C:\\edumanagemnentsystem\\images\\";

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
                                @RequestParam("image") MultipartFile file,
                                BindingResult result, Model model) {
        String contentType = file.getContentType();
        String[] split;
        if (contentType != null) {
            split = contentType.split("/");
        } else {
            throw new NullPointerException("Name is null");
        }
        String fileRealName = System.currentTimeMillis() + "." + split[1];
        teacherDto.setImageUrl(fileRealName);
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

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + fileRealName);
                Files.write(path, bytes);

                logger.info("You successfully uploaded file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        teacherDto.setPassword(bcryptPasswordEncoder.encode(teacherDto.getPassword()));
        teacherService.create(teacherDto);
        return "redirect:/teachers";
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(UPLOADED_FOLDER + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }

}
