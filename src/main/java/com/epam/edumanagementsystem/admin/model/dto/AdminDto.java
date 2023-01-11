package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.util.annotation.ValidEmail;
import com.epam.edumanagementsystem.util.annotation.ValidName;
import com.epam.edumanagementsystem.util.annotation.ValidPassword;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

public class AdminDto {

    private Long id;

    @ValidName
    private String name;

    @ValidName
    private String surname;

    @ValidEmail()
    private String email;

    @NotBlank
    private String role = "ADMIN";

    @ValidPassword
    private String password;

    public AdminDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDto adminDto = (AdminDto) o;
        return id.equals(adminDto.id) && name.equals(adminDto.name) &&
                surname.equals(adminDto.surname) && email.equals(adminDto.email) &&
                role.equals(adminDto.role) && password.equals(adminDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, role, password);
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
