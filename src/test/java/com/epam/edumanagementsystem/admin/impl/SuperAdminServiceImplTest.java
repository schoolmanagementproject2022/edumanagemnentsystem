package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import com.epam.edumanagementsystem.admin.rest.repository.SuperAdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperAdminServiceImplTest {

    private Optional<SuperAdmin> superAdmin;

    @Mock
    private SuperAdminRepository superAdminRepository;

    @InjectMocks
    private SuperAdminServiceImpl superAdminService;

    @BeforeEach
    public void setup() {
        superAdmin = Optional.of(new SuperAdmin(1, "right@gmail.com", "12345"));
    }

    @Test
    void testFindByEmailWithRightEmail() {
        String email = "right@gmail.com";
        when(superAdminRepository.findByEmail(email)).thenReturn(superAdmin);
        Optional<SuperAdmin> actualSuperAdmin = superAdminService.findByEmail(email);
        assertEquals(superAdmin, actualSuperAdmin);
    }

    @Test
    void testFindByEmailResultIsEmptyWithWrongEmail() {
        String email = "wrong@gmail.com";
        when(superAdminRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertTrue(superAdminService.findByEmail(email).isEmpty());
    }
}