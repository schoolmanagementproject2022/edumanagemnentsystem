package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoursesForTimetableRepositoryTest {

    private CoursesForTimetable activeCourse;
    private AcademicClass academicClass;
    private Teacher classroomTeacher;

    @Autowired
    private CoursesForTimetableRepository repository;

    @BeforeEach
    public void setup() {
        classroomTeacher = new Teacher(1L, "John", "Doe", new User(),
                "password", new HashSet(), new HashSet(), new HashSet());

        academicClass = new AcademicClass(1L, "8A", new HashSet(),
                new HashSet(), classroomTeacher, new ArrayList(), new HashSet());

        List<AcademicClass> listOfClasses = List.of(academicClass);
        activeCourse = new CoursesForTimetable(1L, "English", listOfClasses, "MONDAY", "ACTIVE");
    }

    @Test
    void testDeleteCourseByIdIsOk() {
        Long id = 1L;
        repository.create(activeCourse.getDayOfWeek(), activeCourse.getAcademicCourse(),
                activeCourse.getAcademicClass().get(0).getId(), activeCourse.getStatus());

        repository.deleteCourseById(id);


    }

    @Test
    void testCreateIsOk() {
        repository.create(activeCourse.getDayOfWeek(), activeCourse.getAcademicCourse(),
                activeCourse.getAcademicClass().get(0).getId(), activeCourse.getStatus());
    }


}