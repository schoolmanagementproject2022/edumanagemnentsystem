package com.epam.edumanagementsystem.teacher.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.EmailValidation;
import com.epam.edumanagementsystem.util.PasswordValidation;
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

@Controller
@RequestMapping("/teachers")
@Tag(name = "Teachers")
public class TeacherController {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final TeacherService teacherService;
    private final UserService userService;
    private final AcademicCourseService academicCourseService;
    private final ImageService imageService;

    private final SubjectService subjectService;


    private final SubjectService subjectService;

    private final String TEACHER_HTML = "teacherSection";
    private final String PROFILE = "teacherProfile";
    private final String SUBJECTS_FOR_TEACHER = "subjectSectionForTeacherProfile";
    private final String SUBJECTS_FOR_TEACHER = "subjectSectionForTeacherProfile";
    private final String COURSES_FOR_TEACHER = "coursesInTeacherProfile";

    @Autowired
    public TeacherController(PasswordEncoder bcryptPasswordEncoder, TeacherService teacherService,
                             UserService userService, ImageService imageService,
                            SubjectService subjectService) {
                             UserService userService, AcademicCourseService academicCourseService,
                             ImageService imageService, SubjectService subjectService) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.teacherService = teacherService;
        this.userService = userService;
        this.academicCourseService = academicCourseService;
        this.imageService = imageService;
        this.subjectService = subjectService;
        this.imageService = imageService;
        this.subjectService = subjectService;
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
                                BindingResult result,
                                @RequestParam(value = "picture", required = false) MultipartFile multipartFile,
                                Model model) throws IOException {
        model.addAttribute("teachers", teacherService.findAll());
        if (!result.hasFieldErrors("email")) {
            userService.checkDuplicationOfEmail(teacherDto.getEmail(), model);
            EmailValidation.validate(teacherDto.getEmail(), model);
        }
        PasswordValidation.validatePassword(teacherDto.getPassword(), model);
        if (result.hasErrors() || model.containsAttribute("blank")
                || model.containsAttribute("invalidPassword")
                || model.containsAttribute("invalidEmail")
                || model.containsAttribute("duplicated")) {

            return TEACHER_HTML;
        }

        teacherDto.setPassword(bcryptPasswordEncoder.encode(teacherDto.getPassword()));
        Teacher teacher = teacherService.create(teacherDto);

        if (!multipartFile.isEmpty()) {
            teacherService.addProfilePicture(teacher, multipartFile);
        }
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "Shows selected teacher's profile")
    public String openTeacherProfile(@PathVariable("id") Long id, Model model) {
        TeacherDto existingTeacher = teacherService.findById(id);
        model.addAttribute("name_surname", TeacherMapper.
                toTeacher(existingTeacher,
                        userService.findByEmail(existingTeacher.getEmail())).getNameSurname());
        model.addAttribute("teacher", existingTeacher);
        return PROFILE;
    }

    @PostMapping("/{id}/profile")
    @Operation(summary = "Edits selected teacher's profile")
    public String editTeacherPersonalInformation(@ModelAttribute("teacher") @Valid TeacherDto updatableTeacher,
                                                 BindingResult result, @PathVariable("id") Long id, Model model) {
        TeacherDto existingTeacher = teacherService.findById(id);
        model.addAttribute("name_surname", TeacherMapper.toTeacher(existingTeacher,
                userService.findByEmail(existingTeacher.getEmail())).getNameSurname());
        if (!result.hasFieldErrors("email")) {
            if (!updatableTeacher.getEmail().equals(existingTeacher.getEmail())) {
                userService.checkDuplicationOfEmail(updatableTeacher.getEmail(), model);
            }
            EmailValidation.validate(updatableTeacher.getEmail(), model);
        }

        if (result.hasErrors() || model.containsAttribute("invalidEmail") ||
                model.containsAttribute("duplicated")) {
            return PROFILE;
        }
        teacherService.updateFields(updatableTeacher);
        return "redirect:/teachers/" + id + "/profile";
    }

    @PostMapping("/{id}/image/add")
    @Operation(summary = "Adds image to selected teacher's profile")
    public String addPic(@PathVariable("id") Long id, @RequestParam("picture") MultipartFile multipartFile) {
        TeacherDto teacherById = teacherService.findById(id);
        User userByEmail = userService.findByEmail(teacherById.getEmail());
        teacherService.addProfilePicture(TeacherMapper.toTeacher(teacherById, userByEmail), multipartFile);
        return "redirect:/teachers/" + id + "/profile";
    }

    @GetMapping("/{id}/image/delete")
    @Operation(summary = "Deletes image from selected teacher's profile")
    public String deletePic(@PathVariable("id") Long id) {
        TeacherDto teacherById = teacherService.findById(id);
        String picUrl = teacherById.getPicUrl();
        imageService.deleteImage(picUrl);
        teacherService.deletePic(teacherById.getId());
        return "redirect:/teachers/" + id + "/profile";
    }

    @GetMapping("/{id}/courses")
    @Operation(summary = "Gets the list of courses thw teacher has and shows them")
    public String coursesPageInTeacherProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("teachersCourses", academicCourseService.findAcademicCoursesByTeacherId(id));
        return COURSES_FOR_TEACHER;
    }

    @GetMapping("/{id}/subjects")
    @Operation(summary = "Gets the list of subjects thw teacher has and shows them")
    public String openSubjectsForTeacherProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("subjects", subjectService.findSubjectsByTeacherSetId(id));
        model.addAttribute("teacher", teacherService.findById(id));
        return SUBJECTS_FOR_TEACHER;
    }
}
