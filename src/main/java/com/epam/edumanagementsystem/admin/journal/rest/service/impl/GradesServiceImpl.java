package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.journal.rest.mapper.GradesMapper;
import com.epam.edumanagementsystem.admin.journal.rest.repository.GradesRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradesService;
import com.epam.edumanagementsystem.admin.journal.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.TestService;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER_JOURNAL;

@Service
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;
    private final AcademicCourseService academicCourseService;
    private final StudentService studentService;
    private final ClassworkService classworkService;
    private final HomeworkService homeworkService;
    private final TestService testService;

    public GradesServiceImpl(GradesRepository gradesRepository, AcademicCourseService academicCourseService,
                             StudentService studentService, ClassworkService classworkService, HomeworkService homeworkService, TestService testService) {
        this.gradesRepository = gradesRepository;
        this.academicCourseService = academicCourseService;
        this.studentService = studentService;
        this.classworkService = classworkService;
        this.homeworkService = homeworkService;
        this.testService = testService;
    }

    @Override
    public Grades save(GradesDto gradesDto, String date) {
        Grades grades = GradesMapper.toGrades(gradesDto);
        grades.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMATTER_JOURNAL)));
        if (grades.getGradeHomework() != 0) {
            grades.setHomework(homeworkService.findByDescriptionAndAcademicCourseIdAndDateOfHomework(gradesDto.getHomework(), gradesDto.getCourseId(), gradesDto.getDate()));
        }
        if (grades.getGradeTest() != 0) {
            grades.setTest(testService.findByTestDescriptionCourseIdAndDateOdTest(gradesDto.getTest(), gradesDto.getCourseId(), gradesDto.getDate()));
        }
        if (grades.getGradeClasswork() != 0) {
            grades.setClasswork(classworkService.findByDescriptionAndAcademicCourseIdAndDateOfClasswork(gradesDto.getClasswork(), gradesDto.getCourseId(), gradesDto.getDate()));
        }
        grades.setAcademicCourse(AcademicCourseMapper.toAcademicCourse(academicCourseService.findById(gradesDto.getCourseId())));
        return gradesRepository.save(grades);
    }

    @Override
    public boolean existByDateStudentIdAndCourseId(String date, Long studentId, Long courseId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_JOURNAL);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return gradesRepository.existsGradesByDateAndStudentIdAndAcademicCourseId(localDate, studentId, courseId);
    }

    @Override
    public Optional<Grades> findByStudentId(Long id) {
        return gradesRepository.findByStudentId(id);
    }

    @Override
    public Grades findByDateStudentIdAndCourseId(String date, Long studentId, Long courseId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_JOURNAL);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return gradesRepository.findByDateAndStudentIdAndAcademicCourseId(localDate, studentId, courseId);
    }

    @Override
    public void update(GradesDto gradesDto, String date) {
        Grades editedGrades = GradesMapper.toGrades(gradesDto);
        if (existByDateStudentIdAndCourseId(date, gradesDto.getStudent().getId(), gradesDto.getCourseId())) {
            Grades grade = findByDateStudentIdAndCourseId(date, gradesDto.getStudent().getId(), gradesDto.getCourseId());
            grade.setGradeClasswork(editedGrades.getGradeClasswork());
            grade.setGradeHomework(editedGrades.getGradeHomework());
            grade.setGradeTest(editedGrades.getGradeTest());
            if (grade.getGradeHomework() != 0) {
                grade.setHomework(homeworkService.findByDescriptionAndAcademicCourseIdAndDateOfHomework(gradesDto.getHomework(), gradesDto.getCourseId(), gradesDto.getDate()));
            }
            if (grade.getGradeTest() != 0) {
                grade.setTest(testService.findByTestDescriptionCourseIdAndDateOdTest(gradesDto.getTest(), gradesDto.getCourseId(), gradesDto.getDate()));
            }
            if (grade.getGradeClasswork() != 0) {
                grade.setClasswork(classworkService.findByDescriptionAndAcademicCourseIdAndDateOfClasswork(gradesDto.getClasswork(), gradesDto.getCourseId(), gradesDto.getDate()));
            }
            gradesRepository.save(grade);
        }
    }

    @Override
    public List<GradesDto> findAllGradesInClassForWeek(String date, Long classId, Long courseId) {
        Set<StudentDto> allStudentsInClass = new LinkedHashSet<>();
        List<GradesDto> allGradesInClass = new ArrayList<>();
        studentService.findAll().stream()
                .filter(studentDto -> studentDto.getAcademicClass() != null && studentDto.getAcademicClass().getId().equals(classId))
                .collect(Collectors.toCollection(()->allStudentsInClass));

        allStudentsInClass.stream()
                .filter(studentDto -> existByDateStudentIdAndCourseId(date, studentDto.getId(),courseId))
                .map(studentDto -> GradesMapper.toDto(findByDateStudentIdAndCourseId(date, studentDto.getId(), courseId)))
                .collect(Collectors.toCollection(()->allGradesInClass));
        return allGradesInClass;
    }
}
