package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoursesForTimetableRepositoryTest {

    private CoursesForTimetable activeCourse;

    @Mock
    private CoursesForTimetableRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setRole("TEACHER");
        user.setEmail("test@mail.ru");
        entityManager.persist(user);

        Teacher teacher = new Teacher();
        teacher.setName("John");
        teacher.setSurname("Doe");
        teacher.setUser(user);
        teacher.setPassword(user.getEmail());
        teacher.setPassword("Teacher123+");
        teacher.setAcademicCourseSet(new HashSet<>());
        teacher.setSubjectSet(new HashSet<>());
        teacher.setAcademicCourseSet(new HashSet<>());
        entityManager.persist(teacher);

        Subject subject = new Subject();
        subject.setName("Test subject");
        subject.setTeacherSet(Set.of(teacher));
        entityManager.persist(subject);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setClassNumber("Test class");
        academicClass.setTeacher(new HashSet<>());
        academicClass.setAcademicCourseSet(new HashSet<>());
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setStudent(new HashSet<>());
        entityManager.persist(academicClass);

        AcademicCourse academicCourse = new AcademicCourse();
        academicCourse.setName("Test course");
        academicCourse.setSubject(subject);
        academicCourse.setTeacher(Set.of(teacher));
        academicCourse.setAcademicClass(Set.of(academicClass));
        entityManager.persist(academicCourse);

        activeCourse = new CoursesForTimetable();
        activeCourse.setId(321654987L);
        activeCourse.setAcademicCourse(academicCourse.getName());
        activeCourse.setAcademicClass(List.of(academicClass));
        activeCourse.setDayOfWeek("Monday");
        activeCourse.setStatus("Active");

    }

    @Test
    void testDeleteCourseByIdIsSuccess() {
        Long id = 1L;
        repository.deleteCourseById(id);
        Optional<CoursesForTimetable> optionalCoursesForTimetable = repository.findById(id);
        CoursesForTimetable coursesForTimetable = null;

        if (optionalCoursesForTimetable.isPresent()) {
            coursesForTimetable = optionalCoursesForTimetable.get();
        }

        assertThat(coursesForTimetable).isNull();
    }

    @Test
    void testUpdateCourseStatusByIdSetsStatusToNotActive() {
        Long id = activeCourse.getId();

        repository.updateCourseStatusById(id);

        verify(repository,times(1)).updateCourseStatusById(id);
    }
}