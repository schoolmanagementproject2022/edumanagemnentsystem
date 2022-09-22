package com.epam.edumanagementsystem.util.service;

import com.epam.edumanagementsystem.util.entity.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User save(User user);

    List<User> findAll();

    User findByEmail(String email);
}
