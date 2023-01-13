package com.epam.edumanagementsystem.admin.model.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.*;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.FIELD_MAX_SIZE;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.REGEXP;

@Entity
@Table(name = "super_admin")
public class SuperAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NotBlank(message = EMPTY_FIELD)
    @Size(max = FIELD_MAX_SIZE, message = SYMBOL_LENGTH)
    @Email(regexp = REGEXP, message = INVALID_EMAIL)
    private String email;
    @NotBlank(message = EMPTY_FIELD)
    private String password;

    public SuperAdmin() {
    }

    public SuperAdmin(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
