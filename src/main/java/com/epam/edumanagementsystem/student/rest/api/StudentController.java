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
import com.epam.edumanagementsystem.student.rest.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final StudentServices studentService;
    private final ParentService parentService;
    private final AcademicClassService academicClassService;


    @Autowired
    public StudentController(StudentServices studentService,
                             ParentService parentService,
                             AcademicClassService academicClassService) {
        this.studentService = studentService;
        this.parentService = parentService;
        this.academicClassService = academicClassService;
    }

    public List<StudentDto> findAllStudents() {
        return studentService.findAll();
    }

    public List<ParentDto> findAllParents() {
        return ParentMapper.toParentDtoList(parentService.parents());
    }

    public List<AcademicClassDto> findAllClasses(){
        return academicClassService.findAll();
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
    public String createStudentSection(@ModelAttribute("student") @Valid Student student,
                                BindingResult result,
                                       Model model) {
        model.addAttribute("students", findAllStudents());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("parents", ParentMapper.toParentList(findAllParents()));
        model.addAttribute("classes", findAllClasses());
        for (Student students : StudentMapper.toStudentList(findAllStudents())) {
            if (student.getEmail().equals(students.getEmail())) {
                model.addAttribute("duplicated", "A user with the specified email already exists");
                return "studentSection";
            }
        }
        if (result.hasErrors()) {
            return "studentSection";
        } else {
            studentService.create(student);
            return "redirect:/students";
        }
    }
}
