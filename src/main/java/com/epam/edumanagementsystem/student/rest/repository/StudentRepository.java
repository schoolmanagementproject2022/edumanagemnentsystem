package com.epam.edumanagementsystem.student.rest.repository;

import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE student SET pic_url = NULL WHERE id =(?1);")
    void updateStudentPicUrl(Long id);

    List<Student> findAllByParentId(Long parentId);

    List<Student> findAllByAcademicClassIsNull();

    List<Student> findAllByParentIsNull();

}
