package com.epam.edumanagementsystem.student.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String surname;
    @OneToOne
    private User user;
    private String address;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String password;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    @OneToOne(fetch = FetchType.LAZY)
    private Parent parent;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "academic_class_id")
    private AcademicClass academicClass;

    private String picUrl;

    public Student() {

    }
    public Student(Long id) {
        this.id=id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String generatePassword) {
        this.password = generatePassword;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public AcademicClass getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(AcademicClass academicClass) {
        this.academicClass = academicClass;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNameAndSurname() {
        return name + " " + surname;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) && Objects.equals(user, student.user) &&
                Objects.equals(address, student.address) && Objects.equals(date, student.date) &&
                gender == student.gender && Objects.equals(password, student.password) &&
                bloodGroup == student.bloodGroup && Objects.equals(parent, student.parent) &&
                Objects.equals(academicClass, student.academicClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, user, address, date, gender,
                password, bloodGroup, parent, academicClass);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", gender=" + gender +
                ", generatePassword='" + password + '\'' +
                ", bloodGroup=" + bloodGroup +
                ", parent=" + parent +
                ", academicClass=" + academicClass +
                '}';
    }
}
