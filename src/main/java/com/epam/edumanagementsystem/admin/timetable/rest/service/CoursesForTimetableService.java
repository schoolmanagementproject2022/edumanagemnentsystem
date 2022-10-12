package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {

    List<CoursesForTimetable> getCoursesForDayAndClass(String dayOfWeek, Long academicClassId);

    List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId);

    List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicCourseId(Long academicClassId);

    boolean isPresentCoursesForClass(Long academicClassId);

    void create(CoursesForTimetableDto coursesForTimetableDto);

    void updateCourseStatusById(Long id);

    void renameById(Long id);

    void delete(Long id);

    void deleteById(Long id);
}