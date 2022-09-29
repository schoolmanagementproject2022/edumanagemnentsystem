package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
}
