package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.SubjectMapper;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.util.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final Logger logger = Logger.getLogger(SubjectServiceImpl.class.getName());

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> findAll() {
        logger.info("Finding All Subjects");
        return SubjectMapper.toSubjectDtoList(subjectRepository.findAll());
    }

    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        logger.info("Saving Subject");
        return SubjectMapper.toDto(subjectRepository.save(SubjectMapper.toSubject(subjectDto)));
    }

    @Override
    public SubjectDto findByName(String name) {
        logger.info("Finding Subject by Name");
        return SubjectMapper.toDto(subjectRepository.findByName(name).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Set<TeacherDto> findAllTeachersInSubjectByName(String name) {
        logger.info("Finding All Teachers in Subject by Subject Name");
        return TeacherMapper.mapToTeacherDtoSet(findByName(name).getTeacherSet());
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto) {
        logger.info("Updating Subject");
        SubjectDto subjectBySubjectName = findByName(subjectDto.getName());
        subjectBySubjectName.setName(subjectDto.getName());
        subjectBySubjectName.getTeacherSet().addAll(subjectDto.getTeacherSet());
        return save(subjectBySubjectName);
    }

    @Override
    public Set<SubjectDto> findAllByTeacherId(Long teacherId) {
        logger.info("Finding All Subjects by Teacher id");
        return SubjectMapper.toSetOfSubjectDto(subjectRepository.findSubjectsByTeacherSetId(teacherId));
    }

    @Override
    public void checkSubjectDuplication(SubjectDto subjectDto, BindingResult bindingResult, Model model) {
        if (findAll().stream().anyMatch(name -> name.getName().equalsIgnoreCase(subjectDto.getName()))) {
            bindingResult.addError(new ObjectError("subject", "Duplicate subject name"));
            model.addAttribute(AppConstants.DUPLICATED, "A Subject with the same name already exists");
        }
    }
}

