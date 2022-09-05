package com.epam.edumanagementsystem.security;

import com.epam.edumanagementsystem.entity.User;
import com.epam.edumanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with  %s username does not exists", email));
        }
        return new CurrentUser(byEmail.get());
    }
}