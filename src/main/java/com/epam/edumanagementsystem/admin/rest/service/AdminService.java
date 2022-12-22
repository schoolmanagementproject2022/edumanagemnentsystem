package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;

import java.util.List;

public interface AdminService {

    AdminDto save(AdminDto adminDto);

    List<AdminDto> findAll();

    AdminDto findByUserId(Long id);

}