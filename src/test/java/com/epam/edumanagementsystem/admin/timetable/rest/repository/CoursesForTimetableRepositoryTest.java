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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoursesForTimetableRepositoryTest {

    private CoursesForTimetable activeCourse;
    private CoursesForTimetableDto activeCourseDto;

    @Autowired
    private CoursesForTimetableRepository repository;

    @BeforeEach
    public void setup() {
        Teacher teacher = new Teacher(1L, "John", "Doe", new User(), "password", new HashSet(), new HashSet(), new HashSet());
        Subject subject = new Subject(1L, "English", Set.of(teacher));
        AcademicClass academicClass = new AcademicClass(1L, "8A", new HashSet(), new HashSet(), teacher, new ArrayList(), new HashSet());
        AcademicCourse academicCourse = new AcademicCourse(1L, "English", subject, Set.of(teacher), Set.of(academicClass));

        activeCourse = new CoursesForTimetable(1L, academicCourse.getName(), List.of(academicClass), "MONDAY", "ACTIVE");
        activeCourseDto = new CoursesForTimetableDto(1L, academicCourse, academicClass, "MONDAY", "ACTIVE");
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
    void testUpdateCourseStatusById() {
        Long id = 1L;
        repository.updateCourseStatusById(id);
        CoursesForTimetable coursesForTimetable = repository.findById(id).get();

        assertThat(coursesForTimetable.getStatus()).isEqualTo("Not Active");
    }


    @Test
    void testCreateIsSuccess() {
        CoursesForTimetable course = repository.save(activeCourse);
        Optional<CoursesForTimetable> optionalCoursesForTimetable = repository.findById(course.getId());
        CoursesForTimetable coursesForTimetable = null;

        if (optionalCoursesForTimetable.isPresent()) {
            coursesForTimetable = optionalCoursesForTimetable.get();
        }

        assertThat(coursesForTimetable.getId()).isGreaterThan(0);
    }
}