package com.epam.edumanagementsystem.admin.rest.service;


import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void addAdmin(Admin admin);

    List<AdminDto> findAllAdmins();

    Optional<Admin> findByEmail(String email);


}
