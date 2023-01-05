package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.constants.GlobalConstants;
import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.InputFieldsValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
@Tag(name = "Academic course")
public class AcademicCourseController {

    private final AcademicCourseService academicCourseService;
    private final AcademicClassService academicClassService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public AcademicCourseController(AcademicCourseService academicCourseService, AcademicClassService academicClassService,
                                    SubjectService subjectService, TeacherService teacherService) {
        this.academicCourseService = academicCourseService;
        this.academicClassService = academicClassService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Gets the list of the academic courses and shows on admin`s dashboard")
    public String openAcademicCourseSection(Model model) {
        setAttributesInCoursesSection(model);
        return GlobalConstants.ACADEMIC_COURSE_SECTION;
    }

    @PostMapping
    @Operation(summary = "Saves the created academic course")
    public String save(@ModelAttribute("academicCourse") @Valid AcademicCourseDto academicCourse,
                       BindingResult result, Model model) {
        validateName(model, academicCourse, result);
        if (result.hasErrors() || model.containsAttribute("nameSize") || model.containsAttribute("invalidURL")) {
            return GlobalConstants.ACADEMIC_COURSE_SECTION;
        }

        String checkDuplication = checkDuplicationOfCourses(academicCourse, model);
        if (checkDuplication.equals(GlobalConstants.ACADEMIC_COURSE_SECTION)) {
            return checkDuplication;
        }
        String checkedNameAndSubject = validateNameAndSubject(result);
        if (checkedNameAndSubject.equals(GlobalConstants.ACADEMIC_COURSE_SECTION)) {
            return checkedNameAndSubject;
        }

        academicCourseService.save(academicCourse);
        return "redirect:/courses";
    }

    @GetMapping("/{name}/teachers")
    @Operation(summary = "Gets all teachers who teach the selected course and shows them")
    public String openAcademicCourseForTeacherCreation(@PathVariable("name") String courseName, Model model) {
        setTeachersInCourse(courseName, model);
        return "academicCourseSectionForTeachers";
    }

    @GetMapping("/{name}/classes")
    @Operation(summary = "Gets all classes who take the selected course and shows them")
    public String openAcademicCourseForAcademicClasses(@PathVariable("name") String courseName, Model model) {
        List<AcademicClassDto> academicClassSet = new ArrayList<>();
        AcademicCourse findAcademicCourseByName = academicCourseService.findByName(courseName);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.academicClassDtoSet(findAcademicCourseByName.getAcademicClass());
        model.addAttribute("teachersToSelect", findAcademicCourseByName.getTeachers());
        model.addAttribute("academicClasses", allAcademicClasses);
        model.addAttribute("existingClasses", academicClassesInCourse);
        model.addAttribute("newClass", new AcademicClass());
        if (academicClassesInCourse.size() == 0) {
            academicClassSet.addAll(allAcademicClasses);
            model.addAttribute("academicClasses", academicClassSet);
            return "academicCourseSectionForClasses";
        } else if (academicClassesInCourse.size() == allAcademicClasses.size()) {
            model.addAttribute("academicClasses", academicClassSet);
            return "academicCourseSectionForClasses";
        }
        academicClassSet = academicClassService.findAll().stream().filter(academicClass -> !academicClassesInCourse
                .contains(academicClass)).collect(Collectors.toList());
        model.addAttribute("academicClasses", academicClassSet);
        return "academicCourseSectionForClasses";
    }

    @PostMapping("{name}/teachers")
    @Operation(summary = "Adds a teacher to the group of teachers who run the selected course")
    public String saveTeacher(@ModelAttribute("existingAcademicCourse") AcademicCourseDto academicCourse,
                                @PathVariable("name") String courseName, Model model) {
        Set<Teacher> result;
        Set<Teacher> allTeacherSet = academicCourseService.findByName(courseName).getSubject().getTeacherSet();
        Set<Teacher> allTeachersInAcademicCourse = teacherService.findAllTeachersByCourseName(courseName);
        model.addAttribute("teachersInAcademicCourse", allTeachersInAcademicCourse);

        if (academicCourse.getTeachers() == null) {
            model.addAttribute("blank", "There is no new selection.");
            return "academicCourseSectionForTeachers";
        }
        if (allTeacherSet.size() == allTeachersInAcademicCourse.size()) {
            return "academicCourseSectionForTeachers";
        }
        result = allTeacherSet.stream().filter(teacher -> !allTeachersInAcademicCourse.contains(teacher)).collect(Collectors.toSet());
        model.addAttribute("teachers", result);
        academicCourseService.update(academicCourse);
        return "redirect:/courses/" + courseName + "/teachers";
    }

    @PostMapping("{name}/classes")
    @Operation(summary = "Adds classes to the selected course")
    public String addClasses(@ModelAttribute("newClass") @Valid AcademicClassDto academicClass, BindingResult result,
                             @PathVariable("name") String courseName, Model model) {
        setAttributes(model, courseName);
        if (result.hasErrors() || academicClass.getTeacherSet().size() == 0) {
            if (result.hasFieldErrors("classNumber")) {
                model.addAttribute("blankClass", "Please, select the required fields");
            }
            if (academicClass.getTeacherSet().size() == 0) {
                model.addAttribute("blank", "Please, select the required fields");
            }
            return "academicCourseSectionForClasses";
        }

        academicClassService.update(AcademicClassMapper.toDto(addClassesToCourse(academicClass, courseName)));
        return "redirect:/courses/" + courseName + "/classes";
    }

    private void setAttributes(Model model, String courseName) {
        List<AcademicClassDto> academicClassSet = new ArrayList<>();
        AcademicCourse findAcademicCourseByName = academicCourseService.findByName(courseName);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        Set<Teacher> teachersInAcademicCourse = findAcademicCourseByName.getTeachers();
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.academicClassDtoSet(findAcademicCourseByName.getAcademicClass());

        if (academicClassesInCourse.size() == 0) {
            academicClassSet.addAll(allAcademicClasses);
            model.addAttribute("academicClasses", academicClassSet);
        } else if (academicClassesInCourse.size() == allAcademicClasses.size()) {
            model.addAttribute("academicClasses", academicClassSet);
        } else {
            academicClassSet = allAcademicClasses.stream().filter(select -> !academicClassesInCourse.contains(select)).collect(Collectors.toList());
        }

        model.addAttribute("academicClasses", allAcademicClasses);
        model.addAttribute("teachersToSelect", teachersInAcademicCourse);
        model.addAttribute("existingClasses", academicClassesInCourse);
        model.addAttribute("existingCourse", findAcademicCourseByName);
        model.addAttribute("academicClasses", academicClassSet);
    }

    private AcademicClass addClassesToCourse(AcademicClassDto academicClassDto, String courseName) {
        Set<AcademicCourse> academicCourseSet = new HashSet<>();

        AcademicClass academicClassFindByName = academicClassService.findByClassNumber(academicClassDto.getClassNumber());

        academicCourseSet.add(academicCourseService.findByName(courseName));
        academicClassFindByName.getTeacher().addAll(academicClassDto.getTeacherSet());
        academicClassFindByName.getAcademicCourseSet().addAll(academicCourseSet);
        return academicClassFindByName;
    }

    private void setTeachersInCourse(String courseName, Model model) {
        AcademicCourse academicCourse = academicCourseService.findByName(courseName);
        Set<Teacher> result;
        Set<Teacher> teachersInSubject = academicCourse.getSubject().getTeacherSet();
        Set<Teacher> teachersInAcademicCourse = academicCourse.getTeachers();
        result = teachersInSubject.stream().filter(teacher -> !teachersInAcademicCourse.contains(teacher)).collect(Collectors.toSet());
        model.addAttribute("teachers", result);
        model.addAttribute("teachersInAcademicCourse", teachersInAcademicCourse);
    }

    private List<AcademicCourseDto> getAllCoursesAndSetAttributes(Model model) {
        List<AcademicCourseDto> courseDtoList = academicCourseService.findAll();
        model.addAttribute("academicCourses", courseDtoList);
        model.addAttribute("subjects", subjectService.findAll());
        return courseDtoList;
    }

    private void validateName(Model model, AcademicCourseDto academicCourseDto, BindingResult result) {
        if (!result.hasFieldErrors("name")) {
            if (InputFieldsValidation.validateInputFieldSize(academicCourseDto.getName())) {
                model.addAttribute("nameSize", "Symbols can't be more than 50");
            }
            if (InputFieldsValidation.checkingForIllegalCharacters(academicCourseDto.getName(), model)) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
            }
        }
    }

    private void setAttributesInCoursesSection(Model model) {
        model.addAttribute("academicCourses", academicCourseService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("academicCourse", new AcademicCourseDto());
    }

    private String checkDuplicationOfCourses(AcademicCourseDto academicCourseDto, Model model) {
        for (AcademicCourseDto courseDto : getAllCoursesAndSetAttributes(model)) {
            if (courseDto.getName().equalsIgnoreCase(academicCourseDto.getName())) {
                model.addAttribute("duplicated", "An Academic Course with the same name already exists");
                return GlobalConstants.ACADEMIC_COURSE_SECTION;
            }
        }
        return "Passed";
    }

    private String validateNameAndSubject(BindingResult result) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("name") && result.hasFieldErrors("subject")) {
                return GlobalConstants.ACADEMIC_COURSE_SECTION;
            }
            if (result.hasFieldErrors("name")) {
                return GlobalConstants.ACADEMIC_COURSE_SECTION;
            } else if (result.hasFieldErrors("subject")) {
                return GlobalConstants.ACADEMIC_COURSE_SECTION;
            }
            if (result.hasFieldErrors("subject")) {
                return GlobalConstants.ACADEMIC_COURSE_SECTION;
            }
        }
        return "Passed";
    }

}