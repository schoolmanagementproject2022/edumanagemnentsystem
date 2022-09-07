package com.epam.edumanagementsystem.admin.rest.repository;


import com.epam.edumanagementsystem.admin.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

}
