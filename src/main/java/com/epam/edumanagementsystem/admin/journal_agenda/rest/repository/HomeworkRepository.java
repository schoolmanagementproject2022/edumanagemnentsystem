package com.epam.edumanagementsystem.admin.journal_agenda.rest.repository;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {



}
