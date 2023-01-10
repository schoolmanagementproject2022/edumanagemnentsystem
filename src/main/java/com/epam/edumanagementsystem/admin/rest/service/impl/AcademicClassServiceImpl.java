package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
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
    public AcademicClass findByClassNumber(String name) {
        logger.info("Finding Academic Class by Class Number");
        return academicClassRepository.findByClassNumber(name).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AcademicClass removeByTeacherName(String teacherName) {
        logger.info("Removing Academic Class by Teacher Name");
        return academicClassRepository.removeByTeacherName(teacherName);
    }

    @Override
    public AcademicClassDto update(AcademicClassDto academicClassDto) {
        logger.info("Updating Academic Class");
        AcademicClass academicClassByName = findByClassNumber(academicClassDto.getClassNumber());
        academicClassByName.getAcademicCourseSet().addAll(academicClassDto.getAcademicCourse());
        academicClassByName.getTeachers().addAll(academicClassDto.getTeachers());
        return save(AcademicClassMapper.toDto(academicClassByName));
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
            model.addAttribute("duplicated", "Class already exists");
        }
    }

}