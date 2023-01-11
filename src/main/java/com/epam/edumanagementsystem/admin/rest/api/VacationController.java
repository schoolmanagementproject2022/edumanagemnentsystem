package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.rest.service.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.VACATION_REDIRECT;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.VACATION_SECTION;

@Controller
@RequestMapping("/vacations")
@Tag(name = "Vacation")
public class VacationController {
    private final VacationService vacationService;

    @Autowired
    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    @Operation(summary = "Gets the vacations' list and shows them on admin's dashboard")
    public String openVacationSection(Model model) {
        List<VacationDto> vacationDtoList = vacationService.findAll();
        model.addAttribute("vacations", vacationDtoList);
        model.addAttribute("vacation", new VacationDto());
        return VACATION_SECTION;
    }

    @PostMapping
    @Operation(summary = "Saves the created vacation")
    public String create(@ModelAttribute("vacation") @Valid VacationDto vacationDto,
                         BindingResult result, Model model) {
        model.addAttribute("vacations", vacationService.findAll());
        if (result.hasErrors()) {
            if (result.hasFieldErrors("startDate")) {
                return VACATION_SECTION;
            }
            if (result.hasFieldErrors("endDate")) {
                return VACATION_SECTION;
            }
        }
        if (vacationDto.getStartDate().isBefore(LocalDate.now()) || vacationDto.getStartDate().isAfter(vacationDto.getEndDate())) {
            model.addAttribute("invalid", "Wrong selected dates");
            return VACATION_SECTION;
        } else {
            vacationService.save(vacationDto);
            return VACATION_REDIRECT;
        }
    }
}

