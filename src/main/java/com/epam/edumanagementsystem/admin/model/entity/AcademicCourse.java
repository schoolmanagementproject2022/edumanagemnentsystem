package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "academic_course")
public class AcademicCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicCourse_teacher_mapping",
            joinColumns = @JoinColumn(name = "academicCourse_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers;

    @JsonIgnore
    @ManyToMany(mappedBy = "academicCourseSet", fetch = FetchType.LAZY)
    private Set<AcademicClass> academicClass;

    public AcademicCourse() {
    }

    public AcademicCourse(Long id, String name, Subject subject, Set<Teacher> teacher,
                          Set<AcademicClass> academicClass) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.teachers = teacher;
        this.academicClass = academicClass;
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

    public void setTeachers(Set<Teacher> teacher) {
        this.teachers = teacher;
    }

    public Set<AcademicClass> getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(Set<AcademicClass> academicClass) {
        this.academicClass = academicClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicCourse course = (AcademicCourse) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(teachers, course.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teachers);
    }

    @Override
    public String toString() {
        return "AcademicCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}