package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {
    private final AcademicCourseRepository academicCourseRepository;

    @Autowired
    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository) {
        this.academicCourseRepository = academicCourseRepository;
    }

    @Override
    public AcademicCourse findAcademicCourseByAcademicCourseName(String name) {
        return academicCourseRepository.findAcademicCourseByName(name);
    }
    @Override
    public AcademicCourse findAcademicCourseByAcademicCourseUrlName(String name) {
        return academicCourseRepository.findAcademicCourseByUrlName(name);
    }

    @Override
    public void create(AcademicCourse academicCourse) {
        academicCourseRepository.save(academicCourse);
    }

    @Override
    public AcademicCourseDto getById(Long id) {
        Optional<AcademicCourse> classById = academicCourseRepository.findById(id);
        if (classById.isPresent()) {
            return AcademicCourseMapper.toDto(classById.get());
        }
        return new AcademicCourseDto();
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        List<AcademicCourse> academicCourses = academicCourseRepository.findAll();
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourses);
    }

    @Override
    public void update(AcademicCourse academicCourse) {
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
        create(academicCourseByAcademicCourseName);
    }

    @Override
    public Set<Teacher> findAllTeachersByAcademicCourseName(String name) {
        return findAcademicCourseByAcademicCourseName(name).getTeacher();
    }
}