package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;

import java.util.List;

public interface CoursesForTimetableService {

    List<CoursesForTimetable> getCoursesForDay(String dayOfWeek);

    void create(CoursesForTimetableDto coursesForTimetableDto);

    void renameById(Long id);

    void delete(Long id);

    void deleteById(Long id);
}