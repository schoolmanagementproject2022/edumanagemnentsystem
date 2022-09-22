package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.VacationMapper;
import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import com.epam.edumanagementsystem.admin.rest.repository.VacationRepository;
import com.epam.edumanagementsystem.admin.rest.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    @Autowired
    public VacationServiceImpl(VacationRepository vacationRepository) {

        this.vacationRepository = vacationRepository;
    }

    @Override
    public void create(Vacation vacation) {
        vacationRepository.save(vacation);
    }

    @Override
    public List<VacationDto> findAll() {
        List<Vacation> vacationList = vacationRepository.findAll();
        return VacationMapper.toListOfVacationsDto(vacationList);
    }

    @Override
    public VacationDto getById(Long id) {
        Optional<Vacation> classById = vacationRepository.findById(id);
        if (classById.isPresent()) {
            return VacationMapper.toDto(classById.get());
        }
        return new VacationDto();
    }

}


