package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AcademicCourseMapper {

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

    public static List<AcademicCourseDto> toListOfAcademicCourseDto(List<AcademicCourse> academicCourses) {
        List<AcademicCourseDto> academicCourseDtos = new ArrayList<>();
        for (AcademicCourse academicCourse : academicCourses) {
            academicCourseDtos.add(toDto(academicCourse));
        }
        return academicCourseDtos;
    }

    public static List<AcademicCourse> toListOfAcademicCourses(List<AcademicCourseDto> academicCourseDtos) {
        List<AcademicCourse> academicCourses = new ArrayList<>();
        for (AcademicCourseDto academicCourseDto : academicCourseDtos) {
            academicCourses.add(toAcademicCourse(academicCourseDto));
        }
        return academicCourses;
    }

    public static Set<AcademicCourseDto> toSetOfAcademicCourseDto(Set<AcademicCourse> academicCourses) {
        Set<AcademicCourseDto> academicCourseDtos = new LinkedHashSet<>();
        for (AcademicCourse academicCourse : academicCourses) {
            academicCourseDtos.add(toDto(academicCourse));
        }
        return academicCourseDtos;
    }

}