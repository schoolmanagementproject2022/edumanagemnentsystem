package com.epam.edumanagementsystem.util.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "done_courses")
public class DoneCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AcademicCourse academicCourse;

    @ManyToOne
    private AcademicClass academicClass;

    private LocalDate date;

    public DoneCourses() {
    }

    public DoneCourses(AcademicCourse academicCourse, AcademicClass academicClass, LocalDate date) {
        this.academicCourse = academicCourse;
        this.academicClass = academicClass;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoneCourses that = (DoneCourses) o;
        return Objects.equals(id, that.id) && Objects.equals(academicCourse, that.academicCourse) && Objects.equals(academicClass, that.academicClass) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, academicCourse, academicClass, date);
    }

    @Override
    public String toString() {
        return "DoneCourses{" +
                "id=" + id +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                ", date=" + date +
                '}';
    }
}
