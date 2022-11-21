package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @NotNull(message = "Please, fill the required fields")
    private Subject subject;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicCourse_teacher_mapping",
            joinColumns = @JoinColumn(name = "academicCourse_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teacher;

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
        this.teacher = teacher;
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

    public Set<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(Set<Teacher> teacher) {
        this.teacher = teacher;
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
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacher);
    }

    @Override
    public String toString() {
        return "AcademicCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}