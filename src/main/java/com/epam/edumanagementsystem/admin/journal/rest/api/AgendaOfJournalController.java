package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.rest.service.JournalService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/agenda")
@Tag(name = "Agenda")
public class AgendaOfJournalController {

    private final AcademicClassService academicClassService;
    private final JournalService journalService;

    public AgendaOfJournalController(AcademicClassService academicClassService, JournalService journalService) {
        this.journalService = journalService;
        this.academicClassService = academicClassService;
    }

    @PostMapping("/add")
    public String addAgenda(@ModelAttribute(value = "saveAgenda") @Valid SaveAgendaDto agendaDto, BindingResult result,
                            @RequestParam("concreteDay") String concreteDay, Model model,
                            RedirectAttributes redirectAttributes) {
        AcademicClassDto classById = academicClassService.findById(agendaDto.getClassId());
        if (result.hasErrors()) {
            model.addAttribute("saveAgenda", agendaDto);
            return "forward:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId();
        }

        if (agendaDto.getClasswork().isBlank() && agendaDto.getTest().isBlank() && agendaDto.getHomework().isBlank()) {
            redirectAttributes.addAttribute("allFieldsBlankMessage", "At least one type has to be chosen");
            redirectAttributes.addAttribute("concreteDay", concreteDay);
            return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId();
        }

        journalService.checksUpdateOrSaveObjectsAndDoes(agendaDto);
        return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId() + "?date=" + agendaDto.getDate();
    }

}