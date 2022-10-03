package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.rest.service.SubjectService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/courses")
public class AcademicCourseController {
    private final AcademicCourseService academicCourseService;
    private final SubjectService subjectService;

    @Autowired
    public AcademicCourseController(AcademicCourseService academicCourseService, SubjectService subjectService) {
        this.academicCourseService = academicCourseService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public String openAcademicCourseSection(ModelMap model) {
        List<AcademicCourseDto> academicCourseDtos = academicCourseService.findAll();
        List<Subject> all = subjectService.findAll();
        model.addAttribute("academicCourses", academicCourseDtos);
        model.addAttribute("subjects", all);
        model.addAttribute("academicCourse", new AcademicCourseDto());
        return "academicCourseSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicCourse") @Valid AcademicCourse academicCourse,
                         BindingResult result,
                         Model model) {
        List<AcademicCourseDto> all = academicCourseService.findAll();
        model.addAttribute("academicCourses", all);
        List<Subject> allSubjects = subjectService.findAll();
        model.addAttribute("subjects", allSubjects);

        for (AcademicCourseDto aCourse : all) {
            if (aCourse.getName().equalsIgnoreCase(academicCourse.getName())) {
                model.addAttribute("duplicated", "A Subject with the same name already exists");
                return "academicCourseSection";
            }
        }

        if (result.hasErrors()) {
            if (result.hasFieldErrors("name")) {
                return "academicCourseSection";
            }
        }
        academicCourseService.create(academicCourse);
        return "redirect:/courses";
    }

    @GetMapping("/{name}/teachers")
    public String openAcademicCourseForTeacherCreation(@PathVariable("name") String name, Model model) {
        AcademicCourse academicCourse = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        Set<Teacher> result = new HashSet<>();
        Set<Teacher> teachersInSubject = academicCourse.getSubject().getTeacherSet();
        Set<Teacher> teachersInAcademicCourse = academicCourse.getTeacher();
        for (Teacher teacher : teachersInSubject) {
            if (!teachersInAcademicCourse.contains(teacher)) {
                result.add(teacher);
            }
        }
        model.addAttribute("teachers", result);
        model.addAttribute("teachersInAcademicCourse", teachersInAcademicCourse);
        return "academicCourseSectionForTeachers";
    }

    @PostMapping("{name}/teachers")
    public String addNewTeacher(@ModelAttribute("existingAcademicCourse") AcademicCourse academicCourse,
                                @PathVariable("name") String name, Model model) {

        Set<Teacher> result = new HashSet<>();
        Set<Teacher> allTeacherSet = academicCourseService.findAcademicCourseByAcademicCourseName(name)
                .getSubject().getTeacherSet();
        Set<Teacher> allTeachersInAcademicCourse = academicCourseService.findAllTeachersByAcademicCourseName(name);
        model.addAttribute("teachers", allTeachersInAcademicCourse);


        if (allTeacherSet.size() == allTeachersInAcademicCourse.size()) {
            model.addAttribute("blank", "There is no new selection.");
            return "academicCourseSectionForTeachers";
        } else {
            for (Teacher teacher : allTeacherSet) {
                if (!allTeachersInAcademicCourse.contains(teacher)) {
                    result.add(teacher);
                }
            }
            model.addAttribute("teachersToSelect", result);
        }
        academicCourseService.update(academicCourse);
        return "redirect:/courses/" + name + "/teachers";
    }
}