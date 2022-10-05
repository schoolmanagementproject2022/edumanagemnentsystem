package com.epam.edumanagementsystem.admin.timetable.mapper;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.ArrayList;
import java.util.List;

public class CoursesForTimetableMapper {
    public static CoursesForTimetable toCoursesForTimetable(CoursesForTimetableDto coursesForTimetableDto) {
        CoursesForTimetable coursesForTimetable = new CoursesForTimetable();
//        coursesForTimetable.setId(coursesForTimetableDto.getId());
        coursesForTimetable.setAcademicCourse(List.of(coursesForTimetableDto.getAcademicCourse()));
        coursesForTimetable.setAcademicClass(List.of(coursesForTimetableDto.getAcademicClass()));
        coursesForTimetable.setDayOfWeek(coursesForTimetableDto.getDayOfWeek());
        return coursesForTimetable;
    }

//    public static CoursesForTimetableDto toDto(CoursesForTimetable coursesForTimetable) {
//        CoursesForTimetableDto coursesForTimetableDto = new CoursesForTimetableDto();
//        coursesForTimetableDto.setId(coursesForTimetable.getId());
//        coursesForTimetableDto.s(coursesForTimetable.getName());  լիստի տվյալները դարձնել օբյեկտ՞՞՞՞՞
//        coursesForTimetableDto.setSubject(coursesForTimetable.getSubject());
//        return coursesForTimetableDto;
//    }
//
//    public static List<AcademicCourseDto> toListOfAcademicCourseDto(List<AcademicCourse> academicCourses) {
//        List<AcademicCourseDto> academicCourseDtos = new ArrayList<>();
//        for (AcademicCourse academicCourse : academicCourses) {
//            academicCourseDtos.add(toDto(academicCourse));
//        }
//        return academicCourseDtos;
//    }

    public static List<CoursesForTimetable> toListOfCoursesForTimetable(List<CoursesForTimetableDto> listOfCoursesForTimetableDtos) {
        List<CoursesForTimetable> coursesForTimetable = new ArrayList<>();
        for (CoursesForTimetableDto coursesForTimetableDto : listOfCoursesForTimetableDtos) {
            coursesForTimetable.add(toCoursesForTimetable(coursesForTimetableDto));
        }
        return coursesForTimetable;
    }
}
