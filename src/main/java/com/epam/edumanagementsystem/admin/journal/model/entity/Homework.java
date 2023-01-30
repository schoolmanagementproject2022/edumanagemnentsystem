package com.epam.edumanagementsystem.admin.journal.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agenda_homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String homework;
    @ManyToMany(mappedBy = "homeworkList", fetch = FetchType.EAGER)
    private List<Grades> grades;
    private LocalDate dateOfHomework;
    @ManyToOne
    private AcademicCourse academicCourse;
    @ManyToOne
    private AcademicClass academicClass;

    public Homework() {
    }

    public Homework(Long id, String homework, List<Grades> grades, LocalDate dateOfHomework, AcademicCourse academicCourse, AcademicClass academicClass) {
        this.id = id;
        this.homework = homework;
        this.grades = grades;
        this.dateOfHomework = dateOfHomework;
        this.academicCourse = academicCourse;
        this.academicClass = academicClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public List<Grades> getGrades() {
        return grades;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

    public AcademicCourse getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(AcademicCourse academicCourse) {
        this.academicCourse = academicCourse;
    }

    public LocalDate getDateOfHomework() {
        return dateOfHomework;
    }

    public void setDateOfHomework(LocalDate dateOfHomework) {
        this.dateOfHomework = dateOfHomework;
    }

    public AcademicCourse getCourseOfHomework() {
        return academicCourse;
    }

    public void setCourseOfHomework(AcademicCourse academicCourse) {
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
        return "Homework{" +
                "id=" + id +
                ", homework='" + homework + '\'' +
                ", grades=" + grades +
                ", dateOfHomework=" + dateOfHomework +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                '}';
    }
}
