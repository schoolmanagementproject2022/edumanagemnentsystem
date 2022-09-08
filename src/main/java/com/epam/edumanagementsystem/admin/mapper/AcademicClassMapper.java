package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;

import java.util.ArrayList;
import java.util.List;

public class AcademicClassMapper {
    public static AcademicClass ToAcademicClass(AcademicClassDto academicClassDto) {
        AcademicClass academicClass = new AcademicClass();
        academicClass.setId(academicClassDto.getId());
        academicClass.setClassNumber(academicClassDto.getClassNumber());

        return academicClass;
    }

    public static AcademicClassDto toDto(AcademicClass academicClass) {
        AcademicClassDto academicClassDto = new AcademicClassDto();
        academicClassDto.setId(academicClass.getId());
        academicClassDto.setClassNumber(academicClass.getClassNumber());

        return academicClassDto;
    }

    public static List<AcademicClassDto> academicClassDtoList(List<AcademicClass> academicClasses) {
        List<AcademicClassDto> academicClassDtos = new ArrayList<>();
        for (AcademicClass academicClass : academicClasses) {
            academicClassDtos.add(toDto(academicClass));
        }
        return academicClassDtos;
    }
    public static List<AcademicClass> academicClassessList(List<AcademicClassDto> academicClassDtos) {
        List<AcademicClass> academicClasses = new ArrayList<>();
        for (AcademicClassDto academicClassDto : academicClassDtos) {
            academicClasses.add(ToAcademicClass(academicClassDto));
        }
        return academicClasses;
    }
}
