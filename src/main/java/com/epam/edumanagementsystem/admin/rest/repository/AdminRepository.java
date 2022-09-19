package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM admin WHERE LOWER(email) = LOWER(?1)",nativeQuery = true)
    Optional<Admin> findByEmail(String email);
}
