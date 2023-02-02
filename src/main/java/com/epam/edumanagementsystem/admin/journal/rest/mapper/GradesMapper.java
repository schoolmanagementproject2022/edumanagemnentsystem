package com.epam.edumanagementsystem.admin.journal.rest.mapper;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

public class GradesMapper {

    public static Grades toGrades(GradesDto gradesDto) {
        Grades grades = new Grades();
        grades.setId(grades.getId());
        if (gradesDto.getGradeHomework().isBlank()) {
            grades.setGradeHomework(0);
        } else {
            grades.setGradeHomework(Integer.parseInt(gradesDto.getGradeHomework()));
        }
        if (gradesDto.getGradeTest().isBlank()) {
            grades.setGradeTest(0);
        } else {
            grades.setGradeTest(Integer.parseInt(gradesDto.getGradeTest()));
        }
        if (gradesDto.getGradeClasswork().isBlank()) {
            grades.setGradeClasswork(0);
        } else {
            grades.setGradeClasswork(Integer.parseInt(gradesDto.getGradeClasswork()));
        }
        grades.setStudent(gradesDto.getStudent());

        return grades;
    }

    public static GradesDto toDto(Grades grades) {
        GradesDto gradesDto = new GradesDto();
        gradesDto.setId(grades.getId());
        if (grades.getGradeClasswork() == 0) {
            gradesDto.setGradeClasswork("");
        } else {
            gradesDto.setGradeClasswork(Integer.toString(grades.getGradeClasswork()));
        }
        if (grades.getGradeHomework() == 0) {
            gradesDto.setGradeHomework("");
        } else {
            gradesDto.setGradeHomework(Integer.toString(grades.getGradeHomework()));
        }
        if (grades.getGradeTest() == 0) {
            gradesDto.setGradeTest("");
        } else {
            gradesDto.setGradeTest(Integer.toString(grades.getGradeTest()));
        }
        return gradesDto;
    }
}
