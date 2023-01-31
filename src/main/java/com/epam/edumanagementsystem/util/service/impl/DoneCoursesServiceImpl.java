package com.epam.edumanagementsystem.util.service.impl;

import com.epam.edumanagementsystem.util.entity.DoneCourses;
import com.epam.edumanagementsystem.util.repository.DoneCoursesRepository;
import com.epam.edumanagementsystem.util.service.DoneCoursesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DoneCoursesServiceImpl implements DoneCoursesService {

    private final DoneCoursesRepository doneCoursesRepository;

    public DoneCoursesServiceImpl(DoneCoursesRepository doneCoursesRepository) {
        this.doneCoursesRepository = doneCoursesRepository;
    }

    @Override
    public void save(DoneCourses doneCourses) {
        doneCoursesRepository.save(doneCourses);
    }

    @Override
    public List<DoneCourses> findAllByCourseAndClassId(Long classId, Long courseId) {
        return doneCoursesRepository.findAllByAcademicClassIdAndAcademicCourseId(classId, courseId);
    }

    @Override
    public Set<DoneCourses> findAllByAcademicClassId(Long id) {
        return doneCoursesRepository.findAllByAcademicClass_Id(id);
    }

}