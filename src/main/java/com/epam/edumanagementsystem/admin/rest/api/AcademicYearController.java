package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.hibernate.type.LocalDateType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/years")
public class AcademicYearController {

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    private final AcademicYearService academicYearService;
    private final String startDateBiggerEndDate = "Start Date can not be bigger then End Date";
    private final String endDateVeryBigger = "End Date can not be bigger Start Date then 10 year";

    @GetMapping
    public String openAcademicYearSection(Model model) {
        List<AcademicYear> academicYears = academicYearService.findAll();
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("academicYear", new AcademicYear());
        return "academicYearSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicYear") @Valid AcademicYear academicYear,
                         BindingResult result, Model model) {
        List<AcademicYear> academicYearList = academicYearService.findAll();
        model.addAttribute("academicYears", academicYearList);
        LocalDate date = LocalDate.parse("2020-01-08");
        int year = date.getYear();
        if (academicYear.getStartDate() < year) {
            String startDateSmaller = "Start Date can not be smaller then" + year;
            result.addError(new ObjectError(startDateSmaller, "Start Date smaller then this year"));
            if (result.hasErrors()) {
                model.addAttribute("DateValidError", startDateSmaller);
                return "academicYearSection";
            }
        } else if (academicYear.getEndDate() < academicYear.getStartDate()) {

            result.addError(new ObjectError(startDateBiggerEndDate, "Start Date bigger then End Date"));
            if (result.hasErrors()) {
                model.addAttribute("DateValidError", startDateBiggerEndDate);
                return "academicYearSection";
            }
        } else if ((academicYear.getEndDate() - academicYear.getStartDate()) > 10) {
            result.addError(new ObjectError(endDateVeryBigger, "End Date very bigger"));
            if (result.hasErrors()) {
                model.addAttribute("DateValidError", endDateVeryBigger);
                return "academicYearSection";
            }
        } else {
            academicYearService.create(academicYear);
            return "redirect:/years";
        }
        return "academicYearSection";
    }
}
