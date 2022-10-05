package com.epam.edumanagementsystem.admin.rest.repository;//package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AcademicCourseRepository extends JpaRepository<AcademicCourse, Long> {
    AcademicCourse findAcademicCourseByName(String name);

    AcademicCourse findAcademicCourseByUrlName(String name);

}