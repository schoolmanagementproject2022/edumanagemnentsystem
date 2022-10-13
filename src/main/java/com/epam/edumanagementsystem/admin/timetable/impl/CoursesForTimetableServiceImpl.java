package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.CoursesForTimetableRepository;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoursesForTimetableServiceImpl implements CoursesForTimetableService {

    private final CoursesForTimetableRepository coursesRepository;

    @Autowired
    public CoursesForTimetableServiceImpl(CoursesForTimetableRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    public List<CoursesForTimetable> getCoursesForDayAndClass(String dayOfWeek, Long academicClassId) {
        return coursesRepository.findCoursesByDayOfWeekAndAcademicClassId(dayOfWeek, academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesByAcademicClassId(Long academicClassId) {
        return coursesRepository.findCoursesByAcademicClassId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithNotActiveStatusByAcademicCourseId(Long academicClassId) {
        return coursesRepository.findCoursesWithNotActiveStatusByAcademicCourseId(academicClassId);
    }

    @Override
    public List<CoursesForTimetable> getCoursesWithActiveStatusByAcademicCourseId(Long academicClassId) {
        return coursesRepository.findCoursesWithActiveStatusByAcademicCourseId(academicClassId);
    }

    @Override
    public boolean isPresentCoursesForClass(Long academicClassId) {
        return coursesRepository.existsCoursesForTimetableByAcademicClass_Id(academicClassId);
    }

    @Transactional
    @Override
    public void create(CoursesForTimetableDto coursesForTimetableDto) {
        coursesRepository.create(coursesForTimetableDto.getDayOfWeek(),
                coursesForTimetableDto.getAcademicCourse().getName(),
                coursesForTimetableDto.getAcademicClass().getId(),
                coursesForTimetableDto.getStatus());
    }

    @Transactional
    @Override
    public void updateCourseStatusById(Long id) {
        coursesRepository.updateCourseStatusById(id);
    }

    @Transactional
    @Override
    public void renameById(Long id) {
        coursesRepository.renameById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        coursesRepository.delete(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        renameById(id);
        delete(id);
    }


}