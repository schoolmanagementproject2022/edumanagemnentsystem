package com.epam.edumanagementsystem.util.repository;

import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User(1L, "user@email.ru", "Teacher");
    }

    @Test
    void testFindByEmailIsPresentWithRightEmail() {
        String email = "uSEr@emaiL.ru";
        userRepository.save(user);
        assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Test
    void testFindByEmailIsEmptyWithWrongEmail() {
        String email = "u-r@emaiL.ruo";
        userRepository.save(user);
        assertTrue(userRepository.findByEmail(email).isEmpty());
    }
}