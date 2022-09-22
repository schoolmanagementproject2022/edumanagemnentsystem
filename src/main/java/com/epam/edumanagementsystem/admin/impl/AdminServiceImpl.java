package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Lazy
    private static UserService userService;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    @Override
    public void addAdmin(AdminDto adminDto, UserService userService) {
        adminRepository.save(adminDTOConvert(adminDto));
    }

    @Override
    public List<AdminDto> findAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::adminDTOConvert)
                .collect(Collectors.toList());
    }

    public AdminDto adminDTOConvert(Admin admin) {
        AdminDto adminDTO = new AdminDto();
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setSurname(admin.getSurname());
        adminDTO.setEmail(admin.getUser().getEmail());
        adminDTO.setRole(admin.getUser().getRole());
        return adminDTO;
    }

    public Admin adminDTOConvert(AdminDto adminDto) {
        Admin admin = new Admin();
        User user = new User();
        admin.setId(adminDto.getId());
        admin.setUsername(adminDto.getUsername());
        admin.setSurname(adminDto.getSurname());
        user.setEmail(adminDto.getEmail());
        user.setRole(adminDto.getRole());
        User save = userService.save(user);
        admin.setPassword(adminDto.getPassword());
        admin.setUser(save);
        return admin;
    }

    public Admin adminDTOConvertWithoutSavingUser(AdminDto adminDto) {
        Admin admin = new Admin();
        User user = new User();
        admin.setUsername(adminDto.getUsername());
        admin.setSurname(adminDto.getSurname());
        user.setEmail(adminDto.getEmail());
        user.setRole(adminDto.getRole());
        admin.setUser(userService.findByEmail(user.getEmail()));
        return admin;
    }
}
