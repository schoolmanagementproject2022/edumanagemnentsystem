package com.epam.edumanagementsystem.student.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/students")
@Tag(name = "Students")

public class StudentController extends StudentControllerHelper{

    private final PasswordEncoder bcryptPasswordEncoder;
    private final StudentService studentService;
    private final UserService userService;
    private final ParentService parentService;
    private final ImageService imageService;
    private final AcademicClassService academicClassService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


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
        model.addAttribute(StudentControllerHelper.STUDENTS, studentService.findAll());
        model.addAttribute(GROUP, BloodGroup.values());
        model.addAttribute(GENDER, Gender.values());
        model.addAttribute(PARENTS, parentService.findAll());
        model.addAttribute(CLASSES, academicClassService.findAll());
        logger.info("user opened students page . . . ");
        return STUDENT_HTML;
    }

    @GetMapping("/without/parent")
    public String openStudentsWithoutParent(Model model) {
        model.addAttribute(STUDENTS, studentService.findStudentsWithoutParent());
        logger.info("user opened students page without parent . . . ");

        return FILTERED_STUDENTS;
    }

    @PostMapping
    @Operation(summary = "Creates a new student and saves in DB")
    public String createStudentSection(@ModelAttribute("student") @Valid StudentDto studentDto,
                                       BindingResult result,
                                       @RequestParam(value = "picture", required = false) MultipartFile multipartFile,
                                       @RequestParam(value = "status", required = false) String status,
                                       Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            UserDataValidation.validateImage(multipartFile, model);
        }
        logger.debug(multipartFile.getName()+" "+studentDto.getNameAndSurname());
        if (status.equals("validationFail")) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
        }

        model.addAttribute(STUDENTS, studentService.findAll());
        model.addAttribute(GROUP, BloodGroup.values());
        model.addAttribute(GENDER, Gender.values());
        model.addAttribute(PARENTS, parentService.findAll());
        model.addAttribute(CLASSES, academicClassService.findAll());

        if (!result.hasFieldErrors("email")) {
            userService.checkDuplicationOfEmail(studentDto.getEmail(), model);
            if (UserDataValidation.validateEmail(studentDto.getEmail())) {
                model.addAttribute(INVALID_EMAIL, "Email is invalid");
            }
        }
        UserDataValidation.validatePassword(studentDto.getPassword(), model);

        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute(INVALID_EMAIL)
                || model.containsAttribute("duplicated")
                || model.containsAttribute("size")
                || model.containsAttribute("formatValidationMessage")) {
            return STUDENT_HTML;
        }
        studentDto.setPassword(bcryptPasswordEncoder.encode(studentDto.getPassword()));
        StudentDto student = studentService.create(studentDto);

        if (!multipartFile.isEmpty()) {
            studentService.addProfilePicture(student, multipartFile);
        }
        return REDIRECT;
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "Shows selected student's profile")
    public String openStudentProfile(@PathVariable("id") Long studentId, Model model) {
        StudentDto existingStudent = studentService.findById(studentId);
        model.addAttribute("name_surname", StudentMapper.toStudent(existingStudent,
                userService.findByEmail(existingStudent.getEmail())).getNameAndSurname());
        model.addAttribute("existingStudent", existingStudent);
        model.addAttribute(GROUP, BloodGroup.values());
        model.addAttribute(GENDER, Gender.values());

        model.addAttribute(PARENTS, parentService.findAll());
        model.addAttribute(CLASSES, academicClassService.findAll());
        logger.info("user opened parent profile page"+ existingStudent.getNameAndSurname());
        return "studentProfile";
    }

    @PostMapping("/{id}/profile")
    @Operation(summary = "Edits selected student's profile")
    public String editStudentPersonalInformation(@ModelAttribute("existingStudent") @Valid StudentDto updatableStudent,
                                                 BindingResult result, @PathVariable("id") Long studentId, Model model) {
        StudentDto existingStudent = studentService.findById(studentId);
        model.addAttribute("name_surname", StudentMapper.toStudent(existingStudent,
                userService.findByEmail(existingStudent.getEmail())).getNameAndSurname());
        model.addAttribute(GROUP, BloodGroup.values());
        model.addAttribute(GENDER, Gender.values());
        model.addAttribute(PARENTS, parentService.findAll());
        model.addAttribute(CLASSES, academicClassService.findAll());

        if (!result.hasFieldErrors("email") ) {
            if (!updatableStudent.getEmail().equals(existingStudent.getEmail())) {
                userService.checkDuplicationOfEmail(updatableStudent.getEmail(), model);
            }
            if (UserDataValidation.validateEmail(updatableStudent.getEmail())) {
                model.addAttribute(INVALID_EMAIL, "Email is invalid");
            }
        }

        if (result.hasErrors() || model.containsAttribute(INVALID_EMAIL)
                || model.containsAttribute("duplicated")
        ) {
            return "studentProfile";
        }
        studentService.updateFields(updatableStudent);
        return REDIRECT + updatableStudent.getId() + PROFILE;
    }

    @PostMapping("/{id}/image/add")
    @Operation(summary = "Adds image to selected student's profile")
    public String addPic(@PathVariable("id") Long id,
                         @RequestParam("picture") MultipartFile multipartFile) {
        StudentDto studentById = studentService.findById(id);
        logger.debug(multipartFile.getName()+" "+studentById);
        studentService.addProfilePicture(studentById, multipartFile);
        return REDIRECT + id + PROFILE;
    }

    @GetMapping("/{id}/image/delete")
    @Operation(summary = "Deletes image to selected student's profile")
    public String deletePic(@PathVariable("id") Long id) {
        StudentDto studentById = studentService.findById(id);
        logger.info("user want delete omage"+" " +studentById);
        imageService.deleteImage(studentById.getPicUrl());
        studentService.deletePic(studentById.getId());
        return REDIRECT + id + PROFILE;
    }


}