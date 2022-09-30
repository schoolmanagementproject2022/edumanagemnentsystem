package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;

    @Autowired
    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public void create(AcademicClass academicClass) {
        academicClassRepository.save(academicClass);
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
        return academicClassRepository.findByclassNumber(name);
    }

    @Override
    public void update(AcademicClass academicClass) {
        AcademicClass byName = findByName(academicClass.getClassNumber());
        if (academicClass.getClassNumber() != null) {
            byName.setClassNumber(academicClass.getClassNumber());
        }
        if (academicClass.getAcademicCourse() != null) {
          List<AcademicCourse> academicCourse = academicClass.getAcademicCourse();
                byName.setAcademicCourse(academicCourse);
            }

        if (academicClass.getTeacher() != null) {
          List<Teacher> teacher = academicClass.getTeacher();
                byName.setTeacher(teacher);
            }

        create(byName);
    }

    @Override
    public List<AcademicCourse> findAllAcademicCourses(String name) {
        return findByName(name).getAcademicCourse();
    }
    @Override
    public List<Teacher> findAllTeachers(String name) {
        return findByName(name).getTeacher();
    }

    @Override
    public List<AcademicClassDto> findAll() {
        List<AcademicClass> academicClassList = academicClassRepository.findAll();
        return AcademicClassMapper.academicClassDtoList(academicClassList);
    }
}
