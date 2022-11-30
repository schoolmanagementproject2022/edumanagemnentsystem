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
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.Validation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/students")
@Tag(name = "Students")
public class StudentController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final StudentService studentService;
    private final UserService userService;
    private final ParentService parentService;
    private final ImageService imageService;
    private final AcademicClassService academicClassService;
    private final String STUDENT_HTML = "studentSection";

    @Autowired
    public StudentController(PasswordEncoder bcryptPasswordEncoder, StudentService studentService,
                             UserService userService, ParentService parentService,
                             ImageService imageService, AcademicClassService academicClassService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.studentService = studentService;
        this.userService = userService;
        this.parentService = parentService;
        this.imageService = imageService;
        this.academicClassService = academicClassService;

    }

    @GetMapping
    @Operation(summary = "Gets the list of students and shows on admin's dashboard")
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
    @Operation(summary = "Creates a new student and saves in DB")
    public String createStudentSection(@ModelAttribute("student") @Valid StudentDto studentDto,
                                       BindingResult result,
                                       @RequestParam(value = "picture", required = false) MultipartFile multipartFile,
                                       @RequestParam(value = "status", required = false) String status,
                                       Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            Validation.validateImage(multipartFile, model);
        }
        if (status.equals("validationFail")) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
        }

        model.addAttribute("students", findAllStudents());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", ParentMapper.toParentListWithoutSaveUser(findAllParents()));
        model.addAttribute("classes", findAllClasses());
        if (!result.hasFieldErrors("email")) {
            userService.checkDuplicationOfEmail(studentDto.getEmail(), model);
            EmailValidation.validate(studentDto.getEmail(), model);
        }
        Validation.validatePassword(studentDto.getPassword(), model);

        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")
                || model.containsAttribute("size")
                || model.containsAttribute("formatValidationMessage")) {
            return STUDENT_HTML;
        }
        studentDto.setPassword(bcryptPasswordEncoder.encode(studentDto.getPassword()));
        Student student = studentService.create(studentDto);

        if (!multipartFile.isEmpty()) {
            studentService.addProfilePicture(student, multipartFile);
        }
        return "redirect:/students";
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "Shows selected student's profile")
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
    @Operation(summary = "Edits selected student's profile")
    public String editStudentPersonalInformation(@ModelAttribute("existingStudent") @Valid StudentDto updatableStudent,
                                                 BindingResult result, @PathVariable("id") Long studentId, Model model) {
        StudentDto existingStudent = studentService.findByStudentId(studentId);
        model.addAttribute("name_surname", StudentMapper.toStudent(existingStudent,
                userService.findByEmail(existingStudent.getEmail())).getNameAndSurname());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", findAllParents());
        model.addAttribute("classes", findAllClasses());
        if (!result.hasFieldErrors("email")) {
            if (!updatableStudent.getEmail().equals(existingStudent.getEmail())) {
                userService.checkDuplicationOfEmail(updatableStudent.getEmail(), model);
            }
            EmailValidation.validate(updatableStudent.getEmail(), model);
        }

        if (result.hasErrors() || model.containsAttribute("invalidEmail") ||
                model.containsAttribute("duplicated")) {
            return "studentProfile";
        }
        studentService.updateFields(updatableStudent);
        return "redirect:/students/" + updatableStudent.getId() + "/profile";
    }

    @PostMapping("/{id}/image/add")
    @Operation(summary = "Adds image to selected student's profile")
    public String addPic(@PathVariable("id") Long id,
                         @RequestParam("picture") MultipartFile multipartFile) {
        StudentDto studentById = studentService.findByStudentId(id);
        User userByEmail = userService.findByEmail(studentById.getEmail());
        studentService.addProfilePicture(StudentMapper.toStudent(studentById, userByEmail), multipartFile);
        return "redirect:/students/" + id + "/profile";
    }

    @GetMapping("/{id}/image/delete")
    @Operation(summary = "Deletes image to selected student's profile")
    public String deletePic(@PathVariable("id") Long id) {
        StudentDto studentById = studentService.findByStudentId(id);
        imageService.deleteImage(studentById.getPicUrl());
        studentService.deletePic(studentById.getId());
        return "redirect:/students/" + id + "/profile";
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
