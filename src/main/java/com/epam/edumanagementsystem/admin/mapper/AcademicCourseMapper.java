package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;

import java.util.ArrayList;
import java.util.List;

public class AcademicCourseMapper {

    private static AcademicCourseService academicCourseService;

    public AcademicCourseMapper(AcademicCourseService academicCourseService) {
        this.academicCourseService = academicCourseService;
    }

    public static AcademicCourse toAcademicCourse(AcademicCourseDto academicCourseDto) {
        AcademicCourse academicCourse = new AcademicCourse();
        academicCourse.setId(academicCourseDto.getId());
        academicCourse.setName(academicCourseDto.getName());
        academicCourse.setUrlName(academicCourseDto.getUrlName());
        academicCourse.setSubject(academicCourseDto.getSubject());
        academicCourse.setTeacher(academicCourseDto.getTeacher());
        return academicCourse;
    }

    public static AcademicCourseDto toDto(AcademicCourse academicCourse) {
        AcademicCourseDto academicCourseDto = new AcademicCourseDto();
        academicCourseDto.setId(academicCourse.getId());
        academicCourseDto.setName(academicCourse.getName());
        academicCourseDto.setUrlName(academicCourse.getUrlName());
        academicCourseDto.setSubject(academicCourse.getSubject());
        academicCourseDto.setTeacher(academicCourse.getTeacher());
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
}