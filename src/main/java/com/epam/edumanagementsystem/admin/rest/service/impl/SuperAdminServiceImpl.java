package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import com.epam.edumanagementsystem.admin.rest.repository.SuperAdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    @Autowired
    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }

    @Override
    public Optional<SuperAdmin> findByEmail(String email) {
        return superAdminRepository.findByEmail(email);
    }

}
