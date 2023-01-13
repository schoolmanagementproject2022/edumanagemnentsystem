package com.epam.edumanagementsystem.admin.timetable.mapper;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoursesForTimetableMapper {

    private static AcademicCourseService academicCourseService;

    @Autowired
    public CoursesForTimetableMapper(AcademicCourseService academicCourseService) {
        CoursesForTimetableMapper.academicCourseService = academicCourseService;
    }

    public static CoursesForTimetable toCoursesForTimetable(CoursesForTimetableDto coursesForTimetableDto) {
        CoursesForTimetable coursesForTimetable = new CoursesForTimetable();

        coursesForTimetable.setId(coursesForTimetableDto.getId());
        coursesForTimetable.setAcademicCourse(coursesForTimetableDto.getAcademicCourse().getName());
        coursesForTimetable.setAcademicClass(List.of(coursesForTimetableDto.getAcademicClass()));
        coursesForTimetable.setDayOfWeek(coursesForTimetableDto.getDayOfWeek());
        coursesForTimetable.setStatus(coursesForTimetableDto.getStatus());
        return coursesForTimetable;
    }

    public static CoursesForTimetableDto toCoursesForTimetableDto(CoursesForTimetable coursesForTimetable) {
        CoursesForTimetableDto coursesForTimetableDto = new CoursesForTimetableDto();

        coursesForTimetableDto.setId(coursesForTimetable.getId());
        coursesForTimetableDto.setAcademicCourse(AcademicCourseMapper.toAcademicCourse(academicCourseService.findByName(coursesForTimetable.getAcademicCourse())));
        coursesForTimetableDto.setAcademicClass(coursesForTimetable.getAcademicClass().get(0));
        coursesForTimetableDto.setDayOfWeek(coursesForTimetable.getDayOfWeek());
        coursesForTimetableDto.setStatus(coursesForTimetable.getStatus());
        return coursesForTimetableDto;
    }

    public static List<CoursesForTimetable> toListOfCoursesForTimetable(List<CoursesForTimetableDto> listOfCoursesForTimetableDtos) {
        return listOfCoursesForTimetableDtos.stream()
                .map(CoursesForTimetableMapper::toCoursesForTimetable)
                .collect(Collectors.toList());
    }

}