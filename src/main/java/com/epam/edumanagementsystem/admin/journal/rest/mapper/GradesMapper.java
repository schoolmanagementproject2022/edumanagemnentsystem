package com.epam.edumanagementsystem.admin.journal.rest.mapper;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

public class GradesMapper {

    public static Grades toGrades(GradesDto gradesDto) {
        Grades grades = new Grades();
        grades.setId(grades.getId());
        grades.setGradeHomework(gradesDto.getGradeHomework());
        grades.setGradeTest(gradesDto.getGradeTest());
        grades.setGradeClasswork(gradesDto.getGradeClasswork());
        grades.setStudent(gradesDto.getStudent());
        grades.setClasswork(grades.getClasswork());
        grades.setTest(grades.getTest());
        grades.setHomework(grades.getHomework());

        return grades;
    }

    public static GradesDto toDto(Grades grades) {
        GradesDto gradesDto = new GradesDto();
        gradesDto.setId(grades.getId());
        gradesDto.setGradeClasswork(grades.getGradeClasswork());
        gradesDto.setGradeTest(grades.getGradeTest());
        gradesDto.setGradeHomework(grades.getGradeHomework());
        gradesDto.setStudent(grades.getStudent());
        gradesDto.setClasswork(grades.getClasswork());
        gradesDto.setTest(grades.getTest());
        gradesDto.setHomework(grades.getHomework());
        return gradesDto;
    }
}
