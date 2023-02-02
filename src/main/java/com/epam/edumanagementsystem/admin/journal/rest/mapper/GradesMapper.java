package com.epam.edumanagementsystem.admin.journal.rest.mapper;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

public class GradesMapper {

    public static Grades toGrades(GradesDto gradesDto) {
        Grades grades = new Grades();
        grades.setId(grades.getId());
        if (gradesDto.getGradeTest().isBlank() || gradesDto.getGradeClasswork().isBlank() ||
                gradesDto.getGradeHomework().isBlank()) {
            grades.setGradeHomework(0);
        } else {
            grades.setGradeTest(Integer.parseInt(gradesDto.getGradeTest()));
            grades.setGradeHomework(Integer.parseInt(gradesDto.getGradeHomework()));
            grades.setGradeClasswork(Integer.parseInt(gradesDto.getGradeClasswork()));
        }
        grades.setStudent(gradesDto.getStudent());
        grades.setClasswork(grades.getClasswork());
        grades.setTest(grades.getTest());
        grades.setHomework(grades.getHomework());

        return grades;
    }

    public static GradesDto toDto(Grades grades) {
        GradesDto gradesDto = new GradesDto();
        gradesDto.setId(grades.getId());
        gradesDto.setGradeTest(Integer.toString(grades.getGradeTest()));
        gradesDto.setGradeClasswork(Integer.toString(grades.getGradeClasswork()));
        gradesDto.setGradeHomework(Integer.toString(grades.getGradeHomework()));
//        gradesDto.setStudent(grades.getStudent());
        gradesDto.setClasswork(grades.getClasswork());
        gradesDto.setTest(grades.getTest());
        gradesDto.setHomework(grades.getHomework());
        return gradesDto;
    }
}
