package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicYearMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicYearRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.ACADEMIC_YEAR_BY_ID;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;
    private final Logger logger = Logger.getLogger(AcademicYearServiceImpl.class.getName());

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public AcademicYearDto save(AcademicYearDto academicYear) {
        logger.info("Saving Academic Year");
        return AcademicYearMapper.toDto(academicYearRepository.save(AcademicYearMapper.toAcademicYear(academicYear)));
    }

    @Override
    public List<AcademicYearDto> findAll() {
        logger.info("Finding All Academic Years");
        return AcademicYearMapper.toListOfAcademicYearsDto(academicYearRepository.findAll());
    }

    @Override
    public AcademicYearDto findById(Long id) {
        logger.info("Finding Academic Year by Id");
        return AcademicYearMapper.toDto(academicYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ACADEMIC_YEAR_BY_ID)));
    }

}
