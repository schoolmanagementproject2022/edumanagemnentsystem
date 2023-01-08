package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.VacationMapper;
import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import com.epam.edumanagementsystem.admin.rest.repository.VacationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacationServiceImplTest {

    private Vacation vacation;

    @Mock
    private VacationRepository vacationRepository;

    @InjectMocks
    private VacationServiceImpl vacationServiceImpl;

    @BeforeEach
    void setUp() {
        vacation = new Vacation(1L, LocalDate.now(), LocalDate.of(2022, 4, 17));
    }

    @Test
    void saveVacation() {
        when(vacationRepository.save(any(Vacation.class))).thenReturn(vacation);
        VacationDto actualVacation = vacationServiceImpl.save(VacationMapper.toDto(vacation));
        Assertions.assertNotNull(actualVacation);
        assertThat(actualVacation.getId()).isPositive();
    }

    @Test
    void findAllVacations() {
        when(vacationRepository.findAll()).thenReturn(List.of(new Vacation()));
        List<VacationDto> vacationDtoList = vacationServiceImpl.findAll();
        assertNotNull(vacationDtoList);
        assertEquals(1, vacationDtoList.size());
    }

    @Test
    void findByVacationId() {
        when(vacationRepository.findById(1L)).thenReturn(Optional.of(vacation));
        VacationDto vacationDto = vacationServiceImpl.findById(1L);
        assertEquals(1L, vacationDto.getId());

    }

    @Test
    void notFoundById() {
        assertThrows(EntityNotFoundException.class, () -> vacationServiceImpl.findById(3L));
    }

}