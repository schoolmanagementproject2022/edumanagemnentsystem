package com.epam.edumanagementsystem.student.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.entity.User;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String name;
    @Column(name = "surname")
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String surname;
    @OneToOne
    private User user;
    @Column(name = "address")
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String address;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please, fill the required fields")
    private LocalDate date;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please, fill the required fields")
    private Gender gender;
    @Column(name = "password")
    @NotBlank(message = "Please, fill the required fields")
    private String password;
    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please, fill the required fields")
    private BloodGroup bloodGroup;
    @OneToOne(fetch = FetchType.LAZY)
    private Parent parent;
    @OneToOne
    @NotNull(message = "Please, fill the required fields")
    private AcademicClass academicClass;

    public Student(Long id,
                   String name,
                   String surname,
                   String address,
                   LocalDate date,
                   Gender gender,
                   String password,
                   BloodGroup bloodGroup,
                   Parent parent,
                   AcademicClass academicClass) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.date = date;
        this.gender = gender;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.parent = parent;
        this.academicClass = academicClass;
    }

    public Student() {
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
