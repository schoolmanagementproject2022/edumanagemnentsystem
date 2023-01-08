package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    List<SubjectDto> findAll();

    SubjectDto save(SubjectDto subjectDto);

    Set<TeacherDto> findAllTeachersInSubjectByName(String name);

    SubjectDto findByName(String name);

    SubjectDto update(SubjectDto subjectDto);

    Set<SubjectDto> findAllByTeacherId(Long teacherId);

}
