package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.putLessons;

@Controller
@RequestMapping("/classes")
public class AcademicClassController {

    private final AcademicClassService academicClassService;
    private final AcademicCourseService academicCourseService;
    private final StudentService studentService;
    private final TimetableService timetableService;
    private final CoursesForTimetableService coursesForTimetableService;

    @Autowired
    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseService academicCourseService, StudentService studentService, TimetableService timetableService, CoursesForTimetableService coursesForTimetableService) {
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.studentService = studentService;
        this.timetableService = timetableService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @GetMapping
    public String openAcademicCourse(Model model) {

        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        model.addAttribute("academicClasses", academicClassDtoList);
        model.addAttribute("academicClass", new AcademicClassDto());
        return "academicClassSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicClass") @Valid AcademicClass academicClass,
                         BindingResult result, Model model) {

        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        List<AcademicClass> academicClassList = AcademicClassMapper.academicClassessList(academicClassDtoList);
        model.addAttribute("academicClasses", academicClassDtoList);
        Character[] list = {'!', '#', '@', '#', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};
        for (Character character : list) {
            if (academicClass.getClassNumber().contains(character.toString())) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
                return "academicClassSection";
            }
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
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model) {
        List<AcademicCourse> result = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("academicCourseSet", academicCoursesInClass);
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingClass", new AcademicClass());
        model.addAttribute("allCourses", allCourses);
        if (academicCoursesInClass.size() == 0) {
            for (AcademicCourse course : allCourses) {
                if (course.getTeacher().size() > 0) {
                    result.add(course);
                }
            }
            model.addAttribute("coursesForSelect", result);
            return "academicCourseForAcademicClass";
        } else if (academicCoursesInClass.size() == allCourses.size()) {
            return "academicCourseForAcademicClass";
        } else {
            for (AcademicCourse course : allCourses) {
                if (!academicCoursesInClass.contains(course)) {
                    if (course.getTeacher().size() > 0) {
                        result.add(course);
                    }
                }
            }
        }
        model.addAttribute("coursesForSelect", result);
        return "academicCourseForAcademicClass";
    }

    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model) {
        List<AcademicCourse> result = new ArrayList<>();
        List<AcademicCourse> academicCoursesInClass = academicClassService.findAllAcademicCourses(name);
        Set<Teacher> allTeachersByAcademicCourse = academicCourseService.findAllTeacher();
        List<AcademicCourse> allCourses = AcademicCourseMapper.toListOfAcademicCourses(academicCourseService.findAll());
        model.addAttribute("allTeacherByAcademicCourse", allTeachersByAcademicCourse);
        model.addAttribute("existingClass", new AcademicClass());
        for (AcademicCourse course : allCourses) {
            if (!academicCoursesInClass.contains(course)) {
                if (course.getTeacher().size() > 0)
                    result.add(course);
            }
        }

        model.addAttribute("coursesForSelect", result);
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
            AcademicClass findedClass = academicClassService.findByName(name);
            findedClass.getAcademicCourseSet().addAll(academicClass.getAcademicCourseSet());
            findedClass.getTeacher().addAll(academicClass.getTeacher());
            academicClassService.update(findedClass);
            return "redirect:/classes/" + name + "/courses";
        }
    }

    @GetMapping("/{name}/classroom")
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
    public String addClassroomTeacherInAcademicClass(@ModelAttribute("existingClassroomTeacher") AcademicClass academicClass,
                                                     @PathVariable("name") String name,
                                                     Model model) {
        AcademicClass academicClassFindByName = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClassFindByName.getTeacher());
        model.addAttribute("existingClass", new AcademicClass());
        if (academicClass.getClassroomTeacher() == null) {
            model.addAttribute("blank", "Please, select the required fields");
            return "classroomTeacherSection";
        }
        for (AcademicClassDto academicClassDto : academicClassService.findAll()) {
            if (academicClass.getClassroomTeacher()
                    .equals(academicClassDto.getClassroomTeacher())) {
                model.addAttribute("duplicate", "This Teacher is already classroom teacher");
                return "classroomTeacherSection";
            }
        }

        academicClassFindByName.setClassroomTeacher(academicClass.getClassroomTeacher());
        academicClassService.update(academicClassFindByName);
        return "redirect:/classes/" + name + "/classroom";
    }

    @GetMapping("/{name}/students")
    public String showAcademicClassStudents(@PathVariable("name") String name, Model model) {
        Long id = academicClassService.findByName(name).getId();
        List<Student> allStudentsByAcademicClassId = studentService.findByAcademicClassId(id);
        model.addAttribute("studentsInAcademicClass", allStudentsByAcademicClassId);
        List<StudentDto> allStudents = studentService.findAll();
        List<StudentDto> studentsForAcademicCourse = new ArrayList<>();
        for (StudentDto student : allStudents) {
            if (null == student.getAcademicClass()) {
                studentsForAcademicCourse.add(student);
            }
        }
        model.addAttribute("students", studentsForAcademicCourse);
        return "academicClassSectionForStudents";
    }

    @PostMapping("{name}/students")
    public String addNewTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass,
                                @PathVariable("name") String name, Model model) {

        Set<Student> students = academicClass.getStudent();
        AcademicClass academicClassByName = academicClassService.findByName(name);
        if (academicClass.getStudent() == null) {
            model.addAttribute("blank", "There is no new selection.");
            Long id = academicClassService.findByName(name).getId();
            List<Student> allStudentsByAcademicClassId = studentService.findByAcademicClassId(id);
            model.addAttribute("studentsInAcademicClass", allStudentsByAcademicClassId);
            return "academicClassSectionForStudents";
        }
        if (null != students) {
            for (Student student : students) {
                student.setAcademicClass(academicClassByName);
                studentService.update(student);
            }
        }

        return "redirect:/classes/" + name + "/students";
    }

    @GetMapping("/{name}/teachers")
    public String teachersForAcademicClass(Model model, @PathVariable("name") String name) {
        AcademicClass academicClass = academicClassService.findByName(name);
        model.addAttribute("teachers", academicClass.getTeacher());
        Set<Teacher> allTeachersByAcademicClass = academicClassService.findAllTeacher();
        model.addAttribute("allTeacherByAcademicClass", allTeachersByAcademicClass);

        return "teachersForAcademicClass";
    }

    @GetMapping("/{name}/journal")
    public Object journal(Model model, @PathVariable("name") String name,
                          @RequestParam(name = "date", required = false) String date,
                          @RequestParam(name = "startDate", required = false) String startDate,
                          @RequestParam(name = "sel", required = false) String select) {
        if (date != null) {
           startDate = date;
           select =  null;
        }
        AcademicClass academicClassByName = academicClassService.findByName(name);

        if (null != timetableService.findTimetableByAcademicClassName(name)) {
            LocalDate timetableStartDate = timetableService.findTimetableByAcademicClassName(name).getStartDate();
            LocalDate timetableEndDate = timetableService.findTimetableByAcademicClassName(name).getEndDate();
            LocalDate journalStartDate = null;
            if (startDate != null && select == null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localdate = LocalDate.parse(startDate, formatter);
                if (!localdate.isAfter(timetableEndDate) && !localdate.isBefore(timetableStartDate)) {
                    journalStartDate = localdate;
                } else if (!localdate.isAfter(timetableStartDate)){
                    journalStartDate = timetableStartDate;
                } else if (!localdate.isBefore(timetableEndDate)){
                    journalStartDate = timetableEndDate;
                }

            } else if (startDate != null && select.equals("previous")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(startDate, formatter);
                if (localDate.minusDays(7).isAfter(timetableStartDate)) {
                    journalStartDate = localDate.minusDays(14);
                } else {
                    journalStartDate = localDate.minusDays(7);
                }
            } else if (startDate == null || select.equals("today")) {
                if (timetableStartDate.isAfter(LocalDate.now())) {
                    journalStartDate = timetableStartDate;
                } else {
                    journalStartDate = LocalDate.now();
                }
            } else if (startDate != null && select.equals("next")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localdate = LocalDate.parse(startDate, formatter);
                if (!localdate.isAfter(timetableEndDate)) {
                    journalStartDate = localdate;
                } else {
                    journalStartDate = localdate.minusDays(7);
                }
            }
            journalStartDate = academicClassService.recurs(journalStartDate);
            List<AcademicCourse> academicCoursesInClass = academicClassService.findAllAcademicCourses(name);
            model.addAttribute("allCoursesInAcademicClass", academicCoursesInClass);
            for (int i = 0; i < 7; i++) {
                int dayOfMonth = journalStartDate.getDayOfMonth();
                String dayOfMontString = Integer.valueOf(dayOfMonth).toString();
                DayOfWeek dayOfWeeks = journalStartDate.getDayOfWeek();
                String deyOfWeek = dayOfWeeks.toString();
                String day = StringUtils.capitalize(deyOfWeek.toLowerCase(Locale.ROOT));
                List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                        .getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(day, "Active", academicClassByName.getId());
                model.addAttribute(deyOfWeek.toLowerCase(Locale.ROOT), coursesByDayOfWeekAndStatusAndAcademicClassId);
                if (!coursesByDayOfWeekAndStatusAndAcademicClassId.isEmpty() &&
                        !journalStartDate.isBefore(timetableStartDate) && !journalStartDate.isAfter(timetableEndDate)) {
                    model.addAttribute(day, true);
                } else {
                    model.addAttribute(day, false);
                }
                model.addAttribute(deyOfWeek, dayOfMontString);
                journalStartDate = journalStartDate.plusDays(1);
            }
            String journalStartDateToString = journalStartDate.toString();
            model.addAttribute("month", journalStartDate.getMonth());
            model.addAttribute("year", journalStartDate.getYear());
            model.addAttribute("startDate", journalStartDateToString);
            return "JournalForAcademicClass";
        } else {
            model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(name));
            model.addAttribute("creationStatus", false);
            putLessons(model, academicClassByName.getId());
            return "timetableFromJournal";
        }
    }
}