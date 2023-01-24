package com.epam.edumanagementsystem.util.service.impl;

import com.epam.edumanagementsystem.util.entity.DoneCourses;
import com.epam.edumanagementsystem.util.repository.DoneCoursesRepository;
import com.epam.edumanagementsystem.util.service.DoneCoursesService;
import org.springframework.stereotype.Service;

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

}