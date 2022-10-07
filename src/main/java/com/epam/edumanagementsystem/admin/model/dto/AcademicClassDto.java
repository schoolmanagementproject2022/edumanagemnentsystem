package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.Objects;
import java.util.Set;

public class AcademicClassDto {
    private Long id;
    private String classNumber;

    private Set<AcademicCourse> academicCourse;

    private Set<Teacher> teacherSet;

    public AcademicClassDto() {
    }

    public AcademicClassDto(Long id, String classNumber, Set<AcademicCourse> academicCourse, Set<Teacher> teacherSet) {
        this.id = id;
        this.classNumber = classNumber;
        this.academicCourse = academicCourse;
        this.teacherSet = teacherSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public Set<AcademicCourse> getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(Set<AcademicCourse> academicCourse) {
        this.academicCourse = academicCourse;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClassDto that = (AcademicClassDto) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber) && Objects.equals(academicCourse, that.academicCourse) && Objects.equals(teacherSet, that.teacherSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber, academicCourse, teacherSet);
    }

    @Override
    public String toString() {
        return "AcademicClassDto{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                ", academicCourse=" + academicCourse +
                ", teacherSet=" + teacherSet +
                '}';
    }
}
