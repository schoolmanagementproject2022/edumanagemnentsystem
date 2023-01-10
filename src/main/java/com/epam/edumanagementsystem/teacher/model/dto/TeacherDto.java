package com.epam.edumanagementsystem.teacher.model.dto;

import com.epam.edumanagementsystem.util.AppConstants;
import com.epam.edumanagementsystem.util.annotation.ValidEmail;
import com.epam.edumanagementsystem.util.annotation.ValidName;
import com.epam.edumanagementsystem.util.annotation.ValidPassword;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TeacherDto {

    private Long id;

    @ValidName
    private String name;

    @ValidName
    private String surname;

    @ValidEmail
    private String email;

    @NotBlank
    private String role = "TEACHER";

    @ValidPassword
    private String password;

    private String picUrl;

    public TeacherDto() {
    }

    public TeacherDto(String name, String surname, String email,
                      String role, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.password = password;
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

    public String getFullName() {
        return name + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        TeacherDto that = (TeacherDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) && Objects.equals(email, that.email)
                && Objects.equals(role, that.role) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, role, password);
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
