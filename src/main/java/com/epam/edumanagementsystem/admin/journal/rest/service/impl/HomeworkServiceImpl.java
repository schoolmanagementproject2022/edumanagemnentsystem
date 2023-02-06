package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.rest.repository.HomeworkRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final AcademicClassRepository academicClassRepository;
    private final AcademicCourseRepository academicCourseRepository;

    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, AcademicClassRepository academicClassRepository, AcademicCourseRepository academicCourseRepository) {
        this.homeworkRepository = homeworkRepository;
        this.academicClassRepository = academicClassRepository;
        this.academicCourseRepository = academicCourseRepository;
    }

    @Override
    public Homework save(SaveAgendaDto saveAgendaDto) {
        Homework homework = new Homework();
        homework.setHomework(saveAgendaDto.getHomework());
        homework.setDateOfHomework(LocalDate.parse(saveAgendaDto.getDate()));
        homework.setAcademicClass(academicClassRepository.findById(saveAgendaDto.getClassId()).get());
        homework.setCourseOfHomework(academicCourseRepository.findById(saveAgendaDto.getCourseId()).get());
        return homeworkRepository.save(homework);
    }

    @Override
    public Homework update(SaveAgendaDto saveAgendaDto) {
        Homework homeworkOfCourse = getHomeworkOfCourse(LocalDate.parse(saveAgendaDto.getDate()), saveAgendaDto.getClassId(), saveAgendaDto.getCourseId());
        if (saveAgendaDto.getHomework() != null) {
            homeworkOfCourse.setHomework(saveAgendaDto.getHomework());
        }
        return homeworkRepository.save(homeworkOfCourse);
    }

    @Override
    public Homework getHomeworkOfCourse(LocalDate date, Long classId, Long courseId) {
        return homeworkRepository.findByDateOfHomeworkAndAcademicClassIdAndAcademicCourseId(date, classId, courseId);
    }

    @Override
    public Homework findByHomework(String homework) {
        return homeworkRepository.findHomeworkByHomework(homework);
    }
}
