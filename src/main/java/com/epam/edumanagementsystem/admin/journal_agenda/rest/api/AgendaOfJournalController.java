package com.epam.edumanagementsystem.admin.journal_agenda.rest.api;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.TestService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{classId}/{courseId}/add")
    public String addAgenda(@PathVariable("classId") Long classId,
                            @PathVariable("courseId") Long courseId,
                            @ModelAttribute(value = "saveAgenda") SaveAgendaDto agendaDto,
                            ModelMap modelMap, RedirectAttributes redirectAttributes) {
        AcademicClassDto classById = academicClassService.getById(classId);
        if (agendaDto.getClasswork().isBlank() && agendaDto.getTest().isBlank() && agendaDto.getHomework().isBlank()) {
            redirectAttributes.addAttribute("allFieldsBlankMessage", "At least one type has to be chosen");
            return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + courseId;
        }
        if (!agendaDto.getClasswork().isBlank()) {
            classworkService.save(agendaDto.getClasswork(), classId, courseId);
        }
        if (!agendaDto.getHomework().isBlank()) {
            homeworkService.save(agendaDto.getHomework(), classId, courseId);
        }
        if (!agendaDto.getTest().isBlank()) {
            testService.save(agendaDto.getTest(), classId, courseId);
        }
        return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + courseId;
    }

}
