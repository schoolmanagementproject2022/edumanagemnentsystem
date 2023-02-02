package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.annotation.ValidGrade;

public class GradesDto {
    private Long id;
    @ValidGrade
    private String gradeClasswork;
    @ValidGrade
    private String gradeHomework;
    @ValidGrade
    private String gradeTest;
    private Student student;
    private Classwork classwork;
    private Test test;
    private Homework homework;

    public GradesDto() {
    }

    public GradesDto(Long id, String gradeClasswork, String gradeHomework, String gradeTest, Student student,
                     Classwork classwork, Test test, Homework homework) {
        this.id = id;
        this.gradeClasswork = gradeClasswork;
        this.gradeHomework = gradeHomework;
        this.gradeTest = gradeTest;
        this.student = student;
        this.classwork = classwork;
        this.test = test;
        this.homework = homework;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classwork getClasswork() {
        return classwork;
    }

    public void setClasswork(Classwork classwork) {
        this.classwork = classwork;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public String getGradeClasswork() {
        return gradeClasswork;
    }

    public void setGradeClasswork(String gradeClasswork) {
        this.gradeClasswork = gradeClasswork;
    }

    public String getGradeHomework() {
        return gradeHomework;
    }

    public void setGradeHomework(String gradeHomework) {
        this.gradeHomework = gradeHomework;
    }

    public String getGradeTest() {
        return gradeTest;
    }

    public void setGradeTest(String gradeTest) {
        this.gradeTest = gradeTest;
    }

    @Override
    public String toString() {
        return "GradesDto{" +
                "id=" + id +
                ", gradeClasswork=" + gradeClasswork +
                ", gradeHomework=" + gradeHomework +
                ", gradeTest=" + gradeTest +
                ", student=" + student +
                ", classwork=" + classwork +
                ", test=" + test +
                ", homework=" + homework +
                '}';
    }
}
