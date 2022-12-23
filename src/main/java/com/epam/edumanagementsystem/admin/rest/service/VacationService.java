package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;

import java.util.List;

public interface VacationService {

    VacationDto save(VacationDto vacationDto);

    List<VacationDto> findAll();

    VacationDto findById(Long id);
}
