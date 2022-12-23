package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.VacationMapper;
import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.rest.repository.VacationRepository;
import com.epam.edumanagementsystem.admin.rest.service.VacationService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.VACATION_BY_ID;

@Service
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    @Autowired
    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @Override
    public VacationDto save(VacationDto vacationDto) {
        return VacationMapper.toDto(vacationRepository.save(VacationMapper.toVacation(vacationDto)));
    }

    @Override
    public List<VacationDto> findAll() {
        return VacationMapper.toListOfVacationsDto(vacationRepository.findAll());
    }

    @Override
    public VacationDto findById(Long id) {
        return VacationMapper.toDto(vacationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VACATION_BY_ID)));
    }

}


