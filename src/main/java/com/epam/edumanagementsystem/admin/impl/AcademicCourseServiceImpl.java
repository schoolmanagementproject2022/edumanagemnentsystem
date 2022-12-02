package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
}