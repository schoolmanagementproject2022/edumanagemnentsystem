package com.epam.edumanagementsystem.student.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final String STUDENT_HTML = "studentSection";

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
        return STUDENT_HTML;
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
        userService.checkDuplicationOfEmail(studentDto.getEmail(), model);
        PasswordValidation.validatePassword(studentDto.getPassword(), model);
        EmailValidation.validate(studentDto.getEmail(), model);

        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")) {
            return STUDENT_HTML;
        }
        studentDto.setPassword(bcryptPasswordEncoder.encode(studentDto.getPassword()));
        studentService.create(studentDto);
        return "redirect:/students";
    }

    @GetMapping("/{id}/profile")
    public String openStudentProfile(@PathVariable("id") Long studentId, Model model) {
        StudentDto existingStudent = studentService.findByStudentId(studentId);
        model.addAttribute("name_surname", StudentMapper.toStudent(existingStudent,
                userService.findByEmail(existingStudent.getEmail())).getNameAndSurname());
        model.addAttribute("existingStudent", existingStudent);
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", findAllParents());
        model.addAttribute("classes", findAllClasses());
        return "studentProfile";
    }

    @PostMapping("/{id}/profile")
    public String editStudentPersonalInformation(@ModelAttribute("existingStudent") @Valid StudentDto updatableStudent,
                                                 BindingResult result, @PathVariable("id") Long studentId, Model model) {
        StudentDto existingStudent = studentService.findByStudentId(studentId);
        model.addAttribute("name_surname", StudentMapper.toStudent(existingStudent,
                userService.findByEmail(existingStudent.getEmail())).getNameAndSurname());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", findAllParents());
        model.addAttribute("classes", findAllClasses());
        if (!updatableStudent.getEmail().equals(existingStudent.getEmail())) {
            userService.checkDuplicationOfEmail(updatableStudent.getEmail(), model);
        }
        EmailValidation.validate(updatableStudent.getEmail(), model);

        if (result.hasErrors() || model.containsAttribute("invalidEmail") ||
                model.containsAttribute("duplicated")) {
            return "studentProfile";
        }
        studentService.updateFields(updatableStudent);
        return "redirect:/students/" + updatableStudent.getId() + "/profile";
    }

    private List<StudentDto> findAllStudents() {
        return studentService.findAll();
    }

    private List<ParentDto> findAllParents() {
        return ParentMapper.toParentDtoList(parentService.findAll());
    }

    private List<AcademicClassDto> findAllClasses() {
        return academicClassService.findAll();
    }
}