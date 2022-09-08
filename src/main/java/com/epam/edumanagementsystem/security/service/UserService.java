package com.epam.edumanagementsystem.security.service;

import com.epam.edumanagementsystem.security.entity.User;
import org.springframework.ui.Model;

public interface UserService {
    String login(User user, Model model);
}
