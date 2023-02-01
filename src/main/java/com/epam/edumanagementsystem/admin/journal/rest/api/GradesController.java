package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradesService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/grades")
@Tag(name = "Grades in journal")
public class GradesController {
   private final GradesService gradeService;
   private final AcademicClassService academicClassService;
   private final StudentService studentService;

    public GradesController(GradesService gradeService, AcademicClassService academicClassService, StudentService service) {
        this.gradeService = gradeService;
        this.academicClassService = academicClassService;
        this.studentService = service;
    }
    @PostMapping("/add")
    public String addGrade(@ModelAttribute(value = "saveGrade")  GradesDto gradesDto, BindingResult result, Model model,
                           @RequestParam(value = "classId", required = false) Long classId,
                           @RequestParam(value = "studentId", required = false) Long studentId,
                           @RequestParam(value = "courseId", required = false) Long courseId,
                           @RequestParam(name = "date", required = false) String date,
                           @RequestParam(name = "classwork", required = false) String classwork
                             ) {
        AcademicClassDto academicClass = academicClassService.findById(classId);
        StudentDto student =studentService.findById(studentId);
        gradesDto.setStudent(StudentMapper.mapToStudent(student));
        gradeService.save(gradesDto);

        return "redirect:/classes/" + academicClass.getClassNumber() + "/journal/" + courseId + "?date=" + date;

    }
}
