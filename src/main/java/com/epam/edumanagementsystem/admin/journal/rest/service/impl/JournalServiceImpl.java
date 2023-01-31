package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.JournalService;
import com.epam.edumanagementsystem.admin.journal.rest.service.TestService;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.util.DateUtil;
import com.epam.edumanagementsystem.util.entity.DoneCourses;
import com.epam.edumanagementsystem.util.service.DoneCoursesService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER_JOURNAL;
import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.putLessons;

@Service
public class JournalServiceImpl implements JournalService {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final TimetableService timetableService;
    private final ClassworkService classworkService;
    private final DoneCoursesService doneCoursesService;
    private final TestService testService;
    private final HomeworkService homeworkService;
    private final CoursesForTimetableService coursesForTimetableService;

    public JournalServiceImpl(AcademicClassService academicClassService, AcademicCourseService academicCourseService,
                              TimetableService timetableService, ClassworkService classworkService, DoneCoursesService doneCoursesService, TestService testService, HomeworkService homeworkService, CoursesForTimetableService coursesForTimetableService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.timetableService = timetableService;
        this.classworkService = classworkService;
        this.doneCoursesService = doneCoursesService;
        this.testService = testService;
        this.homeworkService = homeworkService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public void openJournal(String date, String startDate, String name, Model model, Long courseId) {
        if (date != null) {
            startDate = date;
        }
        AcademicClassDto academicClassByName = academicClassService.findByClassNumber(name);

        LocalDate timetableStartDate = timetableService.findTimetableByAcademicClassName(name).getStartDate();
        LocalDate timetableEndDate = timetableService.findTimetableByAcademicClassName(name).getEndDate();
        model.addAttribute("startDateForDatePicker", timetableStartDate);
        model.addAttribute("endDateForDatePicker", timetableEndDate);
        LocalDate journalStartDate = null;

        if (startDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_JOURNAL);
            LocalDate localdate = LocalDate.parse(startDate, formatter);
            if (localdate.isBefore(timetableEndDate) && localdate.isAfter(timetableStartDate)) {
                journalStartDate = localdate;
            } else if (localdate.isBefore(timetableStartDate)) {
                journalStartDate = timetableStartDate;
            } else if (localdate.isAfter(timetableEndDate)) {
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

        Set<AcademicCourseDto> academicCoursesInClassDto = new LinkedHashSet<>();

//        if (journalStartDate.isBefore(LocalDate.now()) && (timetableStartDate.isBefore(journalStartDate))) {
//            doneCoursesService.findAllByAcademicClassId(academicClassByName.getId())
//                    .forEach(doneCourse -> academicCoursesInClassDto
//                            .add(AcademicCourseMapper.toDto(doneCourse.getAcademicCourse())));
//        }
        LocalDate tmpForDoneCourses = journalStartDate;

        if (journalStartDate.isBefore(LocalDate.now()) && (timetableStartDate.isBefore(journalStartDate)
                || DateUtil.recurs(timetableStartDate).equals(journalStartDate))) {
            for (int i = 0; i < 7; i++) {
                Set<DoneCourses> allByAcademicClassIdAndDate = doneCoursesService.findAllByAcademicClassIdAndDate(academicClassByName.getId(), tmpForDoneCourses);
                if (!allByAcademicClassIdAndDate.isEmpty()) {
                    allByAcademicClassIdAndDate.forEach(doneCourses -> academicCoursesInClassDto
                            .add(AcademicCourseMapper.toDto(doneCourses.getAcademicCourse())));
                }
                tmpForDoneCourses = tmpForDoneCourses.plusDays(1);

                if (tmpForDoneCourses.equals(LocalDate.now())) {
                    break;
                }
            }
        }

        if (tmpForDoneCourses.equals(LocalDate.now()) || tmpForDoneCourses.isAfter(LocalDate.now()) ||
                DateUtil.recurs(timetableStartDate).equals(journalStartDate)) {
            coursesForTimetableService.getCoursesByAcademicClassId(academicClassByName.getId())
                    .stream()
                    .filter(coursesForTimetable -> coursesForTimetable.getAcademicCourse() != null)
                    .forEach(coursesForTimetable -> academicCoursesInClassDto.add(academicCourseService.findByName(coursesForTimetable.getAcademicCourse())));
        }

        model.addAttribute("allCoursesInAcademicClass", academicCoursesInClassDto);

        boolean existDay = false;
        for (int i = 0; i < 7; i++) {
            existDay = getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate, existDay, courseId);
            journalStartDate = journalStartDate.plusDays(1);
        }
        if (!existDay) {
            if (journalStartDate.isAfter(timetableEndDate)) {
                journalStartDate = journalStartDate.minusDays(14);
            } else if (journalStartDate.isAfter(LocalDate.now())) {
                journalStartDate = journalStartDate.minusDays(14);
            }

            for (int i = 0; i < 7; i++) {
                getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate,
                        existDay, courseId);
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
        model.addAttribute("creationStatus", false);
        putLessons(model, academicClassService.findByClassNumber(className).getId());
    }

    @Override
    public void checksUpdateOrSaveObjectsInAgendaAndDoes(SaveAgendaDto agendaDto) {
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
    }

    private boolean getCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClassDto academicClassByNameDto,
                                         LocalDate timetableStartDate, LocalDate timetableEndDate, boolean existDay, Long courseId) {

        String deyOfWeek = journalStartDate.getDayOfWeek().toString();
        model.addAttribute(deyOfWeek, journalStartDate);
        String day = StringUtils.capitalize(deyOfWeek.toLowerCase(Locale.ROOT));
        if (journalStartDate.isBefore(LocalDate.now()) && (timetableStartDate.isBefore(journalStartDate) || timetableStartDate.isEqual(journalStartDate))){
            List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                    .getDoneCoursesNamesByDayOfWeekAndAcademicClassId(day, academicClassByNameDto.getId(), journalStartDate);
            existDay = isExistDay(model, journalStartDate, timetableStartDate, timetableEndDate,
                    existDay, deyOfWeek, day, coursesByDayOfWeekAndStatusAndAcademicClassId, courseId);
        } else {
            List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                    .getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(day, "Active", academicClassByNameDto.getId());
            existDay = isExistDay(model, journalStartDate, timetableStartDate, timetableEndDate,
                    existDay, deyOfWeek, day, coursesByDayOfWeekAndStatusAndAcademicClassId, courseId);
        }
        return existDay;
    }

    private boolean isExistDay(Model model, LocalDate journalStartDate, LocalDate timetableStartDate, LocalDate timetableEndDate,
                               boolean existDay, String deyOfWeek, String day,
                               List<String> coursesByDayOfWeekAndStatusAndAcademicClassId, Long courseId) {
        model.addAttribute(deyOfWeek.toLowerCase(Locale.ROOT), coursesByDayOfWeekAndStatusAndAcademicClassId);

        if (courseId != null) {
            if (coursesByDayOfWeekAndStatusAndAcademicClassId.contains(academicCourseService.findById(courseId).getName()) &&
                    journalStartDate.isAfter(timetableStartDate) && journalStartDate.isBefore(timetableEndDate)) {
                model.addAttribute(day, true);
                existDay = true;
            } else {
                model.addAttribute(day, false);
            }
        } else {
            if (!coursesByDayOfWeekAndStatusAndAcademicClassId.isEmpty() &&
                    journalStartDate.isAfter(timetableStartDate) && journalStartDate.isBefore(timetableEndDate)) {
                model.addAttribute(day, true);
                existDay = true;
            } else {
                model.addAttribute(day, false);
            }
        }
        return existDay;
    }
}
