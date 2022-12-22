package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public class AcademicCourseDto {

    private Long id;
    @NotBlank(message = "Please, fill the required fields")
    private String name;
    @NotNull(message = "Please, fill the required fields")
    private Subject subject;
    private Set<Teacher> teachers;
    private Set<AcademicClass> academicClassSet;

    public AcademicCourseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<AcademicClass> getAcademicClassSet() {
        return academicClassSet;
    }

    public void setAcademicClassSet(Set<AcademicClass> academicClassSet) {
        this.academicClassSet = academicClassSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicCourseDto that = (AcademicCourseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(subject, that.subject) && Objects.equals(teachers, that.teachers) && Objects.equals(academicClassSet, that.academicClassSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, subject, teachers, academicClassSet);
    }

    @Override
    public String toString() {
        return "AcademicCourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                ", teacher=" + teachers +
                '}';
    }
}