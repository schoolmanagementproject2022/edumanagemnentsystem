package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.admin.journal.rest.repository.TestRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.TestService;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final AcademicClassRepository academicClassRepository;
    private final AcademicCourseRepository academicCourseRepository;

    public TestServiceImpl(TestRepository testRepository, AcademicClassRepository academicClassRepository, AcademicCourseRepository academicCourseRepository) {
        this.testRepository = testRepository;
        this.academicClassRepository = academicClassRepository;
        this.academicCourseRepository = academicCourseRepository;
    }

    @Override
    public Test save(SaveAgendaDto saveAgendaDto) {
        Test test = new Test();
        test.setTest(saveAgendaDto.getTest());
        test.setDateOfTest(LocalDate.parse(saveAgendaDto.getDate()));
        test.setAcademicClass(academicClassRepository.findById(saveAgendaDto.getClassId()).get());
        test.setAcademicCourse(academicCourseRepository.findById(saveAgendaDto.getCourseId()).get());
        return testRepository.save(test);
    }

    @Override
    public Test update(SaveAgendaDto saveAgendaDto) {
        Test testOfCourse = getTestOfCourse(LocalDate.parse(saveAgendaDto.getDate()), saveAgendaDto.getClassId(), saveAgendaDto.getCourseId());
        if (saveAgendaDto.getTest() != null) {
            testOfCourse.setTest(saveAgendaDto.getTest());
        }
        return testRepository.save(testOfCourse);
    }

    @Override
    public Test getTestOfCourse(LocalDate date, Long classId, Long courseId) {
        return testRepository.findByDateOfTestAndAcademicClassIdAndAcademicCourseId(date, classId, courseId);
    }

    @Override
    public Test findByTest(String test) {
        return testRepository.findTestByTest(test);
    }

}
