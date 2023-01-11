package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.constants.GlobalConstants;
import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
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
        model.addAttribute("academicCourse", new AcademicCourseDto());
        return GlobalConstants.ACADEMIC_COURSE_SECTION;
    }

    @PostMapping
    @Operation(summary = "Saves the created academic course")
    public String save(@ModelAttribute("academicCourse") @Valid AcademicCourseDto academicCourse,
                       BindingResult result, Model model) {

        academicCourseService.checkCourseDuplication(academicCourse, result, model);
        if (result.hasErrors()) {
            setAttributesInCoursesSection(model);
            return GlobalConstants.ACADEMIC_COURSE_SECTION;
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
        AcademicCourseDto academicCourseDto = academicCourseService.findByName(courseName);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        //streami mej ete null e gnum xndir e linum???
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.toAcademicClassDtoSet(academicCourseDto.getAcademicClassSet());
        model.addAttribute("teachersToSelect", academicCourseDto.getTeachers());
        model.addAttribute("academicClasses", allAcademicClasses);
        model.addAttribute("existingClasses", academicClassesInCourse);
        model.addAttribute("newClass", new AcademicClassDto());
        if (academicClassesInCourse.isEmpty()) {
            academicClassSet.addAll(allAcademicClasses);
            model.addAttribute("academicClasses", academicClassSet);
            return "academicCourseSectionForClasses";
        } else if (academicClassesInCourse.size() == allAcademicClasses.size()) {
            model.addAttribute("academicClasses", academicClassSet);
            return "academicCourseSectionForClasses";
        }
        academicClassSet = academicClassService.findAll().stream()
                .filter(academicClass -> !academicClassesInCourse
                .contains(academicClass)).collect(Collectors.toList());
        model.addAttribute("academicClasses", academicClassSet);
        return "academicCourseSectionForClasses";
    }

    @PostMapping("{name}/teachers")
    @Operation(summary = "Adds a teacher to the group of teachers who run the selected course")
    public String saveTeacher(@ModelAttribute("existingAcademicCourse") AcademicCourseDto academicCourse,
                                @PathVariable("name") String courseName, Model model) {
        Set<TeacherDto> teachers;
        Set<TeacherDto> allTeacherSet = TeacherMapper.mapToTeacherDtoSet(academicCourseService.findByName(courseName).getSubject().getTeacherSet());
        Set<TeacherDto> allTeachersInAcademicCourse = teacherService.findAllTeachersByCourseName(courseName);
        model.addAttribute("teachersInAcademicCourse", allTeachersInAcademicCourse);
        teachers = allTeacherSet.stream()
                .filter(teacher -> !allTeachersInAcademicCourse.contains(teacher))
                .collect(Collectors.toSet());
        model.addAttribute("teachers", teachers);

        if (academicCourse.getTeachers().isEmpty()) {
            model.addAttribute("blank", "There is no new selection.");
            return "academicCourseSectionForTeachers";
        }
        if (allTeacherSet.size() == allTeachersInAcademicCourse.size()) {
            return "academicCourseSectionForTeachers";
        }
        academicCourseService.update(academicCourse);
        return "redirect:/courses/" + courseName + "/teachers";
    }

    @PostMapping("{name}/classes")
    @Operation(summary = "Adds classes to the selected course")
    public String addClasses(@ModelAttribute("newClass") @Valid AcademicClassDto academicClassDto, BindingResult result,
                             @PathVariable("name") String courseName, Model model) {
        setAttributes(model, courseName);
        if (result.hasErrors() || academicClassDto.getTeachers().isEmpty()) {
            if (result.hasFieldErrors("classNumber")) {
                model.addAttribute("blankClass", "Please, select the required fields");
            }
            if (academicClassDto.getTeachers().isEmpty()) {
                model.addAttribute("blank", "Please, select the required fields");
            }
            return "academicCourseSectionForClasses";
        }

        academicClassService.update(addClassesToCourse(academicClassDto, courseName));
        return "redirect:/courses/" + courseName + "/classes";
    }

    private void setAttributes(Model model, String courseName) {
        List<AcademicClassDto> academicClassSet = new ArrayList<>();
        AcademicCourseDto findAcademicCourseByName = academicCourseService.findByName(courseName);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        Set<Teacher> teachersInAcademicCourse = findAcademicCourseByName.getTeachers();
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.toAcademicClassDtoSet(findAcademicCourseByName.getAcademicClassSet());

        if (academicClassesInCourse.isEmpty()) {
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

    private AcademicClassDto addClassesToCourse(AcademicClassDto academicClassDto, String courseName) {
        Set<AcademicCourseDto> academicCourseSet = new HashSet<>();

        AcademicClassDto academicClassFindByName = academicClassService.findByClassNumber(academicClassDto.getClassNumber());

        academicCourseSet.add(academicCourseService.findByName(courseName));
        academicClassFindByName.getTeachers().addAll(academicClassDto.getTeachers());
        academicClassFindByName.getAcademicCourse().addAll(AcademicCourseMapper.toSetOfAcademicCourse(academicCourseSet));
        return academicClassFindByName;
    }

    private void setTeachersInCourse(String courseName, Model model) {
        AcademicCourseDto academicCourse = academicCourseService.findByName(courseName);
        Set<TeacherDto> teacherSet;
        Set<TeacherDto> teachersInSubject = TeacherMapper.mapToTeacherDtoSet(academicCourse.getSubject().getTeacherSet());
        Set<TeacherDto> teachersInAcademicCourse = TeacherMapper.mapToTeacherDtoSet(academicCourse.getTeachers());
        teacherSet = teachersInSubject.stream().filter(teacher -> !teachersInAcademicCourse.contains(teacher)).collect(Collectors.toSet());
        model.addAttribute("teachers", teacherSet);
        model.addAttribute("existingAcademicCourse", academicCourse);
        model.addAttribute("teachersInAcademicCourse", teachersInAcademicCourse);
    }

    private List<AcademicCourseDto> getAllCoursesAndSetAttributes(Model model) {
        List<AcademicCourseDto> courseDtoList = academicCourseService.findAll();
        model.addAttribute("academicCourses", courseDtoList);
        model.addAttribute("subjects", subjectService.findAll());
        return courseDtoList;
    }


    private void setAttributesInCoursesSection(Model model) {
        model.addAttribute("academicCourses", academicCourseService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
    }

}