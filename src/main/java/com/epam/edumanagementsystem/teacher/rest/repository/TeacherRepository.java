package com.epam.edumanagementsystem.teacher.rest.repository;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByUserId(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE teacher SET pic_url = NULL WHERE id =(?1);")
    void updateTeacherPicUrl(Long id);

}
