package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradesService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/grades")
@Tag(name = "Grades in journal")
public class GradesController {

    private final GradesService gradeService;
    private final AcademicClassService academicClassService;


    public GradesController(GradesService gradeService, AcademicClassService academicClassService) {
        this.gradeService = gradeService;
        this.academicClassService = academicClassService;
    }

    @PostMapping("/add")
    public String addGrade(@ModelAttribute(value = "saveGrade") @Valid GradesDto gradesDto, BindingResult result, Model model,
                           @RequestParam(value = "classId", required = false) Long classId,
                           @RequestParam(value = "studentId", required = false) Long studentId,
                           @RequestParam(name = "date", required = false) String date) {


        AcademicClassDto academicClass = academicClassService.findById(classId);
        if (result.hasFieldErrors("gradeHomework") || result.hasFieldErrors("gradeTest") || result.hasFieldErrors("gradeClasswork")) {
            model.addAttribute("gradesDto", gradesDto);
            return "forward:/classes/" + academicClass.getClassNumber() + "/journal/" + gradesDto.getCourseId();
        }

        if (gradeService.existByStudentIdAndDate(studentId, date)) {
            gradeService.update(gradesDto, date, studentId);
            return "redirect:/classes/" + academicClass.getClassNumber() + "/journal/" + gradesDto.getCourseId() + "?date=" + date;
        }

        gradeService.save(gradesDto, date, studentId);
        return "redirect:/classes/" + academicClass.getClassNumber() + "/journal/" + gradesDto.getCourseId() + "?date=" + date;

    }
}
