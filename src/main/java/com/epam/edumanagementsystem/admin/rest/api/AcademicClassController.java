package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.journal_agenda.rest.service.JournalService;
import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.InputFieldsValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/classes")
@Tag(name = "Academic class")
public class AcademicClassController {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final StudentService studentService;
    private final TimetableService timetableService;
    private final TeacherService teacherService;

    private final JournalService journalService;

    @Autowired
    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseService academicCourseService, StudentService studentService, TimetableService timetableService, TeacherService teacherService, JournalService journalService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.studentService = studentService;
        this.timetableService = timetableService;
        this.teacherService = teacherService;
        this.journalService = journalService;
    }

    @GetMapping
    @Operation(summary = "Shows academic class section on admin's dashboard with existing courses")
    public String openAcademicCourse(Model model) {

        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        model.addAttribute("academicClasses", academicClassDtoList);
        model.addAttribute("academicClass", new AcademicClassDto());
        return "academicClassSection";
    }

    @PostMapping
    @Operation(summary = "Saves the created academic class")
    public String create(@ModelAttribute("academicClass") @Valid AcademicClass academicClass, BindingResult result, Model model) {

        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        List<AcademicClass> academicClassList = AcademicClassMapper.academicClassessList(academicClassDtoList);
        model.addAttribute("academicClasses", academicClassDtoList);

        if (!result.hasFieldErrors("classNumber")) {
            if (InputFieldsValidation.validateInputFieldSize(academicClass.getClassNumber())) {
                model.addAttribute("nameSize", "Symbols can't be more than 50");
            }
            if (InputFieldsValidation.checkingForIllegalCharacters(academicClass.getClassNumber(), model)) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
            }
        }

        if (result.hasErrors() || model.containsAttribute("nameSize") || model.containsAttribute("invalidURL")) {
            return "academicClassSection";
        }

        for (AcademicClass existingListOfAcademicClass : academicClassList) {
            if (academicClass.getClassNumber().equals(existingListOfAcademicClass.getClassNumber())) {
                model.addAttribute("duplicated", "Class already exists");
                return "academicClassSection";
            }
        }
        if (result.hasErrors()) {
            return "academicClassSection";
        } else {
            String decoded = URLDecoder.decode(academicClass.getClassNumber(), StandardCharsets.UTF_8);
            academicClass.setClassNumber(decoded);
            academicClassService.create(academicClass);
            return "redirect:/classes";
        }
    }

    @GetMapping("/{name}/courses")
    @Operation(summary = "Shows academic courses in academic class section")
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model) {
        List<AcademicCourse> coursesForSelection = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicCourseService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("academicCourseSet", academicCoursesInClass);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingClass", new AcademicClass());
        if (academicCoursesInClass.size() == 0) {
            for (AcademicCourse course : allCourses) {
                if (course.getTeacher().size() > 0) {
                    coursesForSelection.add(course);
                }
            }
            model.addAttribute("coursesForSelect", coursesForSelection);
            return "academicCourseForAcademicClass";
        } else if (academicCoursesInClass.size() == allCourses.size()) {
            return "academicCourseForAcademicClass";
        } else {
            for (AcademicCourse course : allCourses) {
                if (!academicCoursesInClass.contains(course)) {
                    if (course.getTeacher().size() > 0) {
                        coursesForSelection.add(course);
                    }
                }
            }
        }
        model.addAttribute("coursesForSelect", coursesForSelection);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    @Operation(summary = "Adds new academic courses and teachers for selected academic class")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingClass") AcademicClass academicClass, @PathVariable("name") String name, Model model) {
        List<AcademicCourse> coursesForSelection = new ArrayList<>();
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> academicCoursesInClass = academicCourseService.findAllAcademicCourses(name);
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        for (AcademicCourse course : allCourses) {
            if (!academicCoursesInClass.contains(course)) {
                if (course.getTeacher().size() > 0) coursesForSelection.add(course);
            }
        }
        model.addAttribute("coursesForSelect", coursesForSelection);
        model.addAttribute("academicCourseSet", academicCoursesInClass);

        if (academicClass.getAcademicCourseSet().size() == 0 && academicClass.getTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            model.addAttribute("blankClass", "Please, select the required fields");
            return "academicCourseForAcademicClass";

        } else if (academicClass.getAcademicCourseSet().size() == 0) {
            model.addAttribute("blankClass", "Please, select the required fields");
            return "academicCourseForAcademicClass";
        } else if (academicClass.getTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            return "academicCourseForAcademicClass";
        } else {
            AcademicClass foundClass = academicClassService.findByName(name);
            foundClass.getAcademicCourseSet().addAll(academicClass.getAcademicCourseSet());
            foundClass.getTeacher().addAll(academicClass.getTeacher());
            academicClassService.update(foundClass);
            return "redirect:/classes/" + name + "/courses";
        }
    }

    @GetMapping("/{name}/classroom")
    @Operation(summary = "Shows teachers who can be selected as a classroom teacher for selected academic class")
    public String classroomTeacherForAcademicClass(@PathVariable("name") String name, Model model) {
        AcademicClass academicClassByName = academicClassService.findByName(name);
        Set<Teacher> allTeachersInClassName = academicClassByName.getTeacher();
        List<AcademicClassDto> allClasses = academicClassService.findAll();

        for (AcademicClassDto allClass : allClasses) {
            if (allClass.getClassroomTeacher() != null) {
                allTeachersInClassName.removeIf(teacher -> teacher == allClass.getClassroomTeacher());
            }
        }

        model.addAttribute("existingClassroomTeacher", new AcademicClass());
        model.addAttribute("teachers", allTeachersInClassName);
        if (academicClassByName.getClassroomTeacher() == null) {
            return "classroomTeacherSection";
        } else {
            model.addAttribute("classroomTeacher", academicClassByName.getClassroomTeacher());
            return "classroomTeacherSection";
        }
    }

    @PostMapping("{name}/classroom")
    @Operation(summary = "Adds a classroom teacher for selected academic class")
    public String addClassroomTeacherInAcademicClass(@ModelAttribute("existingClassroomTeacher") AcademicClass academicClass, @PathVariable("name") String name, Model model) {
        AcademicClass academicClassFindByName = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClassFindByName.getTeacher());
        model.addAttribute("existingClass", new AcademicClass());
        if (academicClass.getClassroomTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            return "classroomTeacherSection";
        }
        for (AcademicClassDto academicClassDto : academicClassService.findAll()) {
            if (academicClass.getClassroomTeacher().equals(academicClassDto.getClassroomTeacher())) {
                model.addAttribute("duplicate", "This Teacher is already classroom teacher");
                return "classroomTeacherSection";
            }
        }

        academicClassFindByName.setClassroomTeacher(academicClass.getClassroomTeacher());
        academicClassService.update(academicClassFindByName);
        return "redirect:/classes/" + name + "/classroom";
    }

    @GetMapping("/{name}/students")
    @Operation(summary = "Gets the list of the student for the academic class")
    public String showAcademicClassStudents(@PathVariable("name") String name, Model model) {
        Long id = academicClassService.findByName(name).getId();
        model.addAttribute("studentsInAcademicClass", studentService.findByAcademicClassId(id));
        model.addAttribute("students", studentService.studentsWithoutConnectionWithClass());
        return "academicClassSectionForStudents";
    }

    @PostMapping("{name}/students")
    public String addNewTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass, @PathVariable("name") String name, Model model) {
        Set<Student> students = academicClass.getStudent();
        AcademicClass academicClassByName = academicClassService.findByName(name);
        if (academicClass.getStudent() == null) {
            model.addAttribute("blank", "There is no new selection.");
            model.addAttribute("studentsInAcademicClass", studentService.findByAcademicClassId(academicClassService.findByName(name).getId()));
            model.addAttribute("students", studentService.studentsWithoutConnectionWithClass());
            return "academicClassSectionForStudents";
        }
        if (null != students) {
            for (Student student : students) {
                student.setAcademicClass(academicClassByName);
                studentService.updateStudentsClass(student);
            }
        }
        return "redirect:/classes/" + name + "/students";
    }

    @GetMapping("/{name}/teachers")
    @Operation(summary = "Gets the list of the teachers for the academic class")
    public String teachersForAcademicClass(Model model, @PathVariable("name") String name) {
        model.addAttribute("teachers", academicClassService.findByName(name).getTeacher());
        model.addAttribute("allTeacherByAcademicClass", teacherService.findAllTeachersInClasses());

        return "teachersForAcademicClass";
    }

    @GetMapping("/{name}/journal")
    public String journal(Model model, @PathVariable("name") String name, @RequestParam(name = "date", required = false) String date,
                          @RequestParam(name = "startDate", required = false) String startDate) {
        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            journalService.openJournal(date, startDate, name, model);
            return "journal";
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date, startDate, name, model);
            return "createTimetableMsgFromJournal";
        }
    }

    @GetMapping("/{name}/journal/{courseId}")
    public String journalWithCourseInfo(@PathVariable("name") String name, @PathVariable("courseId") Long courseId,
                                        @RequestParam(name = "date", required = false) String date,
                                        @RequestParam(name = "startDate", required = false) String startDate,
                                        @RequestParam(value = "allFieldsBlankMessage", required = false) String allFieldsBlankMessage,
                                        @RequestParam(value = "concreteDay", required = false) String concreteDay,
                                        Model model) {
        if (allFieldsBlankMessage != null && !allFieldsBlankMessage.isBlank()) {
            model.addAttribute("allFieldsBlankMessage", allFieldsBlankMessage);
            model.addAttribute("concreteDay", concreteDay);
        }

        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            model.addAttribute("course", academicCourseService.findByID(courseId));
            model.addAttribute("class", academicClassService.findByName(name));
            List<StudentDto> studentsInClass = studentService.findStudentsByClassName(name);
            model.addAttribute("allStudentsInAcademicClass", studentsInClass);
            if (studentsInClass.isEmpty()) {
                return "journalWithCourseInfo";
            }

            journalService.openJournal(date, startDate, name, model);
            return "journalWithCourseInfo";
        } else {
            journalService.doNotOpenJournal_timetableIsNotExist(date.split("/")[0], startDate, name, model);
            model.addAttribute("class", academicClassService.findByName(name));
            model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(name));
            model.addAttribute("creationStatus", false);
            return "createTimetableMsgFromJournal";
        }
    }
}
