package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;

    @Autowired
    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public AcademicClass create(AcademicClass academicClass) {
        if (academicClass == null) {
            throw new NullPointerException("Please, fill the required fields");
        }
        return academicClassRepository.save(academicClass);
    }

    @Override
    public AcademicClassDto getById(Long id) {
        Optional<AcademicClass> classById = academicClassRepository.findById(id);
        if (classById.isPresent()) {
            return AcademicClassMapper.toDto(classById.get());
        }
        return new AcademicClassDto();
    }

    @Override
    public AcademicClass findByName(String name) {
        return academicClassRepository.findByClassNumber(name);
    }

    @Override
    public AcademicClass update(AcademicClass academicClass) {
        AcademicClass updateAcademicClass = findByName(academicClass.getClassNumber());
        if (academicClass.getClassNumber() != null) {
            updateAcademicClass.setClassNumber(academicClass.getClassNumber());
        }
        if (academicClass.getAcademicCourseSet() != null) {
            Set<AcademicCourse> academicCourse = academicClass.getAcademicCourseSet();
            for (AcademicCourse academicCourse1 : academicCourse) {
                updateAcademicClass.getAcademicCourseSet().add(academicCourse1);
            }
        }

        if (academicClass.getTeacher() != null) {
            Set<Teacher> teacher = academicClass.getTeacher();
            for (Teacher teachers : teacher)
                updateAcademicClass.getTeacher().add(teachers);
        }

        if (academicClass.getClassroomTeacher() != null) {
            updateAcademicClass.setClassroomTeacher(academicClass.getClassroomTeacher());
        }

        return create(updateAcademicClass);
    }

    @Override
    public List<AcademicCourse> findAllAcademicCourses(String name) {
        List<AcademicCourse> listOfCourses = new ArrayList<>();
        Set<AcademicCourse> academicCourseSet = findByName(name).getAcademicCourseSet();
        for (AcademicCourse course : academicCourseSet) {
            listOfCourses.add(course);
        }
        return listOfCourses;
    }

    @Override
    public Set<Teacher> findAllTeachers(String name) {
        return findByName(name).getTeacher();
    }

    @Override
    public Set<Teacher> findAllTeacher() {
        Set<Teacher> teachersByAcademicClass = new HashSet<>();
        List<AcademicClass> academicClasses = academicClassRepository.findAll();
        for (AcademicClass academicClass : academicClasses) {
            Set<Teacher> result = academicClass.getTeacher();
            teachersByAcademicClass.addAll(result);
        }
        return teachersByAcademicClass;

    }

    @Override
    public LocalDate recurs(LocalDate localDate) {
        if (!localDate.getDayOfWeek().toString().equals("MONDAY")) {
            localDate = localDate.minusDays(1);
            localDate = recurs(localDate);
        }
        return localDate;
    }

    @Override
    public List<AcademicClassDto> findAll() {
        List<AcademicClass> academicClassList = academicClassRepository.findAll();
        return AcademicClassMapper.academicClassDtoList(academicClassList);
    }
}