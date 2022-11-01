//package com.epam.edumanagementsystem.admin.impl;
//
//import com.epam.edumanagementsystem.admin.mapper.AcademicYearMapper;
//import com.epam.edumanagementsystem.admin.model.dto.AcademicYearDto;
//import com.epam.edumanagementsystem.admin.model.entity.AcademicYear;
//import com.epam.edumanagementsystem.admin.rest.repository.AcademicYearRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class AcademicYearServiceImplTest {
//
//    private AcademicYear academicYear;
//
//    @Mock
//    private AcademicYearRepository academicYearRepository;
//
//    @InjectMocks
//    private AcademicYearServiceImpl academicYearService;
//
//    @BeforeEach
//    void init() {
//        academicYear = new AcademicYear(1L, LocalDate.of(2022, 12, 5), LocalDate.of(2023, 12, 5));
//    }
//
//    @Test
//    @DisplayName("should create Academic year")
//    void testCreateSuccess() {
//        when(academicYearRepository.save(any(AcademicYear.class))).thenReturn(academicYear);
//        AcademicYear save = academicYearService.create(academicYear);
//        assertEquals(academicYear, save);
//    }
//
//    @Test
//    @DisplayName("should throw NullPointerException when given null")
//    void testCreateFail() {
//        assertThrows(NullPointerException.class, () -> academicYearService.create(null));
//    }
//
//    @Test
//    @DisplayName("should find all academic years")
//    void testFindAll() {
//        List<AcademicYear> academicYears = new ArrayList<>();
//        academicYears.add(academicYear);
//        when(academicYearRepository.findAll()).thenReturn(academicYears);
//        List<AcademicYearDto> academicYearsDtoList = academicYearService.findAll();
//        List<AcademicYear> allAcademicYears = AcademicYearMapper.toListOfAcademicYears(academicYearsDtoList);
//        assertEquals(academicYears, allAcademicYears);
//    }
//
//    @Test
//    @DisplayName("find academic year by given id successfully")
//    void testFindByIdSuccess() {
//        long id = 1;
//        when(academicYearRepository.findById(id)).thenReturn(Optional.ofNullable(academicYear));
//        AcademicYearDto academicYearDtoById = academicYearService.getById(id);
//        AcademicYear academicYearById = AcademicYearMapper.toAcademicYear(academicYearDtoById);
//        assertEquals(academicYear, academicYearById);
//    }
//
//    @Test
//    @DisplayName("should throw RuntimeException when can not find academic year by given id ")
//    void testFindByIdFail() {
//        assertThrows(RuntimeException.class, () -> academicYearService.getById(2L));
//    }
//
//    @Test
//    @DisplayName("should throw NullPointerException when given id is null")
//    void testFindByNullId() {
//        assertThrows(NullPointerException.class, () -> academicYearService.getById(null));
//    }
//}
