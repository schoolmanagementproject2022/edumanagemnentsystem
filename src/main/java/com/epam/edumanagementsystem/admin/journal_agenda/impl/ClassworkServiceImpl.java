package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.repository.ClassworkRepository;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClassworkServiceImpl implements ClassworkService {

    private final ClassworkRepository classworkRepository;
    private final AcademicClassRepository academicClassRepository;
    private final AcademicCourseRepository academicCourseRepository;

    public ClassworkServiceImpl(ClassworkRepository classworkRepository, AcademicClassRepository academicClassRepository, AcademicCourseRepository academicCourseRepository) {
        this.classworkRepository = classworkRepository;
        this.academicClassRepository = academicClassRepository;
        this.academicCourseRepository = academicCourseRepository;
    }

    @Override
    public Classwork save(String classworkDesc, Long classId, Long courseId) {
        Classwork classwork = new Classwork();
        classwork.setClasswork(classworkDesc);
        classwork.setDateOfClasswork(LocalDate.now());
        classwork.setAcademicClass(academicClassRepository.findById(classId).get());
        classwork.setAcademicCourse(academicCourseRepository.findById(courseId).get());
        return classworkRepository.save(classwork);
    }
}
