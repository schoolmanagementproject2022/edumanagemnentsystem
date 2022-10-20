package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimetableService {

    void create(Timetable timetable);

    Timetable getById(Long academicClassId);

    Timetable getByName(String academicClassname);

    List<Timetable> findAll();

    Timetable getTimetableByAcademicClassId(Long academicClassId);

    void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId);

    void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId);
}