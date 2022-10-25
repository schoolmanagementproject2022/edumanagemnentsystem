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
    public void testAdminsEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/admins"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN"})
    public void testAdminsEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/admins"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testTeachersEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testTeachersEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testParentsEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/parents"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testParentsEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/parents"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testClassesEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/classes/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testClassesEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/classes/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testYearsEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/years"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testYearsEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/years"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testVacationsEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/vacations"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testVacationsEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/vacations"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testSubjectsEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testSubjectsEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"SUPER_ADMIN", "TEACHER", "PARENT", "STUDENT"})
    public void testCoursesEndpointWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/courses/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testCoursesEndpointWithRightAuthority() throws Exception {
        mockMvc.perform(get("/courses/"))
                .andExpect(status().isOk());
    }
}
