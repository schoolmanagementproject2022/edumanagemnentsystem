package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
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
    private final AcademicClassService academicClassService;
    private final SubjectService subjectService;

    @Autowired
    public AcademicCourseController(AcademicCourseService academicCourseService, AcademicClassService academicClassService, SubjectService subjectService) {
        this.academicCourseService = academicCourseService;
        this.academicClassService = academicClassService;
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
        if(academicCourse.getName().contains(" ")){
            String replace=academicCourse.getName().replace(" ", "");
                academicCourse.setName(replace);
        }
        List<AcademicCourseDto> all = academicCourseService.findAll();
        model.addAttribute("academicCourses", all);
        List<Subject> allSubjects = subjectService.findAll();
        model.addAttribute("subjects", allSubjects);

        Character[] list = {'!', '#', '@', '#', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};
        for (Character character : list) {
            if (academicCourse.getName().contains(character.toString())) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
                return "academicCourseSection";
            }
        }

        for (AcademicCourseDto aCourse : all) {
            if (aCourse.getName().equalsIgnoreCase(academicCourse.getName())) {
                model.addAttribute("duplicated", "A Subject with the same name already exists");
                return "academicCourseSection";
            }
        }

        if (result.hasErrors()) {
            if (result.hasFieldErrors("name") && result.hasFieldErrors("subject")) {
                return "academicCourseSection";
            }
            if (result.hasFieldErrors("name")) {
                return "academicCourseSection";
            } else if (result.hasFieldErrors("subject")) {
                return "academicCourseSection";
            }
            if (result.hasFieldErrors("subject")) {
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

    @GetMapping("/{name}/classes")
    public String openAcademicCourseForAcademicClasses(@PathVariable("name") String name, Model model) {
        AcademicCourse academicCourse = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        List<AcademicClassDto> allAcademicClass = academicClassService.findAll();
        Set<Teacher> teacherSet = academicCourse.getTeacher();
        Set<AcademicClass> academicClasses = academicCourse.getAcademicClass();
        model.addAttribute("teachersToSelect", teacherSet);
        model.addAttribute("existingCourse", academicCourse);
        model.addAttribute("academicClasses", allAcademicClass);
        model.addAttribute("existingClasses", academicClasses);
        model.addAttribute("newClass", new AcademicClass());
        return "academicCourseSectionForClasses";
    }

    @PostMapping("{name}/teachers")
    public String addNewTeacher(@ModelAttribute("existingAcademicCourse") AcademicCourse academicCourse,
                                @PathVariable("name") String name, Model model) {

        Set<Teacher> result = new HashSet<>();
        Set<Teacher> allTeacherSet = academicCourseService.findAcademicCourseByAcademicCourseName(name)
                .getSubject().getTeacherSet();
        Set<Teacher> allTeachersInAcademicCourse = academicCourseService.findAllTeachersByAcademicCourseName(name);
        model.addAttribute("teachersInAcademicCourse", allTeachersInAcademicCourse);

        if (academicCourse.getTeacher().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeacherSet.size() == allTeachersInAcademicCourse.size()) {
                return "academicCourseSectionForTeachers";
            } else {
                for (Teacher teacher : allTeacherSet) {
                    if (!allTeachersInAcademicCourse.contains(teacher)) {
                        result.add(teacher);
                    }
                }
            }
            model.addAttribute("teachers", result);
            return "academicCourseSectionForTeachers";

        }
        academicCourseService.update(academicCourse);
        return "redirect:/courses/" + name + "/teachers";
    }

    @PostMapping("{name}/classes")
    public String addClasses(@ModelAttribute("newClass") @Valid AcademicClass academicClass,BindingResult bindingResult,
                             @PathVariable("name") String name, Model model) {

        AcademicCourse academicCourseByAcademicCourseName = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        Set<AcademicCourse> academicCourseSet = new HashSet<>();
        academicCourseSet.add(academicCourseByAcademicCourseName);
        AcademicClass byName = academicClassService.findByName(academicClass.getClassNumber());
        byName.setTeacher(academicClass.getTeacher());
        byName.setAcademicCourseSet(academicCourseSet);
        academicClassService.update(byName);
        return "redirect:/courses/" + name + "/classes";

//        AcademicCourse findAcademicCourseByName = academicCourseService.findAcademicCourseByAcademicCourseName(name);
//        Set<AcademicCourse> academicCourseSet = new HashSet<>();
//        academicCourseSet.add(findAcademicCourseByName);
//        Set<AcademicClass> academicClasses = academicCourse.getAcademicClass();
//        if (bindingResult.hasErrors()){
//            if (bindingResult.hasFieldErrors("academicClass") && bindingResult.hasFieldErrors("teacher")) {
//                return "academicCourseSectionForClasses";
//            }
//            if (bindingResult.hasFieldErrors("academicClass")) {
//                return "academicCourseSectionForClasses";
//            } else if (bindingResult.hasFieldErrors("teacher")) {
//                return "academicCourseSectionForClasses";
//            }
//        }
//        for (AcademicClass academicClass : academicClasses) {
//            if (academicCourse.getTeacher()!=null){
//                academicClass.setTeacher(academicCourse.getTeacher());
//                academicClass.setAcademicCourseSet(academicCourseSet);
//                academicClassService.update(academicClass);
//            }
//        }
//        model.addAttribute("teachers", findAcademicCourseByName.getTeacher());
//        return "redirect:/courses/" + name + "/classes";
    }
}