package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.TestHelper;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.impl.SubjectServiceImpl;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest extends TestHelper {

    @Mock
    SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectServiceImpl service;
    Subject input;
    SubjectDto returned;
    Subject forUpdate;

    @BeforeEach
    void setUp() {
        input = createSubject();
        returned = returnedSubject();
        forUpdate = updateSubject();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        when(subjectRepository.findAll()).thenReturn(List.of(new Subject(), new Subject()));

        List<SubjectDto> all = service.findAll();
        assertEquals(2, all.size());

        verify(subjectRepository, times(1)).findAll();
        verifyNoMoreInteractions(subjectRepository);

    }

    @Test
    void create() {
        when(subjectRepository.save(any())).thenReturn(input);
        assumeTrue(input.getName().equals(input.getName()));

        Subject subject = service.save(input);
        assertEquals(subject, input);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void createSubjectButSubjectNull() {
        input = null;
        Assertions.assertThrows(NullPointerException.class, () -> service.save(input));
    }

    @Test
    void findSubjectBySubjectName() {
        when(subjectRepository.findByName(any())).thenReturn(input);
        SubjectDto subjectByName = service.findByName(input.getName());

        assertEquals(subjectByName.getId(), input.getId());
        assertEquals(subjectByName.getName(), input.getName());
        verify(subjectRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void find_by_name_throws_entity_not_found_exception() {
        when(subjectRepository.findByName(anyString())).thenReturn(input);
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findByName("hvhg"));
    }

    @Test
    void findAllTeachers() {
        when(subjectRepository.findByName(any())).thenReturn(input);
        Set<Teacher> teacherBySubjectName = service.findByName(input.getName()).getTeacherSet();

        assertEquals(teacherBySubjectName.size(), input.getTeacherSet().size());
        verify(subjectRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void update() {
        Mockito.when(subjectRepository.findByName(input.getName())).thenReturn(input);
        Mockito.when(subjectRepository.save(input)).thenReturn(input);

        Subject result = service.update(forUpdate);
        assertEquals(forUpdate.getName(), result.getName());
    }

    @Test
    void negativeUpdate() {
        forUpdate = null;
        Assertions.assertThrows(NullPointerException.class, () -> service.update(forUpdate));
    }

    @Test
    void findSubjectsByTeacherSetId() {
        when(subjectRepository.findSubjectsByTeacherSetId(any())).thenReturn(Set.of(input));
        Set<Subject> setOfSubjects = service.findAllByTeacherId(input.getId());

        assertThat(setOfSubjects).isNotNull();
        assertEquals(Set.of(input), setOfSubjects);
    }

}