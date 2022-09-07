package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AdminDTO;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
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
    public List<AdminDTO> findAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::adminDTOConvert)
                .collect(Collectors.toList());
    }

    public AdminDTO adminDTOConvert(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setSurname(admin.getSurname());
        adminDTO.setEmail(admin.getEmail());
        return adminDTO;
    }

}
