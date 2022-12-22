package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;

import java.util.ArrayList;
import java.util.List;

public class AcademicYearMapper {

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

    public static List<AcademicYearDto> toListOfAcademicYearsDto(List<AcademicYear> academicYear) {
        List<AcademicYearDto> academicYearsDto = new ArrayList<>();
        for (AcademicYear year : academicYear) {
            academicYearsDto.add(toDto(year));
        }
        return academicYearsDto;
    }

    public static List<AcademicYear> toListOfAcademicYears(List<AcademicYearDto> academicYearDto) {
        List<AcademicYear> academicYears = new ArrayList<>();
        for (AcademicYearDto yearDto : academicYearDto) {
            academicYears.add(toAcademicYear(yearDto));
        }
        return academicYears;
    }

}
