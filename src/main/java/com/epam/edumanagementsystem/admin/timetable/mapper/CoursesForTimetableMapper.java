package com.epam.edumanagementsystem.admin.timetable.mapper;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;

import java.util.ArrayList;
import java.util.List;

public class CoursesForTimetableMapper {

    private static CoursesForTimetableService coursesForTimetableService;

    public static CoursesForTimetable toCoursesForTimetable(CoursesForTimetableDto coursesForTimetableDto) {
        CoursesForTimetable coursesForTimetable = new CoursesForTimetable();

        coursesForTimetable.setId(coursesForTimetableDto.getId());
        coursesForTimetable.setAcademicCourse(coursesForTimetableDto.getAcademicCourse());
        coursesForTimetable.setAcademicClass(List.of(coursesForTimetableDto.getAcademicClass()));
        coursesForTimetable.setDayOfWeek(coursesForTimetableDto.getDayOfWeek());
        coursesForTimetable.setStatus(coursesForTimetableDto.getStatus());

        return coursesForTimetable;
    }

    public static CoursesForTimetableDto toCoursesForTimetableDto(CoursesForTimetable coursesForTimetable) {
        CoursesForTimetableDto coursesForTimetableDto = new CoursesForTimetableDto();

        coursesForTimetableDto.setId(coursesForTimetable.getId());
        coursesForTimetableDto.setAcademicCourse(coursesForTimetable.getAcademicCourse());
        coursesForTimetableDto.setAcademicClass(coursesForTimetable.getAcademicClass().get(0));
        coursesForTimetableDto.setDayOfWeek(coursesForTimetable.getDayOfWeek());
        coursesForTimetableDto.setStatus(coursesForTimetable.getStatus());

        return coursesForTimetableDto;
    }

    public static List<CoursesForTimetable> toListOfCoursesForTimetable(List<CoursesForTimetableDto> listOfCoursesForTimetableDtos) {
        List<CoursesForTimetable> coursesForTimetable = new ArrayList<>();

        for (CoursesForTimetableDto coursesForTimetableDto : listOfCoursesForTimetableDtos) {
            coursesForTimetable.add(toCoursesForTimetable(coursesForTimetableDto));
        }
        return coursesForTimetable;
    }
}
