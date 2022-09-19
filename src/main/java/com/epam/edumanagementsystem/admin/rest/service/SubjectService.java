package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.entity.Subject;

import java.util.List;

public interface SubjectService {
    void create(Subject admin);

    List<Subject> findAll();

}
