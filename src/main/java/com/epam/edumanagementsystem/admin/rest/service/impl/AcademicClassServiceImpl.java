package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.ACADEMIC_CLASS_BY_ID;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;
    private final Logger logger = Logger.getLogger(AcademicClassServiceImpl.class.getName());

    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public AcademicClassDto save(AcademicClassDto academicClassDto) {
        logger.info("Saving Academic Class");
        return AcademicClassMapper.toDto(academicClassRepository.save(AcademicClassMapper.toAcademicClass(academicClassDto)));
    }

    @Override
    public AcademicClassDto findById(Long id) {
        logger.info("Finding Academic Class by Id");
        return AcademicClassMapper.toDto(academicClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACADEMIC_CLASS_BY_ID)));
    }

    @Override
    public AcademicClassDto findByClassNumber(String name) {
        logger.info("Finding Academic Class by Class Number");
        return AcademicClassMapper.toDto(academicClassRepository.findByClassNumber(name).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AcademicClassDto removeByTeacherName(String teacherName) {
        logger.info("Removing Academic Class by Teacher Name");
        return AcademicClassMapper.toDto(academicClassRepository.removeByTeacherName(teacherName));
    }

    @Override
    public AcademicClassDto update(AcademicClassDto academicClassDto) {
        logger.info("Updating Academic Class");
        AcademicClassDto academicClassByNameDto = findByClassNumber(academicClassDto.getClassNumber());
        academicClassByNameDto.getAcademicCourse().addAll(academicClassDto.getAcademicCourse());
        academicClassByNameDto.getTeachers().addAll(academicClassDto.getTeachers());
        academicClassByNameDto.getStudents().addAll(academicClassDto.getStudents());
        academicClassByNameDto.setClassroomTeacher(academicClassDto.getClassroomTeacher());
        return save(academicClassByNameDto);
    }

    @Override
    public List<AcademicClassDto> findAll() {
        logger.info("Finding All Academic Classes");
        return AcademicClassMapper.toAcademicClassDtoList(academicClassRepository.findAll());
    }

    @Override
    public Set<AcademicClassDto> findByTeacherId(Long teacherId) {
        logger.info("Finding Academic Class by Teacher Id");
        return AcademicClassMapper.toAcademicClassDtoSet(academicClassRepository.findAcademicClassByTeachersId(teacherId));
    }

    @Override
    public void checkClassDuplication(AcademicClassDto academicClassDto, BindingResult bindingResult, Model model) {
        logger.info("Checking Class Duplication");
        if (findAll().stream().anyMatch(number -> number.getClassNumber().equalsIgnoreCase(academicClassDto.getClassNumber()))) {
            bindingResult.addError(new ObjectError("academicClass", "Duplicate class number"));
            model.addAttribute(AppConstants.DUPLICATED, "Class already exists");
        }
    }

}