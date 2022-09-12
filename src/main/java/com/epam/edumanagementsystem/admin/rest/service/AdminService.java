package com.epam.edumanagementsystem.admin.rest.service;


import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;

import java.util.List;

public interface AdminService {
    void addAdmin(Admin admin);

    List<AdminDto> findAllAdmins();

}
