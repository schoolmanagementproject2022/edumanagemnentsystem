package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
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
    public Classwork save(SaveAgendaDto saveAgendaDto) {
        Classwork classwork = new Classwork();
        classwork.setClasswork(saveAgendaDto.getClasswork());
        String[] split = saveAgendaDto.getDate().split("/");
        classwork.setDateOfClasswork(LocalDate.parse(split[0]));
        classwork.setAcademicClass(academicClassRepository.findById(saveAgendaDto.getClassId()).get());
        classwork.setAcademicCourse(academicCourseRepository.findById(saveAgendaDto.getCourseId()).get());
        return classworkRepository.save(classwork);
    }

    @Override
    public Classwork update(SaveAgendaDto saveAgendaDto) {
        Classwork classWorkOfCourse = getClassWorkOfCourse(LocalDate.parse(saveAgendaDto.getDate()), saveAgendaDto.getClassId(), saveAgendaDto.getCourseId());
        if (saveAgendaDto.getClasswork() != null) {
            classWorkOfCourse.setClasswork(saveAgendaDto.getClasswork());
        }
        return classworkRepository.save(classWorkOfCourse);
    }

    @Override
    public Classwork getClassWorkOfCourse(LocalDate date, Long classId, Long courseId) {
        return classworkRepository.findByDateOfClassworkAndAcademicClassIdAndAcademicCourseId(date, classId, courseId);
    }
}
