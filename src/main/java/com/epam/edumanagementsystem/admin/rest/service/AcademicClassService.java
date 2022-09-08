package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import java.util.List;

public interface AcademicClassService {

    void create(AcademicClass academicClass);

    List<AcademicClassDto> findAll();

    AcademicClassDto getById(Long id);

}


