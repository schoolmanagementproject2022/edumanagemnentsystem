package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import java.util.List;

public interface AcademicYearService {

    AcademicYearDto save(AcademicYearDto academicYear);

    List<AcademicYearDto> findAll();

    AcademicYearDto findById(Long id);

}


