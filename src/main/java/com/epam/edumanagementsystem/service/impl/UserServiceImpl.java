package com.epam.edumanagementsystem.service.impl;

import com.epam.edumanagementsystem.entity.User;
import com.epam.edumanagementsystem.repository.UserRepository;
import com.epam.edumanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(User user, Model model) {
        User byEmail = userRepository.findUserByEmail(user.getEmail());
        if (byEmail!=null&&user.getPassword().equals(byEmail.getPassword())) {
            model.addAttribute("user", byEmail);
            return "userPage";
        }
        String msg = "wrong password or email";
        model.addAttribute("msg", msg);
        return "login";
    }

}
