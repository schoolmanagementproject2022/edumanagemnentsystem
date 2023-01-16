package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;

import java.util.*;
import java.util.stream.Collectors;

public class SubjectMapper {

    private SubjectMapper() {
        throw new IllegalStateException();
    }

    public static Subject toSubject(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setId(subjectDto.getId());
        subject.setName(subjectDto.getName());
        subject.setTeacherSet(subjectDto.getTeacherSet());
        return subject;
    }

    public static SubjectDto toDto(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setTeacherSet(subject.getTeacherSet());
        return subjectDto;
    }

    public static List<SubjectDto> toSubjectDtoList(List<Subject> subjectList) {
        return subjectList.stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Subject> toSubjectsList(List<SubjectDto> subjectDtoList) {
        return subjectDtoList.stream()
                .map(SubjectMapper::toSubject)
                .collect(Collectors.toList());
    }

    public static Set<SubjectDto> toSetOfSubjectDto(Set<Subject> subjectSet){
        return subjectSet.stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toSet());
    }

}