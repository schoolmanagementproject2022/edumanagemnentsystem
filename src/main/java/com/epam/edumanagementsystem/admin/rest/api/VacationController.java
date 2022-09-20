package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.VacationMapper;
import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import com.epam.edumanagementsystem.admin.rest.service.VacationService;
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

@Controller
@RequestMapping("/vacations")
public class VacationController {

    private final VacationService vacationService;

    @Autowired
    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public String openVacationSection(Model model) {
        List<VacationDto> vacationDtoList = vacationService.findAll();
        model.addAttribute("vacations", vacationDtoList);
        model.addAttribute("vacation", new VacationDto());
        return "vacationSection";
    }

    @PostMapping
    public String create(@ModelAttribute("vacation") @Valid Vacation vacation,
                         BindingResult result, Model model) {
        List<VacationDto> vacationDtos = vacationService.findAll();
        model.addAttribute("vacations", vacationDtos);
        List<Vacation> vacations = VacationMapper.toListOfVacations(vacationDtos);

        if (result.hasErrors()) {
            if (result.hasFieldErrors("startDate")) {
                return "vacationSection";
            }
            if (result.hasFieldErrors("endDate")) {
                return "vacationSection";
            }
        }

        if (vacation.getStartDate().isBefore(LocalDate.now())) {
            model.addAttribute("invalid", "Wrong selected dates");
            return "vacationSection";
        } else if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            model.addAttribute("invalid", "Wrong selected dates");
            return "vacationSection";
        } else {
            vacationService.create(vacation);
            return "redirect:/vacations";
        }
    }

}

