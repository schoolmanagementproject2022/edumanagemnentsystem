package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {

    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassService academicClassService;
    private final CoursesForTimetableService coursesForTimetableService;
    private Logger logger;

    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository,
                                     AcademicClassService academicClassService,
                                     CoursesForTimetableService coursesForTimetableService) {
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassService = academicClassService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public AcademicCourse findByName(String name) {
        return academicCourseRepository.findByName(name).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public AcademicCourse findById(Long id) {
        return academicCourseRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public AcademicCourseDto save(AcademicCourseDto academicCourse) {
        return AcademicCourseMapper.toDto(academicCourseRepository.save(AcademicCourseMapper.toAcademicCourse(academicCourse)));
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourseRepository.findAll());
    }

    @Override
    public AcademicCourseDto update(AcademicCourseDto academicCourse) {
        AcademicCourse academicCourseByName = findByName(academicCourse.getName());
        academicCourseByName.getTeachers().addAll(academicCourse.getTeachers());
        return save(AcademicCourseMapper.toDto(academicCourseByName));
    }

    @Override
    public Set<AcademicCourseDto> findAllByTeachersId(Long id) {
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicCourseRepository.findAllByTeachersId(id));
    }

    @Override
    public List<AcademicCourse> findAllAcademicCoursesInClassByName(String name) {
        return new ArrayList<>(academicClassService.findByClassNumber(name).getAcademicCourseSet());
    }

    //todo
    private boolean showCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClass academicClassByName,
                                          LocalDate timetableStartDate, LocalDate timetableEndDate, boolean existDay) {

        String deyOfWeek = journalStartDate.getDayOfWeek().toString();
        model.addAttribute(deyOfWeek, journalStartDate);
        String day = StringUtils.capitalize(deyOfWeek.toLowerCase(Locale.ROOT));
        List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                .getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(day, "Active", academicClassByName.getId());
        model.addAttribute(deyOfWeek.toLowerCase(Locale.ROOT), coursesByDayOfWeekAndStatusAndAcademicClassId);
        if (!coursesByDayOfWeekAndStatusAndAcademicClassId.isEmpty() &&
                !journalStartDate.isBefore(timetableStartDate) && !journalStartDate.isAfter(timetableEndDate)) {
            model.addAttribute(day, true);
            existDay = true;
        } else {
            model.addAttribute(day, false);
        }
        return existDay;
    }

}