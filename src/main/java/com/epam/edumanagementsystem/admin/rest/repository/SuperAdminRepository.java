package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {

    @Query(value = "SELECT * FROM super_admin WHERE LOWER(email) = LOWER(?1)",nativeQuery = true)
    Optional<SuperAdmin> findByEmail(String email);

}
