package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {
    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassService academicClassService;
    private final CoursesForTimetableService coursesForTimetableService;


    @Autowired
    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository, AcademicClassService academicClassService, CoursesForTimetableService coursesForTimetableService) {
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassService = academicClassService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public AcademicCourse findAcademicCourseByAcademicCourseName(String name) {
        if (name != null) {
            AcademicCourse academicCourseByName = academicCourseRepository.findAcademicCourseByName(name);
            if (!academicCourseByName.equals(null)) {
                return academicCourseByName;
            } else {
                throw new RuntimeException("academic course by given name does not exist");
            }
        } else {
            throw new NullPointerException("the name is null");
        }
    }

    @Override
    public List<AcademicCourse> findAllCourse() {
        return academicCourseRepository.findAll();
    }

    @Override
    public AcademicCourse findByID(Long id) {
        if (!id.equals(null)) {
            AcademicCourse academicCourseById = academicCourseRepository.findAcademicCourseById(id);
            if (academicCourseById.equals(null)) {
                throw new RuntimeException("academic course by given id does not exist");
            } else {
                return academicCourseById;
            }
        } else {
            throw new NullPointerException("given id is null");
        }
    }

    @Override
    public AcademicCourseDto create(AcademicCourse academicCourse) {
        if (academicCourse.equals(null)) {
            throw new NullPointerException("academic course is null");
        } else {
            AcademicCourse savedAcademicCourse = academicCourseRepository.save(academicCourse);
            AcademicCourseDto academicCourseDto = AcademicCourseMapper.toDto(savedAcademicCourse);
            return academicCourseDto;
        }
    }

    @Override
    public AcademicCourseDto getById(Long id) {
        Optional<AcademicCourse> classById = academicCourseRepository.findById(id);
        if (!id.equals(null)) {
            if (classById.isPresent()) {
                return AcademicCourseMapper.toDto(classById.get());
            } else {
                throw new RuntimeException("does not present academic course by given id");
            }
        } else {
            throw new NullPointerException("given id is null");
        }
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        List<AcademicCourse> academicCourses = academicCourseRepository.findAll();
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourses);
    }

    public Set<Teacher> findAllTeacher() {
        Set<Teacher> teachersByAcademicCourse = new HashSet<>();
        List<AcademicCourse> academicCourses = academicCourseRepository.findAll();
        for (AcademicCourse academicCourse : academicCourses) {
            Set<Teacher> result = academicCourse.getTeacher();
            teachersByAcademicCourse.addAll(result);
        }
        return teachersByAcademicCourse;
    }

    @Override
    public AcademicCourseDto update(AcademicCourse academicCourse) {
        AcademicCourse academicCourseByAcademicCourseName = findAcademicCourseByAcademicCourseName(academicCourse.getName());
        if (academicCourse.getName() != null) {
            academicCourseByAcademicCourseName.setName(academicCourse.getName());
        }
        if (academicCourse.getTeacher() != null) {
            Set<Teacher> teacherSet = academicCourse.getTeacher();
            for (Teacher teacher : teacherSet) {
                academicCourseByAcademicCourseName.getTeacher().add(teacher);
            }
        }
        return create(academicCourseByAcademicCourseName);
    }

    @Override
    public Set<Teacher> findAllTeachersByAcademicCourseName(String name) {
        return findAcademicCourseByAcademicCourseName(name).getTeacher();
    }

    @Override
    public Set<AcademicCourseDto> findAcademicCoursesByTeacherId(Long id) {
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicCourseRepository.findAcademicCoursesByTeacherId(id));
    }

    @Override
    public List<AcademicCourse> findAllAcademicCourses(String name) {
        List<AcademicCourse> listOfCourses = new ArrayList<>();
        Set<AcademicCourse> academicCourseSet = academicClassService.findByName(name).getAcademicCourseSet();
        for (AcademicCourse course : academicCourseSet) {
            listOfCourses.add(course);
        }
        return listOfCourses;
    }

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