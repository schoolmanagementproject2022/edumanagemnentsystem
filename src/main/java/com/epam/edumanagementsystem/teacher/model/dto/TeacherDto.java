package com.epam.edumanagementsystem.teacher.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TeacherDto {
    private Long id;
    @NotBlank(message = "Please, fill the required fields")
    private String name;
    @NotBlank(message = "Please, fill the required fields")
    private String surname;
    @Email(message = "You entered invalid email")
    @NotBlank(message = "Please, fill the required fields")
    private String email;
    @NotBlank(message = "Please, fill the required fields")
    @Size(min = 9, max = 50, message = "The password size is not valid")
    private String password;

    public TeacherDto() {
    }

    public TeacherDto(Long id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
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

    public String getNameSurname() {
        return name + " " + surname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto that = (TeacherDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password);
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
