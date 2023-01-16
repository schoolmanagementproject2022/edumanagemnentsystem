package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;

import java.util.List;
import java.util.stream.Collectors;

public class AcademicYearMapper {

    private AcademicYearMapper() {
        throw new IllegalStateException();
    }

    public static AcademicYear toAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = new AcademicYear();
        academicYear.setId(academicYearDto.getId());
        academicYear.setStartDate(academicYearDto.getStartDate());
        academicYear.setEndDate(academicYearDto.getEndDate());
        return academicYear;
    }

    public static AcademicYearDto toDto(AcademicYear academicYear) {
        AcademicYearDto academicYearDto = new AcademicYearDto();
        academicYearDto.setId(academicYear.getId());
        academicYearDto.setStartDate(academicYear.getStartDate());
        academicYearDto.setEndDate(academicYear.getEndDate());
        return academicYearDto;
    }

    public static List<AcademicYearDto> toListOfAcademicYearsDto(List<AcademicYear> academicYearList) {
        return academicYearList.stream()
                .map(AcademicYearMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<AcademicYear> toListOfAcademicYears(List<AcademicYearDto> academicYearDtoList) {
        return academicYearDtoList.stream()
                .map(AcademicYearMapper::toAcademicYear)
                .collect(Collectors.toList());
    }

}
