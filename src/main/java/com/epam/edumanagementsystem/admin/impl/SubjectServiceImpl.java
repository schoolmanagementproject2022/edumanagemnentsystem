package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.SubjectMapper;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> findAll() {
        return SubjectMapper.toSubjectDtoList(subjectRepository.findAll());
    }

    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        return SubjectMapper.toDto(subjectRepository.save(SubjectMapper.toSubject(subjectDto)));
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Set<TeacherDto> findAllTeachers(String name) {
        return TeacherMapper.toSetOfTeachersDto(findByName(name).getTeacherSet());
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto) {
        Subject subjectBySubjectName = findByName(subjectDto.getName());
        subjectBySubjectName.setName(subjectDto.getName());
        subjectBySubjectName.getTeacherSet().addAll(subjectDto.getTeacherSet());
        return save(SubjectMapper.toDto(subjectBySubjectName));
    }



    @Override
    public Set<SubjectDto> findAllByTeacherId(Long teacherId) {
        return SubjectMapper.toSetOfSubjectDto(subjectRepository.findSubjectsByTeacherSetId(teacherId));
    }
}

