package com.epam.edumanagementsystem.admin.timetable.rest.service;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;

import java.util.List;

public interface TimetableService {

    List<Timetable> findAll();

    void create(Timetable timetable);

    void delete(Long id);

    Timetable getById(Long id);

}
