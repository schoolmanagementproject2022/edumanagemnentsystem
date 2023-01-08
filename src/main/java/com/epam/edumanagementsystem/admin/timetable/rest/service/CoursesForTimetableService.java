package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {

    CoursesForTimetableDto create(CoursesForTimetableDto coursesForTimetableDto);

    void deleteCourseById(Long courseId);

    void updateCourseStatusById(Long courseId);

    boolean isPresentCoursesForClass(Long academicClassId);

    void updateCourseStatusToActiveById(Long courseId);

    List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesForDayAndAcademicClassId(String dayOfWeek, Long academicClassId);

    List<CoursesForTimetable> getCoursesWithEditStatusByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId);

    List<String> getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId);

}