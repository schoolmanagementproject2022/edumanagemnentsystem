package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Set;

@Component
public class UtilForTimetableController {

    private static CoursesForTimetableService coursesService;

    public UtilForTimetableController(CoursesForTimetableService coursesService) {
        UtilForTimetableController.coursesService = coursesService;
    }

    public static void putLessons(Model model, Long academicClassId) {
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndAcademicClassId("Monday", academicClassId));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndAcademicClassId("Tuesday", academicClassId));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndAcademicClassId("Wednesday", academicClassId));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndAcademicClassId("Thursday", academicClassId));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndAcademicClassId("Friday", academicClassId));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndAcademicClassId("Saturday", academicClassId));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndAcademicClassId("Sunday", academicClassId));
    }

    public static void putEditedLessons(Model model, Long academicClassId) {
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Monday", "Edit", academicClassId));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Tuesday", "Edit", academicClassId));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Wednesday", "Edit", academicClassId));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Thursday", "Edit", academicClassId));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Friday", "Edit", academicClassId));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Saturday", "Edit", academicClassId));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Sunday", "Edit", academicClassId));
    }

    public static void sendToFrontCurrentTimetableAndLessonId(Model model, TimetableDto currentTimetable, Long lessonId) {
        model.addAttribute("timetable", currentTimetable);
        model.addAttribute("lessonId", lessonId);
    }

    public static void sendToFrontNewTimetableAndAcademicClassName(Model model, String academicClassName) {
        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
    }

    public static void sendToFrontAllAcademicCoursesAndAcademicClass(Model model, Set<AcademicCourseDto> allAcademicCourses, AcademicClassDto academicClass) {
        model.addAttribute("academicClass", academicClass);
        model.addAttribute("courses", allAcademicCourses);
    }

    public static void sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(Model model, Set<AcademicCourseDto> allAcademicCourses, AcademicClassDto academicClass) {
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
    }

    public static void sendToFrontAllNecessaryInfoAfterDateFailValidation(Model model, Set<AcademicCourseDto> allAcademicCourses,
                                                                          AcademicClassDto academicClass, Long academicClassId) {
        sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
        putEditedLessons(model, academicClassId);
    }


}