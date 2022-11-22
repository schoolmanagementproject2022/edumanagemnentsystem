package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.SubjectMapper;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> findAll() {
        List<Subject> allSubjects = subjectRepository.findAll();
        return SubjectMapper.toSubjectDtoList(allSubjects);
    }

    @Override
    public Subject create(Subject subject) {
        if (subject == null) {
            throw new NullPointerException();

        } else {
            return subjectRepository.save(subject);

        }
    }

    @Override
    public SubjectDto findSubjectBySubjectName(String name) {
        Subject subjectByName = subjectRepository.findSubjectByName(name);

        if (!subjectByName.getName().equalsIgnoreCase(name)) {
            throw new EntityNotFoundException();
        }
        return SubjectMapper.toDto(subjectByName);

    }

    @Override
    public Set<TeacherDto> findAllTeachers(String name) {
        return TeacherMapper.toSetOfTeachersDto(findSubjectBySubjectName(name).getTeacherSet());
    }

    @Override
    public Subject update(Subject subject) {
        SubjectDto subjectBySubjectName = findSubjectBySubjectName(subject.getName());
        if (subject.getName() != null) {
            subjectBySubjectName.setName(subject.getName());
        }
        if (subject.getTeacherSet() != null) {
            Set<Teacher> teacherSet = subject.getTeacherSet();
            for (Teacher teacher : teacherSet) {
                subjectBySubjectName.getTeacherSet().add(teacher);
            }
        }
        Subject toSubject = SubjectMapper.toSubject(subjectBySubjectName);
        return subjectRepository.save(toSubject);
    }



    @Override
    public Set<Subject> findSubjectsByTeacherSetId(Long teacherId) {
        return subjectRepository.findSubjectsByTeacherSetId(teacherId);
    }
}

