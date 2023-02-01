package com.epam.edumanagementsystem.util.service;

import com.epam.edumanagementsystem.util.entity.DoneCourses;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface DoneCoursesService {

    void save(DoneCourses doneCourses);

    List<DoneCourses> findAllByCourseAndClassId(Long classId, Long courseId);

    Set<DoneCourses> findAllByAcademicClassId(Long id);

    Set<DoneCourses> findAllByAcademicClassIdAndDate(Long id, LocalDate localDate);

}
