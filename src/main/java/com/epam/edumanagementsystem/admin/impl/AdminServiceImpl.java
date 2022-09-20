package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
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
        adminDTO.setEmail(admin.getEmail());
        return adminDTO;
    }
}
