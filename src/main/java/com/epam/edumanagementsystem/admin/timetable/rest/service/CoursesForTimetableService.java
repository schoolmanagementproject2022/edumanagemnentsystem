package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {

    List<CoursesForTimetable> getCoursesForDayAndClass(String dayOfWeek, Long academicClassId);

    List<CoursesForTimetable> getCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId);

    List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithEditStatusByAcademicCourseId(Long academicClassId);

    boolean isPresentCoursesForClass(Long academicClassId);

    void create(CoursesForTimetable coursesForTimetable);

    void updateCourseStatusById(Long id);
    void updateCourseStatusToActiveById(Long id);

    void deleteCourseById(Long id);

}