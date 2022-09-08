package com.epam.edumanagementsystem.admin.model.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="admin")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "name is required")
    private String username;
    @NotEmpty(message = "name is required")
    private String surname;
    @Size(max = 12,min = 6)
    private String password;
    @Email(message = "You entered invalid email")
    @NotEmpty(message = "email is required")
    private String email;

    public Admin() {
    }

    public Admin(Long id, String username, String surname, String password, String email) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) && Objects.equals(username, admin.username) && Objects.equals(surname, admin.surname) && Objects.equals(password, admin.password) && Objects.equals(email, admin.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, surname, password, email);
    }
}
