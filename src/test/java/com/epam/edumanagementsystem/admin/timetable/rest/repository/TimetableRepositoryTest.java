package com.epam.edumanagementsystem.admin.timetable.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TimetableRepositoryTest {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private AcademicClassRepository academicClassRepository;

    private Timetable timetable;

    @BeforeEach()
    void setUp() {
        timetableRepository.save(timetable = new Timetable(LocalDate.now(), LocalDate.now().plusMonths(3), "Active",
                academicClassRepository.save(new AcademicClass("2A"))));
    }

    @Test
    void getTimetableByAcademicClassIdNotNullAndSameCases() {
        Timetable savedTimetable = timetableRepository.save(timetable);
        Timetable timetableByAcademicClassId = timetableRepository.getTimetableByAcademicClassId(savedTimetable.getAcademicClass()
                .getId());

        Assertions.assertNotNull(timetableByAcademicClassId);
        Assertions.assertSame(savedTimetable.getId(),timetableByAcademicClassId.getId());
    }

    @Test
    void updateTimetableStatusByAcademicClassIdNotNullAndSameCases() {
        String statusAfterUpdate = "Not Active";
        timetableRepository.updateTimetableStatusByAcademicClassId(statusAfterUpdate, timetable.getAcademicClass().getId());

        Optional<Timetable> timetableById = timetableRepository.findById(timetable.getId());
        Assertions.assertNotNull(timetableById);
        assertThat(timetableById.get().getStatus().equalsIgnoreCase(statusAfterUpdate));
    }

    @Test
    void updateTimetableDatesAndStatusByAcademicClassId() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(5);
        String statusAfterUpdate = "Not Active";
        timetableRepository.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate, statusAfterUpdate,
                timetable.getAcademicClass().getId());

        Optional<Timetable> timetableById = timetableRepository.findById(timetable.getId());
        Assertions.assertNotNull(timetableById);
        assertThat(timetableById.get().getStatus().equalsIgnoreCase(statusAfterUpdate));
        assertThat(timetableById.get().getStartDate().equals(startDate));
        assertThat(timetableById.get().getEndDate().equals(endDate));
    }
}