package com.epam.edumanagementsystem.util.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.util.entity.DoneCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface DoneCoursesRepository extends JpaRepository<DoneCourses, Long> {

    List<DoneCourses> findAllByAcademicClassIdAndAcademicCourseId(Long classId, Long courseId);

    Set<DoneCourses> findAllByAcademicClass_Id(Long id);

}
