package com.epam.edumanagementsystem.admin.rest.service;
import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import java.util.List;

public interface AcademicYearService {

    AcademicYearDto create(AcademicYear academicYear);

    List<AcademicYearDto> findAll();

    AcademicYearDto getById(Long id);
}


