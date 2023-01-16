package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.timetable.mapper.CoursesForTimetableMapper;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.CoursesForTimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class CoursesForTimetableServiceImpl implements CoursesForTimetableService {

    private final CoursesForTimetableRepository coursesForTimetableRepository;
    private final Logger logger = Logger.getLogger(CoursesForTimetableServiceImpl.class.getName());

    public CoursesForTimetableServiceImpl(CoursesForTimetableRepository coursesForTimetableRepository) {
        this.coursesForTimetableRepository = coursesForTimetableRepository;
    }

    @Override
    public void create(CoursesForTimetableDto coursesForTimetableDto) {
        logger.info("Creating Course for Timetable");
         coursesForTimetableRepository.create(coursesForTimetableDto.getDayOfWeek(),
                coursesForTimetableDto.getAcademicCourse().getName(),
                coursesForTimetableDto.getAcademicClass().getId(),
                coursesForTimetableDto.getStatus());
    }

    @Override
    public void deleteCourseById(Long courseId) {
        coursesForTimetableRepository.deleteCourseById(courseId);
        logger.info("Deleted Course for Timetable by Id");
    }

    @Override
    public void updateCourseStatusById(Long courseId) {
        coursesForTimetableRepository.updateCourseStatusById(courseId);
        logger.info("Updated Course for Timetable Status by Id");
    }

    @Override
    public boolean isPresentCoursesForClass(Long academicClassId) {
        logger.info("Checking is Courses for Timetable exist by Id");
        return coursesForTimetableRepository.existsCoursesForTimetableByAcademicClass_Id(academicClassId);
    }

    @Override
    public void updateCourseStatusToActiveById(Long courseId) {
        coursesForTimetableRepository.updateCourseStatusToActiveById(courseId);
        logger.info("Updated Course for Timetable Status to Active by Id");
    }

    @Override
    public List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId) {
        logger.info("Getting Courses for Timetable by Academic Class Id");
        return coursesForTimetableRepository.findCoursesByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesForDayAndAcademicClassId(String dayOfWeek, Long academicClassId) {
        logger.info("Getting Courses for Timetable by Day of Week and Academic Class Id");
        return coursesForTimetableRepository.findCoursesByDayOfWeekAndAcademicClassId(dayOfWeek, academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithEditStatusByAcademicClassId(Long academicClassId) {
        logger.info("Getting Courses for Timetable with Edit Status by Academic Class Id");
        return coursesForTimetableRepository.findCoursesWithEditStatusByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicClassId(Long academicClassId) {
        logger.info("Getting Courses for Timetable with Active Status by Academic Class Id");
        return coursesForTimetableRepository.findCoursesWithActiveStatusByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicClassId(Long academicClassId) {
        logger.info("Getting Courses for Timetable with Not Active Status by Academic Class Id");
        return coursesForTimetableRepository.findCoursesWithNotActiveStatusByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId) {
        logger.info("Getting Courses for Timetable by Day of Week, Status and Academic Class Id");
        return coursesForTimetableRepository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId);
    }

    @Override
    public List<String> getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(String dayOfWeek, String status, Long academicClassId) {
        List<String> namesOfCourses = new ArrayList<>();
        coursesForTimetableRepository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId)
                .forEach(coursesForTimetable -> namesOfCourses.add(coursesForTimetable.getAcademicCourse()));
        logger.info("Getting Names of Courses for Timetable by Day of Week, Status and Academic Class Id");
        return namesOfCourses;
    }

}