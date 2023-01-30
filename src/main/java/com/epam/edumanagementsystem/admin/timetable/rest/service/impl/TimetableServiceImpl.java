package com.epam.edumanagementsystem.admin.timetable.rest.service.impl;

import com.epam.edumanagementsystem.admin.timetable.mapper.TimetableMapper;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.TimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Service
public class TimetableServiceImpl implements TimetableService {
    private final TimetableRepository timetableRepository;
    private static final int TIMETABLE_MIN_DURATION_IN_DAYS = 371;
    private static final int TIMETABLE_MAX_DURATION_IN_YEARS = 1;
    private static final int TIMETABLE_MIN_DURATION_IN_MONTHS = 6;

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

    @Override
    public boolean validateEditedTimetableDates(LocalDate editedStartDate, LocalDate editedEndDate, String classname, Model model) {
        TimetableDto timetable = findTimetableByAcademicClassName(classname);
        Period diffOfDate = Period.between(editedEndDate, editedStartDate);

        if (editedStartDate.isAfter(editedEndDate) || editedStartDate.isBefore(timetable.getStartDate()) ||
                editedEndDate.isBefore(timetable.getStartDate())) {
            model.addAttribute("invalid", "Please, select right dates");
            return true;
        } else if (ChronoUnit.DAYS.between(editedStartDate, editedEndDate) < TIMETABLE_MIN_DURATION_IN_MONTHS) {
            model.addAttribute("invalid", "Timetable duration can't be less than 7 days");
            return true;
        } else if (diffOfDate.getDays() > TIMETABLE_MIN_DURATION_IN_MONTHS && diffOfDate.getYears() >= TIMETABLE_MAX_DURATION_IN_YEARS) {
            model.addAttribute("invalid", "Timetable duration can't be more than 12 months");
            return true;
        }
        return false;
    }

    @Override
    public boolean validateTimetableDates(LocalDate startDate, LocalDate endDate, Model model) {
        LocalDate now = LocalDate.now();
        Period diffOfDate = Period.between(endDate, startDate);

        if ((startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now))) {
            model.addAttribute("invalid", "Please, select right dates");
            return true;
        } else if (ChronoUnit.DAYS.between(startDate, endDate) < TIMETABLE_MIN_DURATION_IN_MONTHS) {
            model.addAttribute("invalid", "Timetable duration can't be less than 7 days");
            return true;

        } else if (diffOfDate.getDays() > TIMETABLE_MIN_DURATION_IN_MONTHS && diffOfDate.getYears() >= TIMETABLE_MAX_DURATION_IN_YEARS) {
            model.addAttribute("invalid", "Timetable duration can't be more than 12 months");
            return true;
        }
        return false;
    }
}