package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.config.PostgresTestContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@EnableConfigurationProperties
class AcademicCourseRepositoryTest extends PostgresTestContainerConfig {

    @Autowired
    private AcademicCourseRepository academicCourseRepository;

    @Test
    @Sql(value = "classpath:testDb/CreateAcademicCourse.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:testDb/DeleteAcademicCourse.sql", executionPhase = AFTER_TEST_METHOD)
    void testFindAcademicCourseByTrueName() {
        AcademicCourse academicCourseByName = academicCourseRepository.findAcademicCourseByName("poxos");
        assertEquals("poxos", academicCourseByName.getName());
    }

    @Test
    @Sql(value = "classpath:testDb/CreateAcademicCourse.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:testDb/DeleteAcademicCourse.sql", executionPhase = AFTER_TEST_METHOD)
    void findAcademicCourseById() {
        AcademicCourse academicCourseById = academicCourseRepository.findAcademicCourseById(1L);
        assertEquals(1L, academicCourseById.getId());
    }
}