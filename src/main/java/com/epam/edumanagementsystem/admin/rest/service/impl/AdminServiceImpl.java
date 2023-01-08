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
import java.util.logging.Logger;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        logger.info("Saving Admin");
        Admin admin = AdminMapper.toAdmin(adminDto);
        admin.setUser(userService.save(new User(adminDto.getEmail(), adminDto.getRole())));
        return AdminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public List<AdminDto> findAll() {
        logger.info("Finding All Admins");
        return AdminMapper.adminDTOConvert(adminRepository.findAll());
    }

    @Override
    public AdminDto findByUserId(Long userId) {
        logger.info("Finding Admin by User Id");
        return AdminMapper.toDto(adminRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found by user id: " + userId)));
    }

}
