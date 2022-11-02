package com.epam.edumanagementsystem.util.service.impl;

import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
