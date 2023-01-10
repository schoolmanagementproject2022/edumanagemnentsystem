package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseService academicCourseService, StudentService studentService, TeacherService teacherService) {
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
        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        model.addAttribute("academicClasses", academicClassDtoList);
        academicClassService.checkClassDuplication(academicClassDto, result, model);
        if (result.hasErrors()) {
            return ACADEMIC_CLASSES_SECTION;
        } else {
            academicClassService.save(academicClassDto);
            return ACADEMIC_CLASSES_REDIRECT;
        }
    }

    @GetMapping("/{name}/courses")
    @Operation(summary = "Shows academic courses in academic class section")
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model) {
        List<Set<Teacher>> coursesForSelection;
        List<AcademicCourse> academicCoursesInClass = academicCourseService.findAllAcademicCoursesInClassByName(name);
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("academicCourseSet", academicCoursesInClass);
        model.addAttribute("allTeacherByAcademicCourse", teacherService.findAllTeachersInAllCourses());
        model.addAttribute("existingClass", new AcademicClass());

            if (academicCoursesInClass.isEmpty()) {
                coursesForSelection = allCourses.stream()
                        .map(AcademicCourse::getTeachers)
                        .filter(Set::isEmpty)
                        .collect(Collectors.toList());
                model.addAttribute("coursesForSelect", coursesForSelection);
                return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
            } else if (academicCoursesInClass.size() == allCourses.size()) {
                return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
            } else {
                coursesForSelection = allCourses.stream()
                        .map(AcademicCourse::getTeachers)
                        .filter(teachers -> !teachers.isEmpty())
                        .collect(Collectors.toList());
            }
            model.addAttribute("coursesForSelect", coursesForSelection);
            return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
    }

    @PostMapping("{name}/courses")
    @Operation(summary = "Adds new academic courses and teachers for selected academic class")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingClass") AcademicClassDto academicClassDto,
                                                 @PathVariable("name") String name, Model model) {
        List<AcademicCourse> coursesForSelection = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicCourseService.findAllAcademicCoursesInClassByName(name);
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("allTeacherByAcademicCourse", teacherService.findAllTeachersInAllCourses());
        for (AcademicCourse course : allCourses) {
            if (!academicCoursesInClass.contains(course)) {
                if (!course.getTeachers().isEmpty()) coursesForSelection.add(course);
            }
        }
        model.addAttribute("coursesForSelect", coursesForSelection);
        model.addAttribute("academicCourseSet", academicCoursesInClass);

        if (academicClassDto.getAcademicCourse().isEmpty() && academicClassDto.getTeachers() == null) {
            model.addAttribute("blank", SELECT_FIELD);
            model.addAttribute("blankClass", SELECT_FIELD);
            return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
        } else if (academicClassDto.getAcademicCourse().isEmpty()) {
            model.addAttribute("blankClass", SELECT_FIELD);
            return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
        } else if (academicClassDto.getTeachers() == null) {
            model.addAttribute("blank", SELECT_FIELD);
            return ACADEMIC_COURSE_FOR_ACADEMIC_CLASS;
        } else {
            AcademicClassDto foundClass = AcademicClassMapper.toDto(academicClassService.findByClassNumber(name));
            foundClass.getAcademicCourse().addAll(academicClassDto.getAcademicCourse());
            foundClass.getTeachers().addAll(academicClassDto.getTeachers());
            academicClassService.update(foundClass);
            return ACADEMIC_CLASSES_REDIRECT + name + ACADEMIC_COURSES_URL;
        }
    }

    @GetMapping("/{name}/classroom")
    @Operation(summary = "Shows teachers who can be selected as a classroom teacher for selected academic class")
    public String classroomTeacherForAcademicClass(@PathVariable("name") String name, Model model) {
        AcademicClass academicClassByName = academicClassService.findByClassNumber(name);
        Set<Teacher> allTeachersInClassName = academicClassByName.getTeachers();
        List<AcademicClassDto> allClasses = academicClassService.findAll();

        for (AcademicClassDto allClass : allClasses) {
            if (allClass.getClassroomTeacher() != null) {
                allTeachersInClassName.removeIf(teacher -> teacher == allClass.getClassroomTeacher());
            }
        }

        model.addAttribute("existingClassroomTeacher", new AcademicClass());
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
        AcademicClassDto academicClassFindByName = AcademicClassMapper.toDto(academicClassService.findByClassNumber(name));
        model.addAttribute("teachers", academicClassFindByName.getTeachers());
        model.addAttribute("existingClass", new AcademicClass());
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
        academicClassFindByName.setClassroomTeacher(academicClassDto.getClassroomTeacher());
        academicClassService.update(academicClassFindByName);
        return ACADEMIC_CLASSES_REDIRECT + name + CLASSROOM_URL;
    }

    @GetMapping("/{name}/students")
    @Operation(summary = "Gets the list of the student for the academic class")
    public String showAcademicClassStudents(@PathVariable("name") String name, Model model) {
        Long id = academicClassService.findByClassNumber(name).getId();
        model.addAttribute("studentsInAcademicClass", studentService.findByAcademicClassId(id));
        model.addAttribute("students", studentService.studentsWithoutConnectionWithClass());
        return ACADEMIC_CLASS_SECTION_FOR_STUDENTS;
    }

    @PostMapping("{name}/students")
    public String addNewTeacher(@ModelAttribute("existingAcademicClass") AcademicClassDto academicClassDto,
                                @PathVariable("name") String name, Model model) {
        Set<Student> students = academicClassDto.getStudents();
        AcademicClass academicClassByName = academicClassService.findByClassNumber(name);
        if (academicClassDto.getStudents() == null) {
            model.addAttribute("blank", "There is no new selection.");
            model.addAttribute("studentsInAcademicClass", studentService
                    .findByAcademicClassId(academicClassService.findByClassNumber(name).getId()));
            model.addAttribute("students", studentService.studentsWithoutConnectionWithClass());
            return ACADEMIC_CLASS_SECTION_FOR_STUDENTS;
        }
        for (Student student : students) {
            student.setAcademicClass(academicClassByName);
            studentService.updateStudentsClass(student);
        }
        return ACADEMIC_CLASSES_REDIRECT + name + STUDENTS_URL;
    }

    @GetMapping("/{name}/teachers")
    @Operation(summary = "Gets the list of the teachers for the academic class")
    public String teachersForAcademicClass(Model model, @PathVariable("name") String name) {
        model.addAttribute("teachers", academicClassService.findByClassNumber(name).getTeachers());
        model.addAttribute("allTeacherByAcademicClass", teacherService.findAllTeachersInAllCourses());
        return TEACHERS_FOR_ACADEMIC_CLASSES;
    }

}
