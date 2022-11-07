package com.epam.edumanagementsystem.admin.rest.service;


import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin addAdmin(AdminDto adminDto);

    List<AdminDto> findAllAdmins();
    AdminDto findByUserId(Long id);
}