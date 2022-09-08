package com.epam.edumanagementsystem.student.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private String name;
    @Column(name = "surname")
    @NotBlank(message = "Please, fill the required fields")
    private String surname;
    @Column(name = "email", unique = true)
    @NotBlank(message = "Please, fill the required fields")
    private String email;
    @Column(name = "address")
    @NotBlank(message = "Please, fill the required fields")
    private String address;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please, fill the required fields")
    private LocalDate date;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please, fill the required fields")
    private Gender gender;
    @Column(name = "generate_password")
    @Size(min = 9, max = 50, message = "Please, fill the required fields")
    private String generatePassword;
    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please, fill the required fields")
    private BloodGroup bloodGroup;
    @OneToOne
    private Parent parent;
    @OneToOne
    @NotNull(message = "Please, fill the required fields")
    private AcademicClass academicClass;


    public Student(Long id,
                   String name,
                   String surname,
                   String email,
                   String address,
                   LocalDate date,
                   Gender gender,
                   String generatePassword,
                   BloodGroup bloodGroup,
                   Parent parent,
                   AcademicClass academicClass) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.date = date;
        this.gender = gender;
        this.generatePassword = generatePassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGeneratePassword() {
        return generatePassword;
    }

    public void setGeneratePassword(String generatePassword) {
        this.generatePassword = generatePassword;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(surname, student.surname) && Objects.equals(email, student.email) && Objects.equals(address, student.address) && Objects.equals(date, student.date) && gender == student.gender && Objects.equals(generatePassword, student.generatePassword) && bloodGroup == student.bloodGroup && Objects.equals(parent, student.parent) && Objects.equals(academicClass, student.academicClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, address, date, gender, generatePassword, bloodGroup, parent, academicClass);
    }
}
