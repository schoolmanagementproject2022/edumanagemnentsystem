package com.epam.edumanagementsystem.util.service.impl;

import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User save(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        throw new ObjectIsNull();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public Boolean checkDuplicationOfEmail(String email) {
        return findAll().stream().anyMatch(userEmail -> userEmail.getEmail().equalsIgnoreCase(email));
    }
}
