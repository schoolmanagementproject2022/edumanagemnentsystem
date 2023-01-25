package com.epam.edumanagementsystem.util.shceduledJobs;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.util.entity.DoneCourses;
import com.epam.edumanagementsystem.util.service.DoneCoursesService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.logging.Logger;

@EnableScheduling
@Configuration
public class ScheduleConfig {

    private final CoursesForTimetableService coursesForTimetableService;
    private final TimetableService timetableService;
    private final AcademicCourseService academicCourseService;
    private final DoneCoursesService doneCoursesService;
    private final Logger logger = Logger.getLogger(ScheduleConfig.class.getName());

    public ScheduleConfig(CoursesForTimetableService coursesForTimetableService, TimetableService timetableService, AcademicCourseService academicCourseService, DoneCoursesService doneCoursesService) {
        this.coursesForTimetableService = coursesForTimetableService;
        this.timetableService = timetableService;
        this.academicCourseService = academicCourseService;
        this.doneCoursesService = doneCoursesService;
    }

    @Scheduled(cron = "0/50 * * * * ?")
    @Transactional
    public void scheduleFixedRateTask() {
        LocalDate now = LocalDate.now();
        coursesForTimetableService.findAllByDayOfWeek(capitalize(now))
        .stream()
                .filter(course -> !now.isBefore(timetableService
                        .findTimetableByAcademicClassId(course.getAcademicClass().get(0).getId()).getStartDate()))
                .forEach(course -> doneCoursesService.save(new DoneCourses(AcademicCourseMapper
                        .toAcademicCourse(academicCourseService.findByName(course.getAcademicCourse())),
                        course.getAcademicClass().get(0), now)));
        logger.info("Added done courses to table");
    }

    private String capitalize(LocalDate now) {
        String lowerCase = now.getDayOfWeek().minus(1).name().toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }

}
