package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.util.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUserId(Long id);
}
