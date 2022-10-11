package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursesForTimetableRepository extends JpaRepository<CoursesForTimetable, Long> {

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE day_of_week = (?1) AND academic_class_id = (?2)")
    List<CoursesForTimetable> findByDayOfWeek(String dayOfWeek, Long academicClassId);

    List<CoursesForTimetable> findCoursesByAcademicClassId(Long academicClassId);

    boolean existsCoursesForTimetableByAcademicClass_Id(Long id);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "insert into courses_table(day_of_week, academic_course_name, academic_class_id) " +
            "values(?1,?2,?3)")
    void create(String dayOfWeek, String academicCourseName, Long academicClassId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "Delete FROM courses_table WHERE id =(?1);")
    void delete(Long id);

}
