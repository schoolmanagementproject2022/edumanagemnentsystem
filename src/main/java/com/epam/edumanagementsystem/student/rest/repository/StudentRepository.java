package com.epam.edumanagementsystem.student.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE student SET pic_url = NULL WHERE id =(?1);")
    void updateStudentPicUrl(Long id);

    List<Student> findAllByParentId(Long parentId);

    List<Student> findAllByAcademicClassIsNull();

    List<Student> findAllByParentIsNull();

}
