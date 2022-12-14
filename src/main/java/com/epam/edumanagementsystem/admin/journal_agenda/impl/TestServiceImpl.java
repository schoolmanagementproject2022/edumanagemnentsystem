package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Test;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.repository.TestRepository;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.TestService;
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
        test.setCourseOfTest(academicCourseRepository.findById(saveAgendaDto.getCourseId()).get());
        return testRepository.save(test);
    }

}
