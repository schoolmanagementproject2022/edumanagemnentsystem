package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.TestHelper;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest()
class SubjectServiceImplTest extends TestHelper {

    @Mock
    SubjectRepository subjectRepository;
    @InjectMocks
    private final SubjectService service = Mockito.spy(new SubjectServiceImpl());
    Subject input;
    Subject returned;
    Subject forUpdate;


    @BeforeEach
    void setUp() {
        input = createSubject();
        returned = createSubject();
        forUpdate=updateSubject1();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        when(subjectRepository.findAll()).thenReturn(List.of(new Subject(), new Subject()));

        // test method
        List<Subject> all = service.findAll();
        assertEquals(2,all.size());

        verify(subjectRepository, times(1)).findAll();
        verifyNoMoreInteractions(subjectRepository);

    }

    @Test
    void create() {

        when(subjectRepository.findSubjectByName(input.getName())).thenReturn(returned);
        when(subjectRepository.save(any(Subject.class))).thenReturn(input);
        when(subjectRepository.save(input)).thenReturn(returned);

        assumeTrue(input.getName().equals(returned.getName()));

        Subject result1 = service.create(input);
        assertEquals(input, result1);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void findSubjectBySubjectName() {
        // mock your methods
        when(subjectRepository.findSubjectByName(any())).thenReturn(input);

        // test method
        Subject subjectByName = service.findSubjectBySubjectName(input.getName());

        // assertions
        assertEquals(subjectByName.getId(), input.getId());
        assertEquals(subjectByName.getName(), input.getName());

        // verifies
        verify(subjectRepository, times(1)).findSubjectByName(any(String.class));
    }

    @Test
    void findAllTeachers() {
        // mock your methods
        when(subjectRepository.findSubjectByName(any())).thenReturn(input);

        // test method
        Set<Teacher> teacherBySubjectName = service.findSubjectBySubjectName(input.getName()).getTeacherSet();

        // assertions
        assertEquals(teacherBySubjectName.size(), input.getTeacherSet().size());

        // verifies
        verify(subjectRepository, times(1)).findSubjectByName(any(String.class));
    }

    @Test
    void update() {

        //stub the data
        Mockito.when(subjectRepository.findSubjectByName(input.getName())).thenReturn(returned);
        Mockito.when(subjectRepository.save(returned)).thenReturn(forUpdate);

        //actual method call
        Subject result = service.update(returned);

        assertEquals(forUpdate.getName(), result.getName());
    }
    @Test
    void should_not_found_a_Subject_that_doesnt_exists() {
        // Arrange
        when(subjectRepository.findSubjectByName(anyString())).thenReturn(input);

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findSubjectBySubjectName("hvhg"));
    }
}