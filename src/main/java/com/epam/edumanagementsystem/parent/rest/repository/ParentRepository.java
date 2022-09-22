package com.epam.edumanagementsystem.parent.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query(value = "SELECT * FROM parent WHERE LOWER(email) = LOWER(?1)", nativeQuery = true)
    Optional<Parent> findByEmail(String email);

}
