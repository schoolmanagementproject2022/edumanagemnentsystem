package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findSubjectByName(String name);

    Set<Subject> findSubjectsByTeacherSetId(Long teacherId);
}
