package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import java.util.ArrayList;
import java.util.List;

public class VacationMapper {
    public static Vacation toVacation(VacationDto vacationDto) {
        Vacation vacation = new Vacation();
        vacation.setId(vacationDto.getId());
        vacation.setStartDate(vacationDto.getStartDate());
        vacation.setEndDate(vacationDto.getEndDate());
        return vacation;
    }

    public static VacationDto toDto(Vacation vacation) {
        VacationDto vacationDto = new VacationDto();
        vacationDto.setId(vacation.getId());
        vacationDto.setStartDate(vacation.getStartDate());
        vacationDto.setEndDate(vacation.getEndDate());
        return vacationDto;
    }

    public static List<VacationDto> toListOfVacationsDto(List<Vacation> vacations) {
        List<VacationDto> vacationDtos = new ArrayList<>();
        for (Vacation vacation : vacations) {
            vacationDtos.add(toDto(vacation));
        }
        return vacationDtos;
    }

    public static List<Vacation> toListOfVacations(List<VacationDto> vacationDtos) {
        List<Vacation> vacations = new ArrayList<>();
        for (VacationDto vacationDto : vacationDtos) {
            vacations.add(toVacation(vacationDto));
        }
        return vacations;
    }


}
