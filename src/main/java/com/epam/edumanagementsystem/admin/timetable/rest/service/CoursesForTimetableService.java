package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {

    CoursesForTimetable create(CoursesForTimetable coursesForTimetable);

    void deleteCourseById(Long courseId);

    void updateCourseStatusById(Long courseId);

    boolean isPresentCoursesForClass(Long academicClassId);

    void updateCourseStatusToActiveById(Long courseId);

    List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesForDayAndAcademicClassId(String dayOfWeek, Long academicClassId);

    List<CoursesForTimetable> getCoursesWithEditStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId);
}