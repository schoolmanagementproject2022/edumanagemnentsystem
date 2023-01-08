package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.TestHelper;
import com.epam.edumanagementsystem.admin.mapper.AcademicYearMapper;
import com.epam.edumanagementsystem.admin.mapper.AdminMapper;
import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.impl.AdminServiceImpl;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest extends TestHelper {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl service;

    AdminDto input;
    Admin returned;
    AdminDto createAdmin;
    User user;

    @BeforeEach
    void setUp() {
        input = input();
        returned = returned();
        createAdmin = createAdmin();
        user = user();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreate() {
        when(userRepository.save(any())).thenReturn(user);
        when(adminRepository.save(any())).thenReturn(input);
        assumeTrue(input.equals(returned));
        AdminDto admin = service.save(createAdmin);
        assertEquals(admin, input);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testCreateNegativeCase() {
        createAdmin = null;
        Assertions.assertThrows(NullPointerException.class, () -> service.save(createAdmin));
    }

    @Test
    void testFindAllAdmins() {
        when(adminRepository.findAll()).thenReturn(AdminMapper.toDto(List.of(input, returned)));

        List<AdminDto> all = service.findAll();
        assertEquals(2, all.size());

        verify(adminRepository, times(1)).findAll();
        verifyNoMoreInteractions(adminRepository);
    }

    @Test
    void testFindByUserId() {
        when(adminRepository.findByUserId(any())).thenReturn(input);
        AdminDto adminDto = service.findByUserId(input.getId());

        assertEquals(adminDto, createAdmin);
        verify(adminRepository, times(1)).findByUserId(any(Long.class));
    }

    @Test
    void TestFindByUserIdNegativeCase() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findByUserId(anyLong()));
    }

}