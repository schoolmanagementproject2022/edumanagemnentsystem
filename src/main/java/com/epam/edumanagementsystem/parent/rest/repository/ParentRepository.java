package com.epam.edumanagementsystem.parent.rest.repository;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByEmail(String email);

}
