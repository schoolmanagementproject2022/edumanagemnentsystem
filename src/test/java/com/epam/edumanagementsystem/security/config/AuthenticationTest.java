package com.epam.edumanagementsystem.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    private String wrongEmail = "wrong-user@gmail.com";
    private String rightEmail = "super@gmail.com";
    private String password = "Sa1234567+";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoggingWithValidUser() throws Exception {
        mockMvc.perform(formLogin()
                .user(rightEmail).password(password))
                .andExpect(redirectedUrl("admins"))
                .andExpect(status().isFound())
                .andExpect(authenticated());
    }

    @Test
    public void testLoggingWithInvalidUser() throws Exception {
        mockMvc.perform(formLogin()
                .user(wrongEmail).password(password))
                .andExpect(unauthenticated());
    }
}
