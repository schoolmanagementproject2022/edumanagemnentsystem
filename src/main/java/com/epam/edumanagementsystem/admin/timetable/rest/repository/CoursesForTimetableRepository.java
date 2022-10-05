package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesForTimetableRepository extends JpaRepository<CoursesForTimetable, Long> {

    List<CoursesForTimetable> findByDayOfWeek(String dayOfWeek);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "insert into courses_table(day_of_week, academic_course_name, academic_class_id) " +
            "values(?1,?2,?3)")
    void create(String dayOfWeek, String academicCourseName, Long academicClassId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE courses_table SET academic_class_id = '0' WHERE id =(?1);")
    void renameById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "Delete FROM courses_table WHERE id =(?1);")
    void delete(Long id);

    @Modifying(clearAutomatically = true)
    void deleteById(Long id);
}
