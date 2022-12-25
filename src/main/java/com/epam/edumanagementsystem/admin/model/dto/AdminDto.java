package com.epam.edumanagementsystem.admin.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.EMPTY_FIELD;

public class AdminDto {

    private Long id;

    @NotBlank(message = EMPTY_FIELD)
    private String username;

    @NotBlank(message = EMPTY_FIELD)
    private String surname;

    @NotBlank(message = EMPTY_FIELD)
    private String email;

    @NotBlank
    private String role = "ADMIN";

    @NotBlank(message = EMPTY_FIELD)
    private String password;

    public AdminDto() {
    }

    public String getNameSurname() {
        return username + " " + surname;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
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
        return id.equals(adminDto.id) && username.equals(adminDto.username) &&
                surname.equals(adminDto.surname) && email.equals(adminDto.email) &&
                role.equals(adminDto.role) && password.equals(adminDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, surname, email, role, password);
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
