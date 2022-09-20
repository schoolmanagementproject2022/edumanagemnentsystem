package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicYearRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    private final AcademicYearRepository academicYearRepository;

    @Override
    public void create(AcademicYear academicYear) {
        academicYearRepository.save(academicYear);
    }

    @Override
    public List<AcademicYear> findAll() {
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        return academicYearList;
    }
}
