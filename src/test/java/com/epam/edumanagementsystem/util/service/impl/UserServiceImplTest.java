package com.epam.edumanagementsystem.util.service.impl;

import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private Optional<User> user;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        user = Optional.of(new User(1L, "right-user@gmail.com", "12345"));
    }

    @Test
    void testFindByIdIsOk() {
        Long id = 1L;
        User expectedUser = user.get();
        when(userRepository.findById(id)).thenReturn(Optional.of(user.get()));
        User actualUser = userService.findById(id);

        assertThat(actualUser).isNotNull();
        assertEquals(expectedUser, actualUser);
        assertThat(expectedUser.getEmail().equalsIgnoreCase(actualUser.getEmail()) &&
                expectedUser.getRole().equalsIgnoreCase(actualUser.getRole()));

    }

    @Test
    void testSaveIsOk() {
        when(userRepository.save(any(User.class))).thenReturn(user.get());
        User actualUser = userService.save(user.get());
        assertEquals(user.get(), actualUser);
        verify(userRepository, times(1)).save(user.get());
    }

    @Test
    void testSaveReturnsNull() {
        when(userRepository.save(any(User.class))).thenReturn(null);
        User actualUser = userService.save(user.get());
        assertEquals(null, actualUser);
        verify(userRepository, times(1)).save(user.get());
    }

    @Test
    void testFindByEmailReturnsRightUser() {
        String email = "right-user@gmail.com";
        User expectedUser = user.get();
        when(userRepository.findByEmail(email)).thenReturn(user);
        User actualUser = userService.findByEmail(email);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testFindAllReturnsAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user.get()));
        List<User> listOfUsers = userService.findAll();
        assertNotNull(listOfUsers);
        assertEquals(1, listOfUsers.size());
    }


}