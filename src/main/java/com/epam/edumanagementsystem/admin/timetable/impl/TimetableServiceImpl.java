package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.TimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    public TimetableServiceImpl(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    @Override
    public List<Timetable> findAll() {
        return timetableRepository.findAll();
    }

    @Override
    public void create(Timetable timetable) {
        timetableRepository.save(timetable);
    }

    @Override
    public void delete(Long id) {
        timetableRepository.deleteById(id);
    }

    @Override
    public Timetable getById(Long id) {
        return timetableRepository.findById(id).get();
    }

    @Override
    public Timetable getByName(String name) {
        return timetableRepository.findByAcademicClass_ClassNumber(name);
    }

    @Override
    public boolean isPresentTimetableByAcademicClassId(Long id) {
        return timetableRepository.isPresentTimetableByAcademicClassId(id);
    }

    @Override
    public Timetable getTimetableByAcademicClassId(Long id) {
        return timetableRepository.getTimetableByAcademicClassId(id);
    }

    @Override
    public Timetable getTimetableWithActiveStatusByAcademicClassId(Long academicClassId) {
        return timetableRepository.findTimetableWithActiveStatusByAcademicClassId(academicClassId);
    }

    @Override
    public Timetable getTimetableWithEditStatusByAcademicClassId(Long academicClassId) {
        return timetableRepository.findTimetableWithEditStatusByAcademicClassId(academicClassId);
    }

    @Transactional
    @Override
    public void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate, timeTableStatus, academicClassId);
    }

    @Transactional
    @Override
    public void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableStatusByAcademicClassId(timeTableStatus, academicClassId);
    }
}
