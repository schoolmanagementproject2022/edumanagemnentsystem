package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    List<SubjectDto> findAll();

    Subject create(Subject subject);

    Set<Teacher> findAllTeachers(String name);

    SubjectDto findSubjectBySubjectName(String name);

    Subject update(Subject subject);
}
