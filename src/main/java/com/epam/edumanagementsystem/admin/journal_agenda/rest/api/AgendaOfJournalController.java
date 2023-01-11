package com.epam.edumanagementsystem.admin.journal_agenda.rest.api;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.JournalService;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.TestService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/agenda")
@Tag(name = "Agenda")
public class AgendaOfJournalController {

    private final ClassworkService classworkService;
    private final HomeworkService homeworkService;
    private final TestService testService;
    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final StudentService studentService;
    private final TimetableService timetableService;
    private final JournalService journalService;

    public AgendaOfJournalController(ClassworkService classworkService, HomeworkService homeworkService,
                                     TestService testService,
                                     AcademicClassService academicClassService, AcademicCourseService academicCourseService, StudentService studentService, TimetableService timetableService, JournalService journalService) {
        this.classworkService = classworkService;
        this.homeworkService = homeworkService;
        this.testService = testService;
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.studentService = studentService;
        this.timetableService = timetableService;
        this.journalService = journalService;
    }

    @PostMapping("/add")
    public String addAgenda(@ModelAttribute(value = "saveAgenda") SaveAgendaDto agendaDto,
                            @RequestParam("concreteDay") String concreteDay,
                            RedirectAttributes redirectAttributes) {
        AcademicClassDto classById = academicClassService.findById(agendaDto.getClassId());
        if (agendaDto.getClasswork().isBlank() && agendaDto.getTest().isBlank() && agendaDto.getHomework().isBlank()) {
            redirectAttributes.addAttribute("allFieldsBlankMessage", "At least one type has to be chosen");
            redirectAttributes.addAttribute("concreteDay", concreteDay);
            return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId() + "?date=" + agendaDto.getDate();
        }

        if (classworkService.getClassWorkOfCourse(LocalDate.parse(agendaDto.getDate()), agendaDto.getClassId(), agendaDto.getCourseId()) != null) {
            classworkService.update(agendaDto);
        } else if (!agendaDto.getClasswork().isBlank()) {
            classworkService.save(agendaDto);
        }

        if (testService.getTestOfCourse(LocalDate.parse(agendaDto.getDate()), agendaDto.getClassId(), agendaDto.getCourseId()) != null) {
            testService.update(agendaDto);
        } else if (!agendaDto.getTest().isBlank()) {
            testService.save(agendaDto);
        }

        if (homeworkService.getHomeworkOfCourse(LocalDate.parse(agendaDto.getDate()), agendaDto.getClassId(), agendaDto.getCourseId()) != null) {
            homeworkService.update(agendaDto);
        } else if (!agendaDto.getHomework().isBlank()) {
            homeworkService.save(agendaDto);
        }
        return "redirect:/classes/" + classById.getClassNumber() + "/journal/" + agendaDto.getCourseId() + "?date=" + agendaDto.getDate();
    }

    @GetMapping("/{name}/journal")
    public String journal(Model model, @PathVariable("name") String name, @RequestParam(name = "date", required = false) String date,
                          @RequestParam(name = "startDate", required = false) String startDate) {
        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            journalService.openJournal(date, startDate, name, model);
            return "journal";
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date, startDate, name, model);
            return "createTimetableMsgFromJournal";
        }
    }

    @GetMapping("/{name}/journal/{courseId}")
    public String journalWithCourseInfo(@PathVariable("name") String name, @PathVariable("courseId") Long courseId,
                                        @RequestParam(name = "date", required = false) String date,
                                        @RequestParam(name = "startDate", required = false) String startDate,
                                        @RequestParam(value = "allFieldsBlankMessage", required = false) String allFieldsBlankMessage,
                                        @RequestParam(value = "concreteDay", required = false) String concreteDay,
                                        Model model) {
        if (allFieldsBlankMessage != null && !allFieldsBlankMessage.isBlank()) {
            model.addAttribute("allFieldsBlankMessage", allFieldsBlankMessage);
            model.addAttribute("concreteDay", concreteDay);
        }

        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            model.addAttribute("course", academicCourseService.findById(courseId));
            model.addAttribute("class", academicClassService.findByClassNumber(name));
//            List<StudentDto> studentsInClass = studentService.findStudentsByClassName(name);
//            model.addAttribute("allStudentsInAcademicClass", studentsInClass);
//            if (studentsInClass.isEmpty()) {
//                return "journalWithCourseInfo";
//            }
//
//            journalService.openJournal(date, startDate, name, model);
            return "journalWithCourseInfo";
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date.split("/")[0], startDate, name, model);
            model.addAttribute("class", academicClassService.findByClassNumber(name));
            model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(name));
            model.addAttribute("creationStatus", false);
            return "createTimetableMsgFromJournal";
        }
    }
}
