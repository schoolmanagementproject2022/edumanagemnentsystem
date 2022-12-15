package com.epam.edumanagementsystem.admin.journal_agenda.rest.api;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.TestService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agenda")
@Tag(name = "Agenda")
public class AgendaOfJournalController {

    private final ClassworkService classworkService;
    private final HomeworkService homeworkService;
    private final TestService testService;
    private final AcademicClassService academicClassService;

    public AgendaOfJournalController(ClassworkService classworkService, HomeworkService homeworkService,
                                     TestService testService,
                                     AcademicClassService academicClassService) {
        this.classworkService = classworkService;
        this.homeworkService = homeworkService;
        this.testService = testService;
        this.academicClassService = academicClassService;
    }

    @PostMapping("/add")
    public String addAgenda(@ModelAttribute(value = "saveAgenda") SaveAgendaDto agendaDto,
                            @RequestParam("concreteDay") String concreteDay,
                            RedirectAttributes redirectAttributes) {
        AcademicClassDto classById = academicClassService.getById(agendaDto.getClassId());
        if (agendaDto.getClasswork().isBlank() && agendaDto.getTest().isBlank() && agendaDto.getHomework().isBlank()) {
            redirectAttributes.addAttribute("allFieldsBlankMessage", "At least one type has to be chosen");
            redirectAttributes.addAttribute("concreteDay", concreteDay);
            return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId() + "?date=" + agendaDto.getDate();
        }
        if (!agendaDto.getClasswork().isBlank()) {
            classworkService.save(agendaDto);
        }
        if (!agendaDto.getHomework().isBlank()) {
            homeworkService.save(agendaDto);
        }
        if (!agendaDto.getTest().isBlank()) {
            testService.save(agendaDto);
        }
        return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId() + "?date=" + agendaDto.getDate();
    }

}
