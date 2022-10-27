package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectServiceImpl() {

    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject create(Subject subject) {
        if (subject == null) {
            throw new NullPointerException();
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectBySubjectName(String name) {
        Subject subjectByName = subjectRepository.findSubjectByName(name);

        if (!subjectByName.getName().equalsIgnoreCase(name)) {
            throw new EntityNotFoundException();
        }
        return subjectByName;

    }

    @Override
    public Set<Teacher> findAllTeachers(String name) {
        return findSubjectBySubjectName(name).getTeacherSet();
    }

    @Override
    public Subject update(Subject subject) {
        Subject subjectBySubjectName = findSubjectBySubjectName(subject.getName());
        if (subject.getName() != null) {
            subjectBySubjectName.setName(subject.getName());
        }
        if (subject.getTeacherSet() != null) {
            Set<Teacher> teacherSet = subject.getTeacherSet();
            for (Teacher teacher : teacherSet) {
                subjectBySubjectName.getTeacherSet().add(teacher);
            }
        }
        return create(subjectBySubjectName);
    }
}

