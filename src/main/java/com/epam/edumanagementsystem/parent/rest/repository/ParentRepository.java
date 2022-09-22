package com.epam.edumanagementsystem.parent.rest.repository;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

}
