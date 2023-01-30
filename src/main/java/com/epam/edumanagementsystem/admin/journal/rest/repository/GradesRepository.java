package com.epam.edumanagementsystem.admin.journal.rest.repository;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesRepository extends JpaRepository<Grades, Long> {
}
