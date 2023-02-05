package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.NOT_PRESENT_COURSE_ID;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {

    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassService academicClassService;
    private final Logger logger = Logger.getLogger(AcademicCourseServiceImpl.class.getName());

    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository,
                                     AcademicClassService academicClassService) {
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassService = academicClassService;
    }

    @Override
    public AcademicCourseDto findByName(String name) {
        logger.info("Finding Academic Course by Name");
        return AcademicCourseMapper.toDto(academicCourseRepository.findByName(name).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public AcademicCourseDto findById(Long id) {
        logger.info("Finding Academic Course by Id");
        return AcademicCourseMapper.toDto(academicCourseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_PRESENT_COURSE_ID)));
    }

    @Override
    public AcademicCourseDto save(AcademicCourseDto academicCourse) {
        logger.info("Saving Academic Course");
        return AcademicCourseMapper.toDto(academicCourseRepository.save(AcademicCourseMapper.toAcademicCourse(academicCourse)));
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        logger.info("Finding All Academic Courses");
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourseRepository.findAll());
    }

    @Override
    public AcademicCourseDto update(AcademicCourseDto academicCourse) {
        logger.info("Updating Academic Course");
        AcademicCourseDto academicCourseByNameDto = findByName(academicCourse.getName());
        academicCourseByNameDto.getTeachers().addAll(academicCourse.getTeachers());
        return save(academicCourseByNameDto);
    }

    @Override
    public Set<AcademicCourseDto> findAllByTeacherId(Long id) {
        logger.info("Saving Academic Course");
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicCourseRepository.findAllByTeachersId(id));
    }

    @Override
    public Set<AcademicCourseDto> findAllAcademicCoursesInClassByName(String name) {
        logger.info("Finding All Academic Courses in Class by Name");
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicClassService.findByClassNumber(name).getAcademicCourseSet());
    }

    @Override
    public void checkCourseDuplication(AcademicCourseDto academicCourseDto, BindingResult bindingResult, Model model) {
        if (findAll().stream().anyMatch(name -> name.getName().equalsIgnoreCase(academicCourseDto.getName()))) {
            bindingResult.addError(new ObjectError("academicCourse", "Duplicate course name"));
            model.addAttribute("duplicated", "An Academic Course with the same name already exists");
        }
    }

}