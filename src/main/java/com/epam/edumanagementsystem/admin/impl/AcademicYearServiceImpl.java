package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicYearMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicYearRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public void create(AcademicYear academicYear) {
        academicYearRepository.save(academicYear);
    }

    @Override
    public List<AcademicYearDto> findAll() {
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        return AcademicYearMapper.toListOfAcademicYearsDto(academicYearList);
    }

    @Override
    public AcademicYearDto getById(Long id) {
        return AcademicYearMapper.toDto(academicYearRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}
