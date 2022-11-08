package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import com.epam.edumanagementsystem.admin.rest.repository.VacationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void testCreatePositive() {
        when(vacationRepository.save(any(Vacation.class))).thenReturn(vacation);
        Vacation actualVacation = vacationServiceImpl.create(vacation);
        assertEquals(vacation, actualVacation);
        verify(vacationRepository, times(1)).save(vacation);
    }

    @Test
    void testCreateNegative() {
        assertThrows(NullPointerException.class, () -> vacationServiceImpl.create(null));
    }

    @Test
    void testFindAllPositive() {
        when(vacationRepository.findAll()).thenReturn(List.of(new Vacation()));
        List<VacationDto> vacationDtoList = vacationServiceImpl.findAll();
        assertNotNull(vacationDtoList);
        assertEquals(1, vacationDtoList.size());
    }

    @Test
    void getByIdPositive() {
        Long vocationId = 1L;
        when(vacationRepository.findById(vocationId)).thenReturn(Optional.of(vacation));
        VacationDto vacationDto = vacationServiceImpl.getById(vocationId);
        assertEquals(vocationId, vacationDto.getId());

    }

    @Test
    void getByIdNegative() {
        Long vocationId = null;
        when(vacationRepository.findById(any())).thenReturn(Optional.empty());
        VacationDto vacationDto = vacationServiceImpl.getById(vocationId);
        assertEquals(vocationId, vacationDto.getId());
    }
}