package com.epam.edumanagementsystem.admin.journal.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agenda_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String test;
    private int grade;
    private LocalDate dateOfTest;
    @ManyToOne
    private AcademicCourse academicCourse;
    @ManyToOne
    private AcademicClass academicClass;

    public Test() {
    }

    public Test(Long id, String test, int grade, LocalDate dateOfTest, AcademicCourse academicCourse, AcademicClass academicClass) {
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

    public AcademicCourse getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(AcademicCourse academicCourse) {
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
        return "Test{" +
                "id=" + id +
                ", test='" + test + '\'' +
                ", grade=" + grade +
                ", dateOfTest=" + dateOfTest +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                '}';
    }
}
