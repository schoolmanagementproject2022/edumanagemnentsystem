package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentEditDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.SELECT_FIELD;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.*;

@Controller
@RequestMapping("/classes")
@Tag(name = "Academic class")
public class AcademicClassController {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public AcademicClassController(AcademicClassService academicClassService,
                                   AcademicCourseService academicCourseService,
                                   StudentService studentService, TeacherService teacherService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Shows academic class section on admin's dashboard with existing courses")
    public String openAcademicClassSection(Model model) {
        model.addAttribute("academicClasses", academicClassService.findAll());
        model.addAttribute("academicClass", new AcademicClassDto());
        return ACADEMIC_CLASSES_SECTION;
    }

    @PostMapping
    @Operation(summary = "Saves the created academic class")
    public String save(@ModelAttribute("academicClass") @Valid AcademicClassDto academicClassDto,
                       BindingResult result, Model model) {

        model.addAttribute("academicClasses", academicClassService.findAll());
        academicClassService.checkClassDuplication(academicClassDto, result, model);

        if (result.hasErrors()) {
            return ACADEMIC_CLASSES_SECTION;
        }
        academicClassService.save(academicClassDto);
        return ACADEMIC_CLASSES_REDIRECT;
    }

    @GetMapping("/{name}/courses")
    @Operation(summary = "Shows academic courses in academic class section")
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model) {
        Set<AcademicCourseDto> academicCoursesInClass = academicCourseService.findAllAcademicCoursesInClassByName(name);

        model.addAttribute("academicCourseSet", academicCoursesInClass);
        model.addAttribute("allTeacherByAcademicCourse", teacherService.findAllTeachersInAllCourses());
        model.addAttribute("existingClass", new AcademicClass());

        List<AcademicCourseDto> coursesForSelection = academicCourseService.findAll().stream()
                .filter(course -> !academicCoursesInClass.contains(course))
                .filter(course -> !course.getTeachers().isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("coursesForSelect", coursesForSelection);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    @Operation(summary = "Adds new academic courses and teachers for selected academic class")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingClass") AcademicClassDto academicClassDto,
                                                 @PathVariable("name") String name, Model model) {
        Set<AcademicCourseDto> academicCoursesInClass = academicCourseService.findAllAcademicCoursesInClassByName(name);
        model.addAttribute("allTeacherByAcademicCourse", teacherService.findAllTeachersInAllCourses());

        List<AcademicCourseDto> coursesForSelection = academicCourseService.findAll().stream()
                .filter(course -> !academicCoursesInClass.contains(course))
                .filter(course -> !course.getTeachers().isEmpty())
                .collect(Collectors.toList());

        model.addAttribute("coursesForSelect", coursesForSelection);
        model.addAttribute("academicCourseSet", academicCoursesInClass);

        if (checkCourseAndTeacherForAcademicClass(academicClassDto, model)) {
            return "academicCourseForAcademicClass";
        }

        academicClassService.updateCourses(academicClassDto, name);
        return ACADEMIC_CLASSES_REDIRECT + name + ACADEMIC_COURSES_URL;
    }

    @GetMapping("/{name}/classroom")
    @Operation(summary = "Shows teachers who can be selected as a classroom teacher for selected academic class")
    public String classroomTeacherForAcademicClass(@PathVariable("name") String name, Model model) {
        AcademicClassDto academicClassByName = academicClassService.findByClassNumber(name);
        Set<Teacher> allTeachersInClassName = academicClassByName.getTeachers();

        for (AcademicClassDto allClass : academicClassService.findAll()) {
            if (allClass.getClassroomTeacher() != null) {
                allTeachersInClassName.removeIf(teacher -> teacher == allClass.getClassroomTeacher());
            }
        }

        model.addAttribute("existingClassroomTeacher", new AcademicClassDto());
        model.addAttribute("teachers", allTeachersInClassName);
        if (academicClassByName.getClassroomTeacher() == null) {
            return CLASSROOM_TEACHER_SECTION;
        } else {
            model.addAttribute("classroomTeacher", academicClassByName.getClassroomTeacher());
            return CLASSROOM_TEACHER_SECTION;
        }
    }

    @PostMapping("{name}/classroom")
    @Operation(summary = "Adds a classroom teacher for selected academic class")
    public String addClassroomTeacherInAcademicClass(@ModelAttribute("existingClassroomTeacher") AcademicClassDto academicClassDto,
                                                     @PathVariable("name") String name,
                                                     Model model) {
        model.addAttribute("teachers", academicClassService.findByClassNumber(name).getTeachers());
        if (academicClassDto.getClassroomTeacher() == null) {
            model.addAttribute("blank", SELECT_FIELD);
            return CLASSROOM_TEACHER_SECTION;
        }
        for (AcademicClassDto classDto : academicClassService.findAll()) {
            if (academicClassDto.getClassroomTeacher()
                    .equals(classDto.getClassroomTeacher())) {
                model.addAttribute("duplicate", "This Teacher is already classroom teacher");
                return CLASSROOM_TEACHER_SECTION;
            }
        }
        academicClassService.updateClassroomTeacher(academicClassDto, name);
        return ACADEMIC_CLASSES_REDIRECT + name + CLASSROOM_URL;
    }

    @GetMapping("/{name}/students")
    @Operation(summary = "Gets the list of the student for the academic class")
    public String showAcademicClassStudents(@PathVariable("name") String name, Model model) {
        Long id = academicClassService.findByClassNumber(name).getId();
        model.addAttribute("studentsInAcademicClass", studentService.findByAcademicClassId(id));
        model.addAttribute("students", studentService.findStudentsWithoutConnectionWithClass());
        model.addAttribute("existingAcademicClass", academicClassService.findByClassNumber(name));
        return ACADEMIC_CLASS_SECTION_FOR_STUDENTS;
    }

    @PostMapping("{name}/students")
    public String addNewStudents(@ModelAttribute("existingAcademicClass") AcademicClassDto academicClassDto,
                                 @PathVariable("name") String name, Model model) {
        if (academicClassDto.getStudents().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            model.addAttribute("studentsInAcademicClass", studentService
                    .findByAcademicClassId(academicClassService.findByClassNumber(name).getId()));
            model.addAttribute("students", studentService.findStudentsWithoutConnectionWithClass());
            return ACADEMIC_CLASS_SECTION_FOR_STUDENTS;
        }
        AcademicClassDto byClassNumber = academicClassService.findByClassNumber(name);
        byClassNumber.getStudents().addAll(academicClassDto.getStudents());
        AcademicClass academicClass = AcademicClassMapper.toAcademicClass(byClassNumber);
        for (Student student : academicClassDto.getStudents()) {
            StudentEditDto studentEditDto = StudentMapper.toStudentEditDto(student);
            studentEditDto.setAcademicClass(academicClass);
            studentService.updateFields(studentEditDto);
        }
        return ACADEMIC_CLASSES_REDIRECT + name + STUDENTS_URL;
    }

    @GetMapping("/{name}/teachers")
    @Operation(summary = "Gets the list of the teachers for the academic class")
    public String teachersForAcademicClass(Model model, @PathVariable("name") String name) {
        model.addAttribute("teachers", TeacherMapper.mapToTeacherDtoSet(academicClassService.findByClassNumber(name).getTeachers()));
        model.addAttribute("allTeacherByAcademicClass", teacherService.findAllTeachersInAllCourses());
        return TEACHERS_FOR_ACADEMIC_CLASSES;
    }

    private boolean checkCourseAndTeacherForAcademicClass(AcademicClassDto academicClassDto, Model model) {
        boolean check = false;
        if (!check)
            if (academicClassDto.getAcademicCourseSet().isEmpty()) {
                model.addAttribute("blankClass", SELECT_FIELD);
                check = true;
            }
        if (academicClassDto.getTeachers() == null) {
            model.addAttribute("blank", SELECT_FIELD);
            check = true;
        }
        return check;
    }
}

