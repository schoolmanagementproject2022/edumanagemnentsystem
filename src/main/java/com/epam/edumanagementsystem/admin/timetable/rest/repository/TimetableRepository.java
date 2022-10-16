package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Timetable findByAcademicClass_ClassNumber(String name);

    @Query(nativeQuery = true, value = "SELECT *  FROM timetable_table WHERE academic_class_id = (?1)")
    boolean isPresentTimetableByAcademicClassId(Long id);

    @Query(nativeQuery = true, value = "SELECT *  FROM timetable_table WHERE academic_class_id = (?1)")
    Timetable getTimetableByAcademicClassId(Long id);


    @Query(nativeQuery = true, value = "SELECT * FROM timetable_table WHERE status = 'Active' AND academic_class_id =(?1);")
    Timetable findTimetableWithActiveStatusByAcademicClassId(Long academicClassId);

    @Query(nativeQuery = true, value = "SELECT * FROM timetable_table WHERE status = 'Edit' AND academic_class_id =(?1);")
    Timetable findTimetableWithEditStatusByAcademicClassId(Long academicClassId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE timetable_table SET start_date = (?1), end_date = (?2),status = (?3) WHERE academic_class_id =(?4);")
    void updateTimetableDatesAndStatusByAcademicClassId(LocalDate startDate, LocalDate endDate, String timeTableStatus, Long academicClassId);

}
