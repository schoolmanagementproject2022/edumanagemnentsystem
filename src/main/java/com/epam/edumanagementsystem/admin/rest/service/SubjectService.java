package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    List<Subject> findAll();

    void create(Subject subject);

    Set<Teacher> findAllTeachers(String name);

    Subject findSubjectBySubjectName(String name);

    void update(Subject subject);
}
