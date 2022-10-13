package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Timetable findByAcademicClass_ClassNumber(String name);

    @Query(nativeQuery = true, value = "SELECT *  FROM timetable_table WHERE academic_class_id = (?1)")
    boolean isPresentTimetableByAcademicClassId(Long id);

    @Query(nativeQuery = true, value = "SELECT *  FROM timetable_table WHERE academic_class_id = (?1)")
    Timetable getTimetableByAcademicClassId(Long id);

    @Modifying
//    @Query(nativeQuery = true, value = "Delete FROM timetable_table WHERE id =(?1);")
    void deleteTimetableByAcademicClass_Id(Long academicClassId);


}
