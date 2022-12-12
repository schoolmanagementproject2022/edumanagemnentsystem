package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.CoursesForTimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoursesForTimetableServiceImpl implements CoursesForTimetableService {

    private final CoursesForTimetableRepository coursesRepository;

    @Autowired
    public CoursesForTimetableServiceImpl(CoursesForTimetableRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Transactional
    @Override
    public void create(CoursesForTimetable coursesForTimetable) {
        coursesRepository.create(coursesForTimetable.getDayOfWeek(),
                coursesForTimetable.getAcademicCourse(),
                coursesForTimetable.getAcademicClass().get(0).getId(),
                coursesForTimetable.getStatus());
    }

    @Transactional
    @Override
    public void deleteCourseById(Long courseId) {
        coursesRepository.deleteCourseById(courseId);
    }

    @Transactional
    @Override
    public void updateCourseStatusById(Long courseId) {
        coursesRepository.updateCourseStatusById(courseId);
    }

    @Override
    public boolean isPresentCoursesForClass(Long academicClassId) {
        return coursesRepository.existsCoursesForTimetableByAcademicClass_Id(academicClassId);
    }

    @Transactional
    @Override
    public void updateCourseStatusToActiveById(Long courseId) {
        coursesRepository.updateCourseStatusToActiveById(courseId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId) {
        return coursesRepository.findCoursesByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesForDayAndAcademicClassId(String dayOfWeek, Long academicClassId) {
        return coursesRepository.findCoursesByDayOfWeekAndAcademicClassId(dayOfWeek, academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithEditStatusByAcademicCourseId(Long academicClassId) {
        return coursesRepository.findCoursesWithEditStatusByAcademicCourseId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicCourseId(Long academicClassId) {
        return coursesRepository.findCoursesWithActiveStatusByAcademicCourseId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId) {
        return coursesRepository.findCoursesWithNotActiveStatusByAcademicCourseId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId) {
        return coursesRepository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId);
    }

    @Override
    public List<String> getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId) {
        List<String> classes = new ArrayList<>();
        coursesRepository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId).forEach(coursesForTimetable -> {
            classes.add(coursesForTimetable.getAcademicCourse());
        });
        return classes;
    }

}