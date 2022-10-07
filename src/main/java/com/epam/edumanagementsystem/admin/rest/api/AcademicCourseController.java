package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
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
import java.util.ArrayList;
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
        if (academicCourse.getName().contains(" ")) {
            String replace = academicCourse.getName().replace(" ", "");
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
        List<AcademicClassDto> academicClassSet = new ArrayList<>();
        AcademicCourse findAcademicCourseByName = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        Set<Teacher> allTeachersByAcademicCourseName = findAcademicCourseByName.getTeacher();
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.academicClassDtoSet(findAcademicCourseByName.getAcademicClass());
        model.addAttribute("teachersToSelect", allTeachersByAcademicCourseName);
        model.addAttribute("existingCourse", findAcademicCourseByName);
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
        } else {
            for (AcademicClassDto academicClass : allAcademicClasses) {
                if (!academicClassesInCourse.contains(academicClass)){
                    academicClassSet.add(academicClass);
                }
            }
        }
        model.addAttribute("academicClasses", academicClassSet);
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
    public String addClasses(@ModelAttribute("newClass")@Valid AcademicClass academicClass, BindingResult result,
                             @PathVariable("name") String name, Model model) {
        Set<AcademicCourse> academicCourseSet = new HashSet<>();
        List<AcademicClassDto> academicClassSet = new ArrayList<>();
        AcademicCourse findAcademicCourseByName = academicCourseService.findAcademicCourseByAcademicCourseName(name);
        List<AcademicClassDto> allAcademicClasses = academicClassService.findAll();
        Set<Teacher> teachersInAcademicCourse = findAcademicCourseByName.getTeacher();
        Set<AcademicClassDto> academicClassesInCourse = AcademicClassMapper.academicClassDtoSet(findAcademicCourseByName.getAcademicClass());
        model.addAttribute("academicClasses", allAcademicClasses);
        model.addAttribute("teachersToSelect", teachersInAcademicCourse);
        model.addAttribute("newClass", new AcademicClass());

        if (academicClassesInCourse.size() == 0) {
            academicClassSet.addAll(allAcademicClasses);
            model.addAttribute("academicClasses", academicClassSet);
        } else if (academicClassesInCourse.size() == allAcademicClasses.size()) {
            model.addAttribute("academicClasses", academicClassSet);
        } else {
            for (AcademicClassDto select : allAcademicClasses) {
                if (!academicClassesInCourse.contains(select)){
                    academicClassSet.add(select);
                }
            }
        }
        model.addAttribute("academicClasses", academicClassSet);
        if (result.hasErrors() || academicClass.getTeacher().size()==0){
            if (result.hasFieldErrors("classNumber")){
                model.addAttribute("blankClass", "Please, select the required fields");
            }
            if (academicClass.getTeacher().size()==0){
                model.addAttribute("blank", "Please, select the required fields");
            }
            return "academicCourseSectionForClasses";
        }

        AcademicCourse academicCourseByAcademicCourseName = academicCourseService.findAcademicCourseByAcademicCourseName(name);

        AcademicClass academicClassFindByName = academicClassService.findByName(academicClass.getClassNumber());

        academicCourseSet.add(academicCourseByAcademicCourseName);

        academicClassFindByName.getTeacher().addAll(academicClass.getTeacher());
        academicClassFindByName.getAcademicCourseSet().addAll(academicCourseSet);

        academicClassService.update(academicClassFindByName);
        return "redirect:/courses/" + name + "/classes";
    }
}