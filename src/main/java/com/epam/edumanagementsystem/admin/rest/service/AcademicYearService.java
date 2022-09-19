package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;

import java.util.List;

public interface AcademicYearService {

    void create(AcademicYear academicYear);

    List<AcademicYear> findAll();

}


