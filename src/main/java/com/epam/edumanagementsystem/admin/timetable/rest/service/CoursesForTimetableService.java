package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {
    List<CoursesForTimetable> getCoursesForMonday(String dayOfWeek);

    void create(CoursesForTimetableDto coursesForTimetableDto);

    //    @Modifying
//    @Query(nativeQuery = true, value = "UPDATE courses_table SET day_of_week = 'Not defined' WHERE courses_table.id =(?1);")
    void renameById(Long id);
}