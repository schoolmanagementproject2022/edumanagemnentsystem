package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.util.entity.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "admin")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String username;
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String surname;
    @NotBlank(message = "Please, fill the required fields")
    private String password;
    @OneToOne
    private User user;

    public Admin() {
    }

    public Admin(Long id, String username, String surname, String password) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.password = password;
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
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) && Objects.equals(username, admin.username) && Objects.equals(surname, admin.surname) && Objects.equals(password, admin.password) && Objects.equals(user, admin.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, surname, password, user);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
