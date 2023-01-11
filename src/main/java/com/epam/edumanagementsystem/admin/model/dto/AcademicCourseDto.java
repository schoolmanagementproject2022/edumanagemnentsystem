package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.EMPTY_FIELD;
import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.SYMBOL_LENGTH;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.FIELD_MAX_SIZE;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.USED_SYMBOLS;

public class AcademicCourseDto {

    private Long id;
    @NotBlank(message = EMPTY_FIELD)
    @Size(max = FIELD_MAX_SIZE, message = SYMBOL_LENGTH)
    @Pattern(regexp = "^[A-Za-z0-9-<>_`*,:|()]+$", message = USED_SYMBOLS)
    private String name;
    @NotNull(message = EMPTY_FIELD)
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