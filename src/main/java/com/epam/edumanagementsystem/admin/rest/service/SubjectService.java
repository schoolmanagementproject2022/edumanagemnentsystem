package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    List<SubjectDto> findAll();

    SubjectDto save(SubjectDto subjectDto);

    Set<TeacherDto> findAllTeachers(String name);

    Subject findByName(String name);

    SubjectDto update(SubjectDto subjectDto);

    Set<SubjectDto> findAllByTeacherId(Long teacherId);

    void checkSubjectDuplication(SubjectDto subjectDto, BindingResult bindingResult, Model model);
}
