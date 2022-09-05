package com.epam.edumanagementsystem.service;

import com.epam.edumanagementsystem.entity.User;
import org.springframework.ui.Model;

public interface UserService {
    String login(User user, Model model);
}
