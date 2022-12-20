package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.*;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {

    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassService academicClassService;
    private final CoursesForTimetableService coursesForTimetableService;


    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository,
                                     AcademicClassService academicClassService,
                                     CoursesForTimetableService coursesForTimetableService) {
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassService = academicClassService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public AcademicCourse findByName(String name) {
        return academicCourseRepository.findAcademicCourseByName(name);
    }

    @Override
    public AcademicCourse findByID(Long id) {
        return academicCourseRepository.findAcademicCourseById(id);
    }

    @Override
    public AcademicCourseDto create(AcademicCourse academicCourse) {
        return AcademicCourseMapper.toDto(academicCourseRepository.save(academicCourse));
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourseRepository.findAll());
    }

    //First Version
    @Override
    public AcademicCourseDto update(AcademicCourse academicCourse) {
        AcademicCourse academicCourseByName = findByName(academicCourse.getName());
        for (Teacher teacher : academicCourse.getTeacher()) {
            academicCourseByName.getTeacher().add(teacher);
        }
        return create(academicCourseByName);
    }

//    Second version
//    @Override
//    public AcademicCourseDto update(AcademicCourse academicCourse) {
//        return create(academicCourse);
//    }

    @Override
    public Set<AcademicCourseDto> findAcademicCoursesByTeacherId(Long id) {
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicCourseRepository.findAcademicCoursesByTeacherId(id));
    }

    @Override
    public List<AcademicCourse> findAllAcademicCourses(String name) {
        return new ArrayList<>(academicClassService.findByName(name).getAcademicCourseSet());
    }

    //todo
    private boolean getCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClass academicClassByName,
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