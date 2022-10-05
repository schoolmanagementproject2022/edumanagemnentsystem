package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesForTimetableRepository extends JpaRepository<CoursesForTimetable, Long> {

    List<CoursesForTimetable> findByDayOfWeek(String dayOfWeek);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "insert into courses_table(day_of_week, academic_course_id, academic_class_id) " +
            "values(?1,?2,?3)")
    void create(String dayOfWeek, Long academicCourseId, Long academicClassId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE courses_table SET day_of_week = 'Not defined' WHERE id =(?1);")
    void renameById(Long id);

}
