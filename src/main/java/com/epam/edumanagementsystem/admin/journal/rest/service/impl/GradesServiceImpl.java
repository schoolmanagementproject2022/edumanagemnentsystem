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
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER_JOURNAL;

@Service
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;
    private final AcademicCourseService academicCourseService;
    private final ClassworkService classworkService;
    private final HomeworkService homeworkService;
    private final TestService testService;
    private final StudentService studentService;

    public GradesServiceImpl(GradesRepository gradesRepository, AcademicCourseService academicCourseService,
                             ClassworkService classworkService, HomeworkService homeworkService, TestService testService,
                             StudentService studentService) {
        this.gradesRepository = gradesRepository;
        this.academicCourseService = academicCourseService;
        this.classworkService = classworkService;
        this.homeworkService = homeworkService;
        this.testService = testService;
        this.studentService = studentService;
    }

    @Override
    public Grades save(GradesDto gradesDto, String date, Long studentId) {
        Grades grades = GradesMapper.toGrades(gradesDto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_JOURNAL);
        LocalDate localdate = LocalDate.parse(date, formatter);
        grades.setDate(localdate);
        StudentDto student = studentService.findById(studentId);
        grades.setStudent(StudentMapper.mapToStudent(student));
        if (grades.getGradeHomework() != 0) {
            grades.setHomework(homeworkService.findByHomework(gradesDto.getHomework()));
        }
        if (grades.getGradeTest() != 0) {
            grades.setTest(testService.findByTest(gradesDto.getTest()));
        }
        if (grades.getGradeClasswork() != 0) {
            grades.setClasswork(classworkService.findByClasswork(gradesDto.getClasswork()));
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
    public void update(GradesDto gradesDto, String date, Long studentId) {
        StudentDto student = studentService.findById(studentId);
        gradesDto.setStudent(StudentMapper.mapToStudent(student));
        Grades editedGrades = GradesMapper.toGrades(gradesDto);
        if (existByDateStudentIdAndCourseId(date, gradesDto.getStudent().getId(), gradesDto.getCourseId())) {
            Grades grade = findByDateStudentIdAndCourseId(date, gradesDto.getStudent().getId(), gradesDto.getCourseId());
            grade.setGradeClasswork(editedGrades.getGradeClasswork());
            grade.setGradeHomework(editedGrades.getGradeHomework());
            grade.setGradeTest(editedGrades.getGradeTest());
            if (grade.getGradeHomework() != 0) {
                grade.setHomework(homeworkService.findByHomework(gradesDto.getHomework()));
            }
            if (grade.getGradeTest() != 0) {
                grade.setTest(testService.findByTest(gradesDto.getTest()));
            }
            if (grade.getGradeClasswork() != 0) {
                grade.setClasswork(classworkService.findByClasswork(gradesDto.getClasswork()));
            }
            gradesRepository.save(grade);
        }
    }
}
