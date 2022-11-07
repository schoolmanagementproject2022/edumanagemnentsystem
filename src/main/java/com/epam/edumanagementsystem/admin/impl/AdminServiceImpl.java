package com.epam.edumanagementsystem.admin.impl;


import com.epam.edumanagementsystem.admin.mapper.AdminMapper;
import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.AdminService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserService userService;


    @Autowired
    public AdminServiceImpl() {

    }

    @Override

    public Admin addAdmin(AdminDto adminDto) {
        if (adminDto == null) {
            throw new NullPointerException();

        } else {
            User user = new User();
            user.setEmail(adminDto.getEmail());
            user.setRole(adminDto.getRole());
            User userSave=userService.save(user);
            Admin admin= AdminMapper.toAdmin(adminDto);
            admin.setUser(userSave);

          return   adminRepository.save(admin);

        }
    }


    public List<AdminDto> findAllAdmins() {
        List<Admin> allAdmins = adminRepository.findAll();
        return AdminMapper.adminDTOConvert(allAdmins);
    }

    @Override
    public AdminDto findByUserId(Long id) {
        Admin byUserId = adminRepository.findByUserId(id);
        if (byUserId==null) {
            throw new EntityNotFoundException();
        }
        return AdminMapper.toDto(byUserId);
    }


}
