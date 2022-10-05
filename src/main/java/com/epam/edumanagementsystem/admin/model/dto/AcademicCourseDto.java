package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.Objects;
import java.util.Set;

public class AcademicCourseDto {
    private Long id;
    private String name;
    private String urlName;
    private Subject subject;
    private Set<Teacher> teacher;

    public AcademicCourseDto() {
    }

    public AcademicCourseDto(Long id, String name, String urlName, Subject subject, Set<Teacher> teacher) {
        this.id = id;
        this.name = name;
        this.urlName = urlName;
        this.subject = subject;
        this.teacher = teacher;
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

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicCourseDto that = (AcademicCourseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(urlName, that.urlName) && Objects.equals(subject, that.subject) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, urlName, subject, teacher);
    }

    @Override
    public String toString() {
        return "AcademicCourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlName='" + urlName + '\'' +
                ", subject=" + subject +
                ", teacher=" + teacher +
                '}';
    }
}