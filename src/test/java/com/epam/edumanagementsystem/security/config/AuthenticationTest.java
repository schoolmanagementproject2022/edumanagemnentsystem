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

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoggingWithInvalidUser() throws Exception {
        mockMvc.perform(formLogin()
                .user("wrong-user@gmail.com").password( "123456"))
                .andExpect(unauthenticated());
    }

    @Test
    public void testLoggingWithValidUser() throws Exception {
        mockMvc.perform(formLogin()
                .user("super@gmail.com").password("Sa1234567+"))
                .andExpect(redirectedUrl("admins"))
                .andExpect(status().isFound())
                .andExpect(authenticated());
    }
}
