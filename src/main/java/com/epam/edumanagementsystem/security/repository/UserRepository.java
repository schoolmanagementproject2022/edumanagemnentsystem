package com.epam.edumanagementsystem.security.repository;

import com.epam.edumanagementsystem.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}
