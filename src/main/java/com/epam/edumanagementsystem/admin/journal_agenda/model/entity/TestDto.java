package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;

public class TestDto {

    private Long id;
    private String test;
    private int grade;
    private LocalDate dateOfTest;
    private AcademicCourse academicCourse;
    private AcademicClass academicClass;

    public TestDto() {
    }

    public TestDto(Long id, String test, int grade, LocalDate dateOfTest, AcademicCourse academicCourse, AcademicClass academicClass) {
        this.id = id;
        this.test = test;
        this.grade = grade;
        this.dateOfTest = dateOfTest;
        this.academicCourse = academicCourse;
        this.academicClass = academicClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(LocalDate dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public AcademicCourse getCourseOfTest() {
        return academicCourse;
    }

    public void setCourseOfTest(AcademicCourse academicCourse) {
        this.academicCourse = academicCourse;
    }

    public AcademicClass getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(AcademicClass academicClass) {
        this.academicClass = academicClass;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "id=" + id +
                ", test='" + test + '\'' +
                ", grade=" + grade +
                ", dateOfTest=" + dateOfTest +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                '}';
    }
}
