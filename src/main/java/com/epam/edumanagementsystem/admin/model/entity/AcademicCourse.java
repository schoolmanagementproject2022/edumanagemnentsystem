package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "academic_course")
public class AcademicCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Symbols can't be more than 50")
    @Column(unique = true)
    @NotBlank(message = "Please, fill the required fields")
    private String name;
    @ManyToMany(mappedBy = "academicCourseSet", fetch = FetchType.LAZY)
    private Set<AcademicClass> academicClassSet = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "subject_id")
    @NotNull(message = "Please, fill the required fields")
    private Subject subject;

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    @ManyToMany(mappedBy = "academicCourseSet", fetch = FetchType.LAZY)
    private Set<Teacher> teacherSet=new HashSet<>();

    public AcademicCourse(Long id, String name, Set<AcademicClass> academicClassSet, Subject subject) {
        this.id = id;
        this.name = name;
        this.academicClassSet = academicClassSet;
        this.subject = subject;
    }
    public AcademicCourse() {
    }
    public Set<AcademicClass> getAcademicClassSet() {
        return academicClassSet;
    }

    public void setAcademicClassSet(Set<AcademicClass> academicClassSet) {
        this.academicClassSet = academicClassSet;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicCourse that = (AcademicCourse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, subject);
    }
}
