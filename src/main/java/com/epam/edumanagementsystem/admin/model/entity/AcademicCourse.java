package com.epam.edumanagementsystem.admin.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="course")
public class AcademicCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String academicCourseName;




    public AcademicCourse() {
    }

    public AcademicCourse(Long id, String academicCourseName) {
        this.id = id;
        this.academicCourseName = academicCourseName;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicCourseName() {
        return academicCourseName;
    }

    public void setAcademicCourseName(String academicCourseName) {
        this.academicCourseName = academicCourseName;
    }
}
