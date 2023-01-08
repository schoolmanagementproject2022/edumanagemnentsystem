package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;

import java.time.LocalDate;

public interface TimetableService {

    TimetableDto create(TimetableDto timetableDto);

    TimetableDto findTimetableByAcademicClassName(String academicClassName);

    TimetableDto findTimetableByAcademicClassId(Long academicClassId);

    void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId);

    void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId);

}