package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import com.epam.edumanagementsystem.admin.rest.repository.SuperAdminRepository;
import com.epam.edumanagementsystem.admin.rest.service.SuperAdminService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;
    private final Logger logger = Logger.getLogger(SuperAdminServiceImpl.class.getName());

    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }

    @Override
    public Optional<SuperAdmin> findByEmail(String email) {
        logger.info("Finding Super Admin By Email");
        return superAdminRepository.findByEmail(email);
    }

}
