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
        academicClass.setTeachers(academicClassDto.getTeachers());
        academicClass.setAcademicCourseSet(academicClassDto.getAcademicCourse());
        academicClass.setStudent(academicClassDto.getStudents());

        return academicClass;
    }

    public static AcademicClassDto toDto(AcademicClass academicClass) {
        AcademicClassDto academicClassDto = new AcademicClassDto();
        academicClassDto.setId(academicClass.getId());
        academicClassDto.setAcademicCourse(academicClass.getAcademicCourseSet());
        academicClassDto.setTeachers(academicClass.getTeachers());
        academicClassDto.setClassNumber(academicClass.getClassNumber());
        academicClassDto.setClassroomTeacher(academicClass.getClassroomTeacher());
        return academicClassDto;
    }

    public static List<AcademicClassDto> toAcademicClassDtoList(List<AcademicClass> academicClassList) {
        return academicClassList.stream()
                .map(AcademicClassMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Set<AcademicClassDto> toAcademicClassDtoSet(Set<AcademicClass> academicClassSet) {
        return academicClassSet.stream()
                .map(AcademicClassMapper::toDto)
                .collect(Collectors.toSet());
    }

    public static List<AcademicClass> toAcademicClassesList(List<AcademicClassDto> academicClassDtoList) {
        return academicClassDtoList.stream()
                .map(AcademicClassMapper::toAcademicClass)
                .collect(Collectors.toList());
    }

}
