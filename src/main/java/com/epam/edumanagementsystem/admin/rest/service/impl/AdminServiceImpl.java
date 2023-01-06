package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AdminMapper;
import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;

    public AdminServiceImpl(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        Admin admin = AdminMapper.toAdmin(adminDto);
        admin.setUser(userService.save(new User(adminDto.getEmail(), adminDto.getRole())));
        return AdminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public List<AdminDto> findAll() {
        return AdminMapper.adminDTOConvert(adminRepository.findAll());
    }

    @Override
    public AdminDto findByUserId(Long userId) {
        return AdminMapper.toDto(adminRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found by user id: " + userId)));
    }

}
