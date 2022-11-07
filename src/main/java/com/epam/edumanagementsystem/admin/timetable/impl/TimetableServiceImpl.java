package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.timetable.mapper.TimetableMapper;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
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
    public Timetable findTimetableByAcademicClassName(String academicClassName) {
        return timetableRepository.findByAcademicClass_ClassNumber(academicClassName);
    }

    @Override
    public TimetableDto create(Timetable timetable) {
        return TimetableMapper.toDto(timetableRepository.save(timetable));
    }

    @Override
    public Timetable findTimetableByAcademicClassId(Long academicClassId) {
        return timetableRepository.getTimetableByAcademicClassId(academicClassId);
    }

    @Transactional
    @Override
    public void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate,
                                                               String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate,
                timeTableStatus, academicClassId);
    }

    @Transactional
    @Override
    public void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableStatusByAcademicClassId(timeTableStatus, academicClassId);
    }
}