package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import com.epam.edumanagementsystem.admin.rest.repository.SuperAdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SuperAdminServiceImplTest {

    @MockBean
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private SuperAdminServiceImpl superAdminService;


    @Test
    void testFindByEmailResultIsEmptyWithWrongEmail() {
        String email = "wrong@gmail.com";
        when(superAdminRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertTrue(superAdminService.findByEmail(email).isEmpty());
    }

    @Test
    void testFindByEmailResultIsNotEmptyWithRightEmail() {
        String email = "right@gmail.com";
        Optional<SuperAdmin> superAdmin = Optional.of(new SuperAdmin(1, "right@gmail.com", "12345"));
        when(superAdminRepository.findByEmail(email)).thenReturn(superAdmin);
        assertFalse(superAdminService.findByEmail(email).isEmpty());
    }

    @Test
    void testFindByEmailResultIsNotPresentWithWrongEmail() {
        String email = "wrong@gmail.com";
        when(superAdminRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertFalse(superAdminService.findByEmail(email).isPresent());
    }

    @Test
    void testFindByEmailResultIsPresentWithRightEmail() {
        String email = "right@gmail.com";
        Optional<SuperAdmin> superAdmin = Optional.of(new SuperAdmin(1, "right@gmail.com", "12345"));
        when(superAdminRepository.findByEmail(email)).thenReturn(superAdmin);
        assertTrue(superAdminService.findByEmail(email).isPresent());
    }

    @Test
    void testFindByEmailWithRightEmail() {
        String email = "right@gmail.com";
        Optional<SuperAdmin> expectedSuperAdmin = Optional.of(new SuperAdmin(1, "right@gmail.com", "12345"));
        when(superAdminRepository.findByEmail(email)).thenReturn(expectedSuperAdmin);
        Optional<SuperAdmin> actualSuperAdmin = superAdminService.findByEmail(email);
        assertEquals(expectedSuperAdmin, actualSuperAdmin);
    }
}