package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicYearMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicYearRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.ACADEMIC_YEAR_BY_ID;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public AcademicYearDto save(AcademicYearDto academicYear) {
        return AcademicYearMapper.toDto(academicYearRepository.save(AcademicYearMapper.toAcademicYear(academicYear)));
    }

    @Override
    public List<AcademicYearDto> findAll() {
        return AcademicYearMapper.toListOfAcademicYearsDto(academicYearRepository.findAll());
    }

    @Override
    public AcademicYearDto findById(Long id) {
        return AcademicYearMapper.toDto(academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ACADEMIC_YEAR_BY_ID)));
    }

}
