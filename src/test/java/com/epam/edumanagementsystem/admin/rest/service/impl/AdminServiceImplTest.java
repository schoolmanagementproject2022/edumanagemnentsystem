package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AdminMapper;
import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminServiceImpl service;

    private User user;
    private Admin admin;
    private AdminDto adminDto;

    @BeforeEach
    void setUp() {
        user = new User("admin@mail.com", "ADMIN");
        admin = new Admin(1L, "AdminName", "AdminSurName", user, "password");
        adminDto = new AdminDto();

    }

    @Test
    void testSaveAdmin() {
        when(userService.save(any())).thenReturn(user);
        when(adminRepository.save(any())).thenReturn(admin);
        AdminDto savedAdmin = service.save(AdminMapper.toDto(admin));
        Assertions.assertNotNull(savedAdmin, "The savedAdmin should not be null");
        Assertions.assertEquals("admin@mail.com", savedAdmin.getEmail());
    }

    @Test
    void testSaveNullCase() {
        assertThrows(NullPointerException.class, () -> service.save(null));
    }

    @Test
    void testFindAllAdmins() {
        when(adminRepository.findAll()).thenReturn(List.of(admin));
        List<AdminDto> adminList = service.findAll();
        Assertions.assertNotNull(adminList);
        Assertions.assertEquals(1, adminList.size());
    }

    @Test
    void testFindAllAdminsEmptyCase() {
        List<AdminDto> adminDtoList = service.findAll();
        Assertions.assertEquals(0, adminDtoList.size());
    }

    @Test
    void testFindByUserId() {
        when(adminRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(admin));
        AdminDto adminDto = service.findByUserId(1L);
        assertEquals(1L, adminDto.getId());
    }

    @Test
    void TestFindByUserIdNegativeCase() {
        lenient().when(adminRepository.findById(1L)).thenReturn(Optional.empty());
    }
}