package com.epam.edumanagementsystem.admin.model.dto;

import java.util.Objects;

public class AdminDto {
    private String username;
    private String surname;
    private String email;

    public AdminDto() {
    }
    public AdminDto(String username, String surname, String email) {
        this.username = username;
        this.surname = surname;
        this.email = email;
    }

    public String getNameSurname() {
        return username + " " + surname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDto adminDTO = (AdminDto) o;
        return Objects.equals(username, adminDTO.username) && Objects.equals(surname, adminDTO.surname) && Objects.equals(email, adminDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, surname, email);
    }
}
