package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimetableService {

    TimetableDto create(Timetable timetable);

    Timetable findTimetableByAcademicClassName(String academicClassname);

    List<Timetable> findAll();

    Timetable findTimetableByAcademicClassId(Long academicClassId);

    void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId);

    void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId);
}