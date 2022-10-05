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
    public AcademicCourse findByName(String name) {
        return academicCourseRepository.findByName(name);
    }

    @Override
    public Set<Teacher> findTeacherByAcademicCourseName(String name) {
        return academicCourseRepository.findByName(name).getTeacherSet();
    }

    public void update(AcademicCourse academicCourse) {
        AcademicCourse byName = findByName(academicCourse.getName());
        if (academicCourse.getName() != null) {
            byName.setName(academicCourse.getName());
        }
        if (academicCourse.getSubject() != null){
            byName.setSubject(academicCourse.getSubject());
        }
        if (academicCourse.getAcademicClassSet()!=null){
            byName.setAcademicClassSet(academicCourse.getAcademicClassSet());
        }
        if (academicCourse.getTeacherSet() != null) {
            Set<Teacher> teacherSet = academicCourse.getTeacherSet();
            for (Teacher teacher : teacherSet) {
                byName.getTeacherSet().add(teacher);
            }
        }
        create(byName);
    }
}

