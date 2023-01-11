package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AdminMapper;
import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl(AdminRepository adminRepository, UserService userService, PasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        logger.info("Entered save method:{}" +adminDto);
        Admin admin = AdminMapper.toAdmin(adminDto);
        admin.setUser(userService.save(new User(adminDto.getEmail(), adminDto.getRole())));
        admin.setPassword(encoder.encode(adminDto.getPassword()));
        return AdminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public List<AdminDto> findAll() {
        logger.info("Entered findAll method" );
        return AdminMapper.adminDTOConvert(adminRepository.findAll());
    }

    @Override
    public AdminDto findByUserId(Long userId) {
        logger.info("Entered findByUserId method: {}" + userId);
        return AdminMapper.toDto(adminRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found by user id: " + userId)));
    }

}
