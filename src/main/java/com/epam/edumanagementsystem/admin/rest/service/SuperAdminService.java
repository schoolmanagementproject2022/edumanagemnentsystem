package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import java.util.Optional;

public interface SuperAdminService {

    Optional<SuperAdmin> findByEmail(String email);

}
