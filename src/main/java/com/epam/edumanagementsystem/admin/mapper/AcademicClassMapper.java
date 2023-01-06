package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AcademicClassMapper {

    private AcademicClassMapper() {
        throw new IllegalStateException();
    }

    public static AcademicClass toAcademicClass(AcademicClassDto academicClassDto) {
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
    public static AcademicClassDto toNormalDto(AcademicClass academicClass) {
        AcademicClassDto academicClassDto = new AcademicClassDto();
        academicClassDto.setId(academicClass.getId());
        academicClassDto.setAcademicCourse(academicClass.getAcademicCourseSet());
        academicClassDto.setTeacherSet(academicClass.getTeacher());
        academicClassDto.setClassNumber(academicClass.getClassNumber());
        academicClassDto.setClassroomTeacher(academicClass.getClassroomTeacher());
        return academicClassDto;
    }

    public static List<AcademicClassDto> academicClassDtoList(List<AcademicClass> academicClassList) {
        return academicClassList.stream()
                .map(AcademicClassMapper::toNormalDto)
                .collect(Collectors.toList());
    }
    public static Set<AcademicClassDto> academicClassDtoSet(Set<AcademicClass> academicClassSet) {
        return academicClassSet.stream()
                .map(AcademicClassMapper::toNormalDto)
                .collect(Collectors.toSet());
    }

    public static List<AcademicClass> academicClassessList(List<AcademicClassDto> academicClassDtoList) {
        return academicClassDtoList.stream()
                .map(AcademicClassMapper::toAcademicClass)
                .collect(Collectors.toList());
    }
}
