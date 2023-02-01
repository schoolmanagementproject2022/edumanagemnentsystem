package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.student.model.entity.Student;

public class GradesDto {
    private Long id;
    private int gradeClasswork;
    private int gradeHomework;
    private int gradeTest;
    private Student student;
    private Classwork classwork;
    private Test test;
    private Homework homework;

    public GradesDto() {
    }

    public GradesDto(Long id, int gradeClasswork, int gradeHomework, int gradeTest, Student student,
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

    public int getGradeClasswork() {
        return gradeClasswork;
    }

    public void setGradeClasswork(int gradeClasswork) {
        this.gradeClasswork = gradeClasswork;
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

    public int getGradeHomework() {
        return gradeHomework;
    }

    public void setGradeHomework(int gradeHomework) {
        this.gradeHomework = gradeHomework;
    }

    public int getGradeTest() {
        return gradeTest;
    }

    public void setGradeTest(int gradeTest) {
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
