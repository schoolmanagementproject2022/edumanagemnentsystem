package com.epam.edumanagementsystem.parent.rest.api;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/parents")
public class ParentController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final ParentService parentService;
    private final UserService userService;
    private final ImageService imageService;

    private final StudentService studentService;

    @Autowired
    public ParentController(PasswordEncoder bcryptPasswordEncoder, ParentService parentService,
                            UserService userService, ImageService imageService, StudentService studentService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.parentService = parentService;
        this.userService = userService;
        this.imageService = imageService;
        this.studentService = studentService;
    }

    @GetMapping()
    @Operation(summary = "Gets the list of parents and shows on admin's dashboard")
    public String toParents(ModelMap modelMap) {
        modelMap.addAttribute("parents", parentService.findAll());
        modelMap.addAttribute("parent", new ParentDto());
        modelMap.addAttribute("user", new User());
        return "parentSection";
    }

    @PostMapping()
    @Operation(summary = "Creates a new parent and saves in DB")
    public String saveParent(@Valid @ModelAttribute(value = "parent") ParentDto parentDto, BindingResult bindingResult,
                             Model model) {
        model.addAttribute("parents", parentService.findAll());
        if (!bindingResult.hasFieldErrors("email")) {
            userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
            EmailValidation.validate(parentDto.getEmail(), model);
        }
        PasswordValidation.validatePassword(parentDto.getPassword(), model);
        if (bindingResult.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")) {
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

    @GetMapping("/{id}/students")
    public String openParentOfStudent(@PathVariable("id") Long id, Model model) {
        List<StudentDto> studentsByParentId = studentService.findStudentsByParentId(id);
        model.addAttribute("students", studentsByParentId);
        model.addAttribute("parent", parentService.findById(id).get());
        return "parentSectionForStudents";
    }

    @PostMapping("/{id}/profile")
    public String editParent(@Valid @ModelAttribute("parentDto") ParentDto parentDto, BindingResult bindingResult,
                             @PathVariable("id") Long id, Model model) {
        if (!bindingResult.hasFieldErrors("email")) {
            if (!parentDto.getEmail().equals(parentService.findById(id).get().getUser().getEmail())) {
                userService.checkDuplicationOfEmail(parentDto.getEmail(), model);
            }
            EmailValidation.validate(parentDto.getEmail(), model);
        }

        if (bindingResult.hasErrors() || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")) {
            model.addAttribute("parentData", parentService.findById(id).get().getNameAndSurname());
            return "parentProfile";
        }
        parentService.updateParent(parentDto);
        return "redirect:/parents/" + id + "/profile";
    }

    @PostMapping("/{id}/image/add")
    public String addPic(@ModelAttribute("existingParent") Parent parent, @PathVariable("id") Long id,
                         @RequestParam("picture") MultipartFile multipartFile) {
        Parent parentById = parentService.findById(id).get();

        parentService.addProfilePicture(parentById, multipartFile);
        return "redirect:/parents/" + id + "/profile";
    }

    @GetMapping("/{id}/image/delete")
    public String deletePic(@PathVariable("id") Long id) {
        Parent parentById = parentService.findById(id).get();
        String picUrl = parentById.getPicUrl();
        imageService.deleteImage(picUrl);
        parentService.deletePic(parentById.getId());
        return "redirect:/parents/" + id + "/profile";
    }

}
