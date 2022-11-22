package com.epam.edumanagementsystem.teacher.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.CoursesForTimetableRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {

    private Teacher teacher;
    @Autowired
    private TeacherRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setRole("TEACHER");
        user.setEmail("test@mail.ru");
        entityManager.persist(user);

        Teacher teacherForSave = new Teacher();
        teacherForSave.setName("John");
        teacherForSave.setSurname("Doe");
        teacherForSave.setUser(user);
        teacherForSave.setPassword("Teacher123+");

        Subject subject = new Subject();
        subject.setName("Test subject");
        subject.setTeacherSet(Set.of(teacherForSave));
        entityManager.persist(subject);

        teacher = repository.save(teacherForSave);

    }

    @Test
    void testFindSubjectsByTeacherIdReturnsCorrectList() {
        String subjectName = "Test subject";

        List<String> listOfSubjects = repository.findSubjectsByTeacherId(teacher.getId());

        assertThat(listOfSubjects).isNotNull();
        assertEquals(subjectName,listOfSubjects.get(0));
    }
}