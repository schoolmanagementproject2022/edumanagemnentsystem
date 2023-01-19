package com.epam.edumanagementsystem.admin.timetable.rest.service.impl;

import com.epam.edumanagementsystem.admin.timetable.mapper.TimetableMapper;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.TimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.logging.Logger;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final Logger logger = Logger.getLogger(TimetableServiceImpl.class.getName());

    public TimetableServiceImpl(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    @Override
    public TimetableDto findTimetableByAcademicClassName(String academicClassName) {
        logger.info("Finding Timetable by Academic Class Name");
        return TimetableMapper.toDto(timetableRepository.findByAcademicClass_ClassNumber(academicClassName));
    }

    @Override
    public TimetableDto create(TimetableDto timetableDto) {
        logger.info("Creating Timetable");
        return TimetableMapper.toDto(timetableRepository.save(TimetableMapper.toTimetable(timetableDto)));
    }

    @Override
    public boolean existTimetableByClassId(Long id) {
        return timetableRepository.existsTimetableByAcademicClassId(id);
    }

    @Override
    public TimetableDto findTimetableByAcademicClassId(Long academicClassId) {
        logger.info("Finding Timetable by Academic Class Id");
        return TimetableMapper.toDto(timetableRepository.getTimetableByAcademicClassId(academicClassId));
    }

    @Transactional
    @Override
    public void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate,
                                                               String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate,
                timeTableStatus, academicClassId);
        logger.info("Updated Timetable Dates and Status by Academic Class Id");
    }

    @Transactional
    @Override
    public void updateTimetableStatusByAcademicClassId(String timeTableStatus, Long academicClassId) {
        timetableRepository.updateTimetableStatusByAcademicClassId(timeTableStatus, academicClassId);
        logger.info("Updated Timetable Status by Academic Class Id");
    }

}