package com.epam.edumanagementsystem.student.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.entity.User;
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
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {


    private final PasswordEncoder bcryptPasswordEncoder;
    private final StudentService studentService;
    private final UserService userService;
    private final ParentService parentService;
    private final AcademicClassService academicClassService;

    @Autowired
    public StudentController(PasswordEncoder bcryptPasswordEncoder, StudentService studentService,
                             UserService userService, ParentService parentService,
                             AcademicClassService academicClassService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.studentService = studentService;
        this.userService = userService;
        this.parentService = parentService;
        this.academicClassService = academicClassService;
    }

    @GetMapping
    public String openStudentSection(Model model) {
        model.addAttribute("student", new StudentDto());
        model.addAttribute("students", findAllStudents());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", findAllParents());
        model.addAttribute("classes", findAllClasses());
        return "studentSection";
    }

    @PostMapping
    public String createStudentSection(@ModelAttribute("student") @Valid StudentDto studentDto,
                                       BindingResult result,
                                       Model model) {
        model.addAttribute("students", findAllStudents());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", ParentMapper.toParentListWithoutSaveUser(findAllParents()));
        model.addAttribute("classes", findAllClasses());
        for (User user : userService.findAll()) {
            if (studentDto.getEmail().equalsIgnoreCase(user.getEmail())) {
                model.addAttribute("duplicated", "A user with the specified email already exists");
                return "studentSection";
            }
        }
        if (result.hasErrors()) {
            if (!result.hasFieldErrors("email")) {
                if (!EmailValidation.validate(studentDto.getEmail())) {
                    model.addAttribute("invalid", "Email is invalid");
                    return "studentSection";
                }
            }
            return "studentSection";
        } else if (!EmailValidation.validate(studentDto.getEmail())) {
            model.addAttribute("invalid", "Email is invalid");
            return "studentSection";
        }
        studentDto.setPassword(bcryptPasswordEncoder.encode(studentDto.getPassword()));
        studentService.create(studentDto);
        return "redirect:/students";
    }

    public List<StudentDto> findAllStudents() {
        return studentService.findAll();
    }

    public List<ParentDto> findAllParents() {
        return ParentMapper.toParentDtoList(parentService.findAll());
    }

    public List<AcademicClassDto> findAllClasses() {
        return academicClassService.findAll();
    }
}
