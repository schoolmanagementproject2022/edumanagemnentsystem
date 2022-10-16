package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimetableService {

    List<Timetable> findAll();

    void create(Timetable timetable);

    void delete(Long id);

    Timetable getById(Long id);

    Timetable getByName(String name);

    boolean isPresentTimetableByAcademicClassId(Long id);

    Timetable getTimetableByAcademicClassId(Long id);

    Timetable getTimetableWithActiveStatusByAcademicClassId(Long academicClassId);

    Timetable getTimetableWithEditStatusByAcademicClassId(Long academicClassId);

    void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId);
}
