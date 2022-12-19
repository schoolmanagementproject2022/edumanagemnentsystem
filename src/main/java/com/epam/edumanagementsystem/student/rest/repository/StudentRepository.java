package com.epam.edumanagementsystem.student.rest.repository;

import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE student SET pic_url = NULL WHERE id =(?1);")
    void updateStudentPicUrl(Long id);

    List<Student> findAllByParentId(Long parentId);

    //@ToDo example change the update field method in just query
    @Modifying
    @Query(value = "UPDATE Student SET name = :name, surname = :surname,  WHERE s.id = :id",nativeQuery = true)
    void updateField(@Param(value = "id") long id, @Param(value = "name") String name, @Param(value = "surname") String surname);

}
