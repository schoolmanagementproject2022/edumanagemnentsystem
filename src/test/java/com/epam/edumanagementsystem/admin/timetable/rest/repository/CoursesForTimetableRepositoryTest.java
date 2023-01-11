package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoursesForTimetableRepositoryTest {

    private Long activeCourseId;
    private Long editedCourseId;
    private Long notActiveCourseId;
    private Long academicClass1Id;
    private Long academicClass2Id;
    private Long academicClass3Id;
    private AcademicCourse academicCourse;
    private AcademicClass academicClass;
    private Long academicClassId;

    @Autowired
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
        teacher.setPassword("Teacher123+");
        entityManager.persist(teacher);

        Subject subject = new Subject();
        subject.setName("Test subject");
        subject.setTeacherSet(Set.of(teacher));
        entityManager.persist(subject);

        academicClass = new AcademicClass();
        academicClass.setClassNumber("Test class");
        academicClass.setTeachers(new HashSet<>());
        academicClass.setAcademicCourseSet(new HashSet<>());
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setStudent(new HashSet<>());
        academicClassId = entityManager.persist(academicClass).getId();

        AcademicClass academicClass1 = new AcademicClass();
        academicClass1.setClassNumber("Test class1");
        academicClass1.setTeachers(new HashSet<>());
        academicClass1.setAcademicCourseSet(new HashSet<>());
        academicClass1.setClassroomTeacher(teacher);
        academicClass1.setCoursesForTimetableList(new ArrayList<>());
        academicClass1.setStudent(new HashSet<>());
        academicClass1Id = entityManager.persist(academicClass1).getId();

        AcademicClass academicClass2 = new AcademicClass();
        academicClass2.setClassNumber("Test class2");
        academicClass2.setTeachers(new HashSet<>());
        academicClass2.setAcademicCourseSet(new HashSet<>());
        academicClass2.setClassroomTeacher(teacher);
        academicClass2.setCoursesForTimetableList(new ArrayList<>());
        academicClass2.setStudent(new HashSet<>());
        academicClass2Id = entityManager.persist(academicClass2).getId();

        AcademicClass academicClass3 = new AcademicClass();
        academicClass3.setClassNumber("Test class3");
        academicClass3.setTeachers(new HashSet<>());
        academicClass3.setAcademicCourseSet(new HashSet<>());
        academicClass3.setClassroomTeacher(teacher);
        academicClass3.setCoursesForTimetableList(new ArrayList<>());
        academicClass3.setStudent(new HashSet<>());
        academicClass3Id = entityManager.persist(academicClass3).getId();

        academicCourse = new AcademicCourse();
        academicCourse.setName("Test course");
        academicCourse.setSubject(subject);
        academicCourse.setTeachers(Set.of(teacher));
        academicCourse.setAcademicClass(Set.of(academicClass1));
        entityManager.persist(academicCourse).getId();

        CoursesForTimetableDto activeCourseDto = new CoursesForTimetableDto();
        activeCourseDto.setAcademicCourse(academicCourse);
        activeCourseDto.setAcademicClass(academicClass1);
        activeCourseDto.setDayOfWeek("Monday");
        activeCourseDto.setStatus("Active");

        CoursesForTimetableDto editCourseDto = new CoursesForTimetableDto();
        editCourseDto.setAcademicCourse(academicCourse);
        editCourseDto.setAcademicClass(academicClass2);
        editCourseDto.setDayOfWeek("Monday");
        editCourseDto.setStatus("Edit");

        CoursesForTimetableDto notActiveCourseDto = new CoursesForTimetableDto();
        notActiveCourseDto.setAcademicCourse(academicCourse);
        notActiveCourseDto.setAcademicClass(academicClass3);
        notActiveCourseDto.setDayOfWeek("Monday");
        notActiveCourseDto.setStatus("Not Active");

        repository.create(activeCourseDto.getDayOfWeek(), activeCourseDto.getAcademicCourse().getName(),
                activeCourseDto.getAcademicClass().getId(), activeCourseDto.getStatus());
        activeCourseId = repository.findCoursesByAcademicClassId(academicClass1Id).get(0).getId();

        repository.create(editCourseDto.getDayOfWeek(), editCourseDto.getAcademicCourse().getName(),
                editCourseDto.getAcademicClass().getId(), editCourseDto.getStatus());
        editedCourseId = repository.findCoursesByAcademicClassId(academicClass2Id).get(0).getId();

        repository.create(notActiveCourseDto.getDayOfWeek(), notActiveCourseDto.getAcademicCourse().getName(),
                notActiveCourseDto.getAcademicClass().getId(), notActiveCourseDto.getStatus());

        notActiveCourseId = repository.findCoursesByAcademicClassId(academicClass3Id).get(0).getId();
    }

    @Test
    void testDeleteCourseByIdReturnsEmptyOptionalCourse() {
        repository.deleteById(notActiveCourseId);
        Optional<CoursesForTimetable> deletedCourse = repository.findById(notActiveCourseId);
        assertThat(deletedCourse.isEmpty());
    }

    @Test
    void testUpdateCourseStatusToActiveByIdIsOk() {
        String statusAfterUpdate = "Active";

        repository.updateCourseStatusToActiveById(notActiveCourseId);

        Optional<CoursesForTimetable> courseWithChangedStatus = repository.findById(notActiveCourseId);
        assertThat(courseWithChangedStatus.isPresent());
        assertThat(courseWithChangedStatus.get().getStatus().equalsIgnoreCase(statusAfterUpdate));
    }

    @Test
    void testUpdateCourseStatusByIdSetsStatusToNotActive() {
        String statusAfterUpdate = "Not Active";

        repository.updateCourseStatusById(activeCourseId);

        Optional<CoursesForTimetable> courseWithChangedStatus = repository.findById(activeCourseId);
        assertThat(courseWithChangedStatus.isPresent());
        assertThat(courseWithChangedStatus.get().getStatus().equalsIgnoreCase(statusAfterUpdate));
    }

    @Test
    void testFindCoursesByDayOfWeekAndAcademicClassIdReturnsCorrectList() {
        String dayOfWeek = "Monday";
        Long academicClassId = academicClass1Id;

        List<CoursesForTimetable> listOfCourses = repository.findCoursesByDayOfWeekAndAcademicClassId(dayOfWeek, academicClassId);

        assertThat(listOfCourses).isNotNull();
        assertThat(listOfCourses.get(0).getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                listOfCourses.get(0).getAcademicClass().get(0).getId().equals(academicClassId));
    }

    @Test
    void testFindCoursesWithEditStatusByAcademicCourseIdReturnsCorrectList() {
        List<CoursesForTimetable> listOfEditedCourses = repository.findCoursesWithEditStatusByAcademicClassId(academicClass2Id);
        assertThat(listOfEditedCourses).isNotNull();
        assertEquals(1, listOfEditedCourses.size());
    }

    @Test
    void testCreateIsOk() {
        String dayOfWeek = "Test day of week";
        String status = "Test status";

        CoursesForTimetableDto courseDto = new CoursesForTimetableDto();
        courseDto.setAcademicCourse(academicCourse);
        courseDto.setAcademicClass(academicClass);
        courseDto.setDayOfWeek(dayOfWeek);
        courseDto.setStatus(status);

        repository.create(courseDto.getDayOfWeek(), courseDto.getAcademicCourse().getName(),
                courseDto.getAcademicClass().getId(), courseDto.getStatus());

        CoursesForTimetable savedCourse = repository.findCoursesByAcademicClassId(academicClassId).get(0);

        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                savedCourse.getStatus().equalsIgnoreCase(status));
    }

    @Test
    void testFindCoursesWithActiveStatusByAcademicCourseIdReturnsCorrectList() {
        List<CoursesForTimetable> listOfActiveCourses = repository.findCoursesWithActiveStatusByAcademicClassId(academicClass1Id);
        assertThat(listOfActiveCourses).isNotNull();
        assertEquals(1, listOfActiveCourses.size());
    }

    @Test
    void testFindCoursesWithNotActiveStatusByAcademicCourseIdReturnsCorrectList() {
        List<CoursesForTimetable> listOfNotActiveCourses = repository.findCoursesWithNotActiveStatusByAcademicClassId(academicClass3Id);
        assertThat(listOfNotActiveCourses).isNotNull();
        assertEquals(1, listOfNotActiveCourses.size());
    }

    @Test
    void testFindCoursesByDayOfWeekAndStatusAndAcademicClassIdReturnsCorrectList() {
        String dayOfWeek = "Monday";
        String status = "Active";
        Long academicClassId = academicClass1Id;

        List<CoursesForTimetable> listOfCourses = repository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId);

        assertThat(listOfCourses).isNotNull();
        assertThat(listOfCourses.get(0).getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                listOfCourses.get(0).getStatus().equalsIgnoreCase(status) &&
                listOfCourses.get(0).getAcademicClass().get(0).getId().equals(academicClassId));
    }
}