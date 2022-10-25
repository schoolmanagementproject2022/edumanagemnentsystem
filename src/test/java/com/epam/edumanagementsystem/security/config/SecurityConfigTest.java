package com.epam.edumanagementsystem.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedAdminsEndpoint() throws Exception {
        mockMvc.perform(get("/admins"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN"})
    public void testAuthenticatedAdminsEndpoint() throws Exception {
        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedTeachersEndpoint() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedTeachersEndpoint() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedParentsEndpoint() throws Exception {
        mockMvc.perform(get("/parents"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedParentsEndpoint() throws Exception {
        mockMvc.perform(get("/parents"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedClassesEndpoint() throws Exception {
        mockMvc.perform(get("/classes/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedClassesEndpoint() throws Exception {
        mockMvc.perform(get("/classes/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedYearsEndpoint() throws Exception {
        mockMvc.perform(get("/years"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedYearsEndpoint() throws Exception {
        mockMvc.perform(get("/years"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedVacationsEndpoint() throws Exception {
        mockMvc.perform(get("/vacations"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedVacationsEndpoint() throws Exception {
        mockMvc.perform(get("/vacations"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedSubjectsEndpoint() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedSubjectsEndpoint() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testUnauthenticatedCoursesEndpoint() throws Exception {
        mockMvc.perform(get("/courses/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testAuthenticatedCoursesEndpoint() throws Exception {
        mockMvc.perform(get("/courses/"))
                .andExpect(status().isOk());
    }
}
