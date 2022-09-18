package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {
    Optional<SuperAdmin> findByEmail(String email);
}
