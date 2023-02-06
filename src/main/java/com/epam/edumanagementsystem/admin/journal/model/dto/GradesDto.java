package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.annotation.ValidGrade;

import java.time.LocalDate;

public class GradesDto {

    private Long id;
    @ValidGrade
    private String gradeClasswork;
    @ValidGrade
    private String gradeHomework;
    @ValidGrade
    private String gradeTest;
    private Student student;
    private String classwork;
    private String test;
    private String homework;
    private Long courseId;
    private LocalDate date;

    public GradesDto() {
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

    public String getClasswork() {
        return classwork;
    }

    public void setClasswork(String classwork) {
        this.classwork = classwork;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
