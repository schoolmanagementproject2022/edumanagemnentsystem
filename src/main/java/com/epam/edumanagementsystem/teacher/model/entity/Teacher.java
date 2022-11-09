package com.epam.edumanagementsystem.teacher.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.util.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String name;

    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String surname;

    @OneToOne
    private User user;

    @NotBlank(message = "Please, fill the required fields")
    private String password;

    private String imageUrl;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicCourse_teacher_mapping",
            joinColumns = @JoinColumn(name = "academicCourse_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<AcademicCourse> academicCourseSet;

    @JsonIgnore
    @ManyToMany(mappedBy = "teacherSet", fetch = FetchType.EAGER)
    private Set<Subject> subjectSet;

    @JsonIgnore
    @ManyToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private Set<AcademicClass> academicClass;

    public Teacher() {
    }

    public Teacher(Long id, String name, String surname, User user, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.user = user;
        this.password = password;
    }

    public Teacher(Long id, String name, String surname, User user, String password, Set<AcademicCourse> academicCourseSet, Set<Subject> subjectSet, Set<AcademicClass> academicClass) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.user = user;
        this.password = password;
        this.academicCourseSet = academicCourseSet;
        this.subjectSet = subjectSet;
        this.academicClass = academicClass;
    }

    public Set<AcademicClass> getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(Set<AcademicClass> academicClass) {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AcademicCourse> getAcademicCourseSet() {
        return academicCourseSet;
    }

    public void setAcademicCourseSet(Set<AcademicCourse> academicCourseSet) {
        this.academicCourseSet = academicCourseSet;
    }

    public Set<Subject> getSubjectSet() {
        return subjectSet;
    }

    public String getNameSurname() {
        return name + " " + surname;
    }

    public void setSubjectSet(Set<Subject> subjectSet) {
        this.subjectSet = subjectSet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(surname, teacher.surname) && Objects.equals(user, teacher.user) && Objects.equals(password, teacher.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, user, password);
    }
}