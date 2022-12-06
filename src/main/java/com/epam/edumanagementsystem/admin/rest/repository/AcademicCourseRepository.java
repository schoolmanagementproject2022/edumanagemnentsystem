package com.epam.edumanagementsystem.admin.rest.repository;//package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AcademicCourseRepository extends JpaRepository<AcademicCourse, Long> {
    AcademicCourse findAcademicCourseByName(String name);

    AcademicCourse findAcademicCourseById(Long id);

    Set<AcademicCourse> findAcademicCoursesByTeacherId(Long id);
}