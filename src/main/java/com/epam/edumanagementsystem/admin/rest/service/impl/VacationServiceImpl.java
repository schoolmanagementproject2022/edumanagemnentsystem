package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.VacationMapper;
import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.rest.repository.VacationRepository;
import com.epam.edumanagementsystem.admin.rest.service.VacationService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.VACATION_BY_ID;

@Service
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final Logger logger = Logger.getLogger(VacationServiceImpl.class.getName());

    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @Override
    public VacationDto save(VacationDto vacationDto) {
        logger.info("Saving Vacation");
        return VacationMapper.toDto(vacationRepository.save(VacationMapper.toVacation(vacationDto)));
    }

    @Override
    public List<VacationDto> findAll() {
        logger.info("Finding All Vacations");
        return VacationMapper.toListOfVacationsDto(vacationRepository.findAll());
    }

    @Override
    public VacationDto findById(Long id) {
        logger.info("Finding Vacation by Id");
        return VacationMapper.toDto(vacationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VACATION_BY_ID)));
    }

}


