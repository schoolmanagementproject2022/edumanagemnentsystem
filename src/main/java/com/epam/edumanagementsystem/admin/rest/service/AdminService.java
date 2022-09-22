package com.epam.edumanagementsystem.admin.rest.service;


import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.util.service.UserService;

import java.util.List;

public interface AdminService {
    void addAdmin(AdminDto adminDto, UserService userService);

    List<AdminDto> findAllAdmins();

}
