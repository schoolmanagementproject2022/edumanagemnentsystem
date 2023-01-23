package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.ACADEMIC_YEAR_SECTION;

@Controller
@RequestMapping("/years")
@Tag(name = "Academic year")
public class AcademicYearController {

    private final AcademicYearService academicYearService;
    private final String invalidMsg = "Wrong selected dates";
    private final String minRangeMsg = "Academic Year can’t be less than 30 days";
    private final LocalDate now = LocalDate.now();

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping
    @Operation(summary = "Gets all academic years and shows them on admin's dashboard")
    public String openAcademicYearSection(Model model) {
        setAttributesInYearSection(model);
        return ACADEMIC_YEAR_SECTION;
    }

    @PostMapping
    @Operation(summary = "Saves the created academic year")
    public String create(@ModelAttribute("academicYear") @Valid AcademicYearDto academicYear,
                         BindingResult result, Model model) {
        model.addAttribute("academicYears", academicYearService.findAll());

        String checkedValidationOfDates = validateStartAndEndDates(result, model, academicYear.getStartDate(),
                academicYear.getEndDate());
        if (checkedValidationOfDates.equals("academicYearSection")) {
            return checkedValidationOfDates;
        }
        academicYearService.save(academicYear);
        return "redirect:/years";
    }

    private void setAttributesInYearSection(Model model) {
        model.addAttribute("academicYears", academicYearService.findAll());
        model.addAttribute("academicYear", new AcademicYear());
    }

    private String validateStartAndEndDates(BindingResult result, Model model, LocalDate startDate, LocalDate endDate) {
        if (result.hasFieldErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                }
                return ACADEMIC_YEAR_SECTION;
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                }
                return ACADEMIC_YEAR_SECTION;
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                return ACADEMIC_YEAR_SECTION;
            }
        }
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            return ACADEMIC_YEAR_SECTION;
        } else if (endDate.getYear() == startDate.getYear()) {
            if (endDate.getMonth() == startDate.getMonth()) {
                if (startDate.plusDays(29).isAfter(endDate)) {
                    model.addAttribute("min", minRangeMsg);
                    return ACADEMIC_YEAR_SECTION;
                }
            } else if (endDate.isBefore(startDate.plusDays(29))) {
                model.addAttribute("min", minRangeMsg);
                return ACADEMIC_YEAR_SECTION;
            }
        } else if (endDate.getYear() > now.getYear() + 10) {
            model.addAttribute("max", "Academic Year can’t be more than 10 year");
            return ACADEMIC_YEAR_SECTION;
        }
        return "Passed";
    }

}
