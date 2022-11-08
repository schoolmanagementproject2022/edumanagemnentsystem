package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;

import java.util.List;

public interface VacationService {

    Vacation create(Vacation vacation);

    List<VacationDto> findAll();

    VacationDto getById(Long id);
}
