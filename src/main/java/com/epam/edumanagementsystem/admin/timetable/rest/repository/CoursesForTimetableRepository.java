package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesForTimetableRepository extends JpaRepository<CoursesForTimetable, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "Delete FROM courses_table WHERE id =(?1);")
    void deleteCourseById(Long courseId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE courses_table SET status = 'Not Active' WHERE id =(?1);")
    void updateCourseStatusById(Long courseId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE courses_table SET status = 'Active' WHERE id =(?1);")
    void updateCourseStatusToActiveById(Long courseId);

    boolean existsCoursesForTimetableByAcademicClass_Id(Long academicClassId);

    List<CoursesForTimetable> findCoursesByAcademicClassId(Long academicClassId);

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE day_of_week = (?1) AND academic_class_id = (?2)")
    List<CoursesForTimetable> findCoursesByDayOfWeekAndAcademicClassId(String dayOfWeek, Long academicClassId);

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE status = 'Edit' AND academic_class_id = (?1)")
    List<CoursesForTimetable> findCoursesWithEditStatusByAcademicCourseId(Long academicClassId);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO courses_table(day_of_week, academic_course_name, academic_class_id, status) " +
            "values(?1,?2,?3,?4)")
    void create(String dayOfWeek, String academicCourseName, Long academicClassId, String status);

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE status = 'Active' AND academic_class_id = (?1)")
    List<CoursesForTimetable> findCoursesWithActiveStatusByAcademicCourseId(Long academicClassId);

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE status = 'Not Active' AND academic_class_id = (?1)")
    List<CoursesForTimetable> findCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId);

    @Query(nativeQuery = true, value = "SELECT *  FROM courses_table WHERE day_of_week = (?1) AND status = (?2) AND academic_class_id = (?3)")
    List<CoursesForTimetable> findCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId);
}