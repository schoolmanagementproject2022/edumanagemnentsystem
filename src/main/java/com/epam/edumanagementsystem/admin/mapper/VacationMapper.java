package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.VacationDto;
import com.epam.edumanagementsystem.admin.model.entity.Vacation;
import java.util.List;
import java.util.stream.Collectors;

public class VacationMapper {

    private VacationMapper() {
        throw new IllegalStateException();
    }

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

    public static List<VacationDto> toListOfVacationsDto(List<Vacation> vacationList) {
        return vacationList.stream()
                .map(VacationMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Vacation> toListOfVacations(List<VacationDto> vacationDtoList) {
        return vacationDtoList.stream()
                .map(VacationMapper::toVacation)
                .collect(Collectors.toList());
    }

}
