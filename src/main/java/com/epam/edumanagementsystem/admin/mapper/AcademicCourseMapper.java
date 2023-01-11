package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AcademicCourseMapper {

    private AcademicCourseMapper() {
        throw new IllegalStateException();
    }

    public static AcademicCourse toAcademicCourse(AcademicCourseDto academicCourseDto) {
        AcademicCourse academicCourse = new AcademicCourse();
        academicCourse.setId(academicCourseDto.getId());
        academicCourse.setName(academicCourseDto.getName());
        academicCourse.setSubject(academicCourseDto.getSubject());
        academicCourse.setTeachers(academicCourseDto.getTeachers());
        return academicCourse;
    }

    public static AcademicCourseDto toDto(AcademicCourse academicCourse) {
        AcademicCourseDto academicCourseDto = new AcademicCourseDto();
        academicCourseDto.setId(academicCourse.getId());
        academicCourseDto.setName(academicCourse.getName());
        academicCourseDto.setSubject(academicCourse.getSubject());
        academicCourseDto.setTeachers(academicCourse.getTeachers());
        return academicCourseDto;
    }

    public static List<AcademicCourseDto> toListOfAcademicCourseDto(List<AcademicCourse> academicCourseList) {
        return academicCourseList.stream()
                .map(AcademicCourseMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<AcademicCourse> toListOfAcademicCourses(List<AcademicCourseDto> academicCourseDtoList) {
        return academicCourseDtoList.stream()
                .map(AcademicCourseMapper::toAcademicCourse)
                .collect(Collectors.toList());
    }

    public static Set<AcademicCourseDto> toSetOfAcademicCourseDto(Set<AcademicCourse> academicCourseSet) {
        return academicCourseSet.stream()
                .map(AcademicCourseMapper::toDto)
                .collect(Collectors.toSet());
    }

}