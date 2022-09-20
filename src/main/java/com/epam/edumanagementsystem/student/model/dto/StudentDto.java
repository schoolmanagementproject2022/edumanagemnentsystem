package com.epam.edumanagementsystem.student.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;

import java.time.LocalDate;
import java.util.Objects;
public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private LocalDate date;
    private BloodGroup bloodGroup;
    private Gender gender;
    private String generatePassword;
    private Parent parent;
    private AcademicClass academicClass;

    public StudentDto(Long id,
                      String name,
                      String surname,
                      String email,
                      String address,
                      LocalDate date,
                      BloodGroup bloodGroup,
                      Gender gender,
                      String generatePassword,
                      Parent parent,
                      AcademicClass academicClass) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.date = date;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.generatePassword = generatePassword;
        this.parent = parent;
        this.academicClass = academicClass;
    }

    public StudentDto() {
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

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGeneratePassword() {
        return generatePassword;
    }

    public void setGeneratePassword(String generatePassword) {
        this.generatePassword = generatePassword;
    }

    public String getNameAndSurname() {
        return name + " " + surname;
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
        StudentDto that = (StudentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(address, that.address) && Objects.equals(date, that.date) && bloodGroup == that.bloodGroup && gender == that.gender && Objects.equals(generatePassword, that.generatePassword) && Objects.equals(parent, that.parent) && Objects.equals(academicClass, that.academicClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, address, date, bloodGroup, gender, generatePassword, parent, academicClass);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", bloodGroup=" + bloodGroup +
                ", gender=" + gender +
                ", generatePassword='" + generatePassword + '\'' +
                ", parent=" + parent +
                ", academicClass=" + academicClass +
                '}';
    }
}
