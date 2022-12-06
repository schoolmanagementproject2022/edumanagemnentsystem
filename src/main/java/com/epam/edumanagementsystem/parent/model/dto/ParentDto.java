package com.epam.edumanagementsystem.parent.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Objects;

public class ParentDto {
    private Long id;

    @NotBlank(message = "Please, fill the required fields")
    private String name;

    @NotBlank(message = "Please, fill the required fields")
    private String surname;

    @NotBlank(message = "Please, fill the required fields")
    private String email;

    @NotBlank
    private String role = "PARENT";

    private String password;

    private String picUrl;

    public ParentDto() {}

    public ParentDto(@Size(max = 50, message = "Symbols can't be more than 50") String name,
                     @Size(max = 50, message = "Symbols can't be more than 50") String surname,
                     @Size(max = 50, message = "Symbols can't be more than 50") String email,
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

    public String getNameSurname() {
        return name + " " + surname;
    }

    public String getNameAndSurname() {
        return this.name + " " + this.surname;
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
        ParentDto parentDto = (ParentDto) o;
        return id.equals(parentDto.id) && name.equals(parentDto.name) &&
                surname.equals(parentDto.surname) && email.equals(parentDto.email) &&
                role.equals(parentDto.role) && password.equals(parentDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, role, password);
    }

    @Override
    public String toString() {
        return "ParentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
