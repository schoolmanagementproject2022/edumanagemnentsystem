package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.rest.service.JournalService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/classes")
@Tag(name = "Journal")
public class JournalController {

    private final TimetableService timetableService;
    private final JournalService journalService;
    private final AcademicCourseService academicCourseService;
    private final AcademicClassService academicClassService;
    private final StudentService studentService;

    private static final String JOURNAL_HTML = "journal";
    private static final String JOURNAL_WITH_COURSE_INFO_HTML = "journalWithCourseInfo";
    private static final String CREATE_TIMETABLE_MSG_FROM_JOURNAL_HTML = "createTimetableMsgFromJournal";

    public JournalController(TimetableService timetableService, JournalService journalService,
                             AcademicCourseService academicCourseService, AcademicClassService academicClassService,
                             StudentService studentService) {
        this.timetableService = timetableService;
        this.journalService = journalService;
        this.academicCourseService = academicCourseService;
        this.academicClassService = academicClassService;
        this.studentService = studentService;
    }

    @GetMapping("/{name}/journal")
    @Operation(summary = "Shows journal page")
    public String journal(Model model, @PathVariable("name") String name, @RequestParam(name = "date", required = false) String date,
                          @RequestParam(name = "startDate", required = false) String startDate,
                          @RequestParam(name = "courseId", required = false) Long courseId) {
        if (timetableService.existTimetableByClassId(academicClassService.findByClassNumber(name).getId())) {
            journalService.openJournal(date, startDate, name, model, courseId);
            return JOURNAL_HTML;
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date, startDate, name, model);
            return CREATE_TIMETABLE_MSG_FROM_JOURNAL_HTML;
        }
    }

    @GetMapping("/{name}/journal/{courseId}")
    public String journalWithCourseInfo(@PathVariable("name") String name, @PathVariable("courseId") Long courseId,
                                        @RequestParam(name = "date", required = false) String date,
                                        @RequestParam(name = "startDate", required = false) String startDate,
                                        @RequestParam(value = "allFieldsBlankMessage", required = false) String allFieldsBlankMessage,
                                        @RequestParam(value = "concreteDay", required = false) String concreteDay,
                                        Model model) {
        model.addAttribute("startDateForDatePicker", timetableService.findTimetableByAcademicClassName(name).getStartDate());
        model.addAttribute("endDateForDatePicker", timetableService.findTimetableByAcademicClassName(name).getEndDate());

        if (allFieldsBlankMessage != null && !allFieldsBlankMessage.isBlank()) {
            model.addAttribute("allFieldsBlankMessage", allFieldsBlankMessage);
            model.addAttribute("concreteDay", concreteDay);
        }
        model.addAttribute("saveAgenda", new SaveAgendaDto());
        model.addAttribute("saveGrade", new GradesDto());

        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            setAttributesInJournalSectionWhenTimetableExist(model, name, courseId);
            if (studentService.findStudentsByClassName(name).isEmpty()) {
                return JOURNAL_WITH_COURSE_INFO_HTML;
            }

            journalService.openJournal(date, startDate, name, model, courseId);
            return JOURNAL_WITH_COURSE_INFO_HTML;
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date.split("/")[0], startDate, name, model);
            setAttributesInJournalSectionWhenTimetableNotExist(model, name);
            return CREATE_TIMETABLE_MSG_FROM_JOURNAL_HTML;
        }
    }

    @PostMapping("/{name}/journal/{courseId}")
    public String journalWithCourses(@ModelAttribute(value = "saveAgenda") @Valid SaveAgendaDto agendaDto, BindingResult result,
                                     @ModelAttribute(value = "saveGrade") @Valid GradesDto saveGrade, BindingResult bindingResult,
                                     @PathVariable("name") String name, @PathVariable("courseId") Long courseId,
                                     @RequestParam(name = "date", required = false) String date,
                                     @RequestParam(name = "startDate", required = false) String startDate,
                                     @RequestParam(value = "concreteDay", required = false) String concreteDay,
                                     Model model) {
        model.addAttribute("startDateForDatePicker", timetableService.findTimetableByAcademicClassName(name).getStartDate());
        model.addAttribute("endDateForDatePicker", timetableService.findTimetableByAcademicClassName(name).getEndDate());

        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            setAttributesInJournalSectionWhenTimetableExist(model, name, courseId);
            model.addAttribute("concreteDay", concreteDay);
            if (studentService.findStudentsByClassName(name).isEmpty()) {
                return JOURNAL_WITH_COURSE_INFO_HTML;
            }

            journalService.openJournal(date, startDate, name, model, courseId);
            return JOURNAL_WITH_COURSE_INFO_HTML;
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date.split("/")[0], startDate, name, model);
            setAttributesInJournalSectionWhenTimetableNotExist(model, name);
            return CREATE_TIMETABLE_MSG_FROM_JOURNAL_HTML;
        }
    }

    private void setAttributesInJournalSectionWhenTimetableExist(Model model, String className, Long courseId) {
        model.addAttribute("course", academicCourseService.findById(courseId));
        model.addAttribute("class", academicClassService.findByClassNumber(className));
        model.addAttribute("allStudentsInAcademicClass", studentService.findStudentsByClassName(className));
    }

    private void setAttributesInJournalSectionWhenTimetableNotExist(Model model, String className) {
        model.addAttribute("class", academicClassService.findByClassNumber(className));
        model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(className));
        model.addAttribute("creationStatus", false);
    }
}
