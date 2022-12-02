package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AcademicClassRepository extends JpaRepository<AcademicClass, Long> {
    AcademicClass findByClassNumber(String name);

    Set<AcademicClass> findAcademicClassByTeacherId(Long id);
}
