package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.ACADEMIC_CLASS_BY_ID;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;

    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public AcademicClassDto save(AcademicClassDto academicClassDto) {
        return AcademicClassMapper.toDto(academicClassRepository.save(AcademicClassMapper.toAcademicClass(academicClassDto)));
    }

    @Override
    public AcademicClass findById(Long id) {
        return academicClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACADEMIC_CLASS_BY_ID));
    }

    @Override
    public AcademicClass findByClassNumber(String name) {
        return academicClassRepository.findByClassNumber(name).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AcademicClass removeByTeacherName(String teacherName) {
        return academicClassRepository.removeByTeacherName(teacherName);
    }

    @Override
    public AcademicClassDto update(AcademicClassDto academicClassDto) {
        AcademicClass academicClassByName = findByClassNumber(academicClassDto.getClassNumber());
        academicClassByName.getAcademicCourseSet().addAll(academicClassDto.getAcademicCourse());
        academicClassByName.getTeacher().addAll(academicClassDto.getTeacherSet());
        academicClassByName.getClassroomTeacher().getNameSurname();
        return save(AcademicClassMapper.toDto(academicClassByName));
    }

    @Override
    public List<AcademicClassDto> findAll() {
        return AcademicClassMapper.academicClassDtoList(academicClassRepository.findAll());
    }

    @Override
    public Set<AcademicClassDto> findByTeacherId(Long id) {
        return AcademicClassMapper.academicClassDtoSet(academicClassRepository.findAcademicClassByTeacherId(id));
    }

}