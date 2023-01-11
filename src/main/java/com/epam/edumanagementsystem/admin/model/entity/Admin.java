package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.util.entity.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin")

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String surname;

    private String password;

    @OneToOne
    private User user;

    public Admin() {
    }

    public Admin(Long id, String username, String surname, User user, String password) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.user = user;
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
        return Objects.hash(id, username, surname, password);
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
