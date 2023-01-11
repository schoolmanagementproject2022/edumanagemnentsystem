package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.JournalService;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.*;
import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.putLessons;

@Service
public class JournalServiceImpl implements JournalService {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final TimetableService timetableService;
    private final CoursesForTimetableService coursesForTimetableService;

    public JournalServiceImpl(AcademicClassService academicClassService, AcademicCourseService academicCourseService,
                              TimetableService timetableService, CoursesForTimetableService coursesForTimetableService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.timetableService = timetableService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public void openJournal(String date, String startDate, String name, Model model) {
        if (date != null) {
            startDate = date;
        }
        AcademicClassDto academicClassByName = academicClassService.findByClassNumber(name);

        LocalDate timetableStartDate = timetableService.findTimetableByAcademicClassName(name).getStartDate();
        LocalDate timetableEndDate = timetableService.findTimetableByAcademicClassName(name).getEndDate();
        model.addAttribute(START_DATE, timetableStartDate);
        model.addAttribute(END_DATE, timetableEndDate);
        LocalDate journalStartDate = null;

        if (startDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            LocalDate localdate = LocalDate.parse(startDate, formatter);
            if (!localdate.isAfter(timetableEndDate) && !localdate.isBefore(timetableStartDate)) {
                journalStartDate = localdate;
            } else if (!localdate.isAfter(timetableStartDate)) {
                journalStartDate = timetableStartDate;
            } else if (!localdate.isBefore(timetableEndDate)) {
                journalStartDate = timetableEndDate;
            }
        } else {
            if (timetableStartDate.isAfter(LocalDate.now())) {
                journalStartDate = timetableStartDate;
            } else {
                journalStartDate = LocalDate.now();
            }
        }
        journalStartDate = DateUtil.recurs(journalStartDate);
        List<AcademicCourseDto> academicCoursesInClassDto = academicCourseService.findAllAcademicCoursesInClassByName(name);
        model.addAttribute(ALL_COURSES_IN_ACADEMIC_CLASS, academicCoursesInClassDto);
        boolean existDay = false;
        for (int i = 0; i < 7; i++) {
            existDay = getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate, existDay);
            journalStartDate = journalStartDate.plusDays(1);
        }
        if (!existDay) {
            if (journalStartDate.isAfter(timetableEndDate)) {
                journalStartDate = journalStartDate.minusDays(14);
            }
            for (int i = 0; i < 7; i++) {
                getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate, existDay);
                journalStartDate = journalStartDate.plusDays(1);
            }
        }
        String journalStartDateToString = journalStartDate.toString();
        model.addAttribute("month", journalStartDate.getMonth());
        model.addAttribute("year", journalStartDate.getYear());
        model.addAttribute("startDate", journalStartDateToString);
    }

    @Override
    public void doNotOpenJournal_timetableIsNotExist(String date, String startDate, String className, Model model) {
        if (date != null) {
            startDate = date;
        }
        model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(className));
        model.addAttribute("creationStatus", false);
        putLessons(model, academicClassService.findByClassNumber(className).getId());
    }

    private boolean getCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClassDto academicClassByNameDto,
                                         LocalDate timetableStartDate, LocalDate timetableEndDate, boolean existDay) {

        String deyOfWeek = journalStartDate.getDayOfWeek().toString();
        model.addAttribute(deyOfWeek, journalStartDate);
        String day = StringUtils.capitalize(deyOfWeek.toLowerCase(Locale.ROOT));
        List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                .getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(day, "Active", academicClassByNameDto.getId());
        model.addAttribute(deyOfWeek.toLowerCase(Locale.ROOT), coursesByDayOfWeekAndStatusAndAcademicClassId);
        if (!coursesByDayOfWeekAndStatusAndAcademicClassId.isEmpty() &&
                !journalStartDate.isBefore(timetableStartDate) && !journalStartDate.isAfter(timetableEndDate)) {
            model.addAttribute(day, true);
            existDay = true;
        } else {
            model.addAttribute(day, false);
        }
        return existDay;
    }

}
