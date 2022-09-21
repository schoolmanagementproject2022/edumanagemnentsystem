package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/years")
public class AcademicYearController {
    private final AcademicYearService academicYearService;
    private final String invalidMsg = "Wrong selected dates";
    private final String minRangeMsg = "Academic Year can’t be less than 30 days";
    private final LocalDate now = LocalDate.now();

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping
    public String openAcademicYearSection(Model model) {
        List<AcademicYearDto> academicYears = academicYearService.findAll();
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("academicYear", new AcademicYear());
        return "academicYearSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicYear") @Valid AcademicYear academicYear,
                         BindingResult result, Model model) {

        LocalDate endDate = academicYear.getEndDate();
        LocalDate startDate = academicYear.getStartDate();
        List<AcademicYearDto> academicYears = academicYearService.findAll();
        model.addAttribute("academicYears", academicYears);

        if (result.hasErrors()) {
            if (result.hasFieldErrors("startDate") || result.hasFieldErrors("endDate")) {
                return "academicYearSection";
            }
        }

        if (startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            return "academicYearSection";
        } else if (startDate.isAfter(endDate)) {
            model.addAttribute("invalid", invalidMsg);
            return "academicYearSection";
        }

        if (endDate.getYear() == startDate.getYear()) {
            if (endDate.getMonth().getValue() == startDate.getMonth().getValue()) {
                model.addAttribute("min", minRangeMsg);
                return "academicYearSection";
            } else if (endDate.getMonth().getValue() - startDate.getMonth().getValue() == 1) {
                if (endDate.getMonth().maxLength() + endDate.getDayOfMonth() - startDate.getDayOfMonth() < 30) {
                    model.addAttribute("min", minRangeMsg);
                    return "academicYearSection";
                }
            }
        } else if (endDate.getYear() > now.getYear() + 10) {
            model.addAttribute("max", "Academic Year can’t be more than 10 year");
            return "academicYearSection";
        }
        academicYearService.create(academicYear);
        return "redirect:/years";
    }
}
