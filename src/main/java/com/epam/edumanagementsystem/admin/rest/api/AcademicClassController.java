package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/classes")
public class AcademicClassController {

    private final AcademicClassService academicClassService;
    private final AcademicCourseRepository academicCourseRepository;
    private final TeacherService teacherService;

    @Autowired
    public AcademicClassController(AcademicClassService academicClassService, AcademicCourseRepository academicCourseRepository, TeacherService teacherService) {
        this.academicClassService = academicClassService;
        this.academicCourseRepository = academicCourseRepository;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String openStudentSection(Model model) {
        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        model.addAttribute("academicClass", new AcademicClassDto());
        model.addAttribute("academicClasses", academicClassDtoList);

        return "academicClassSection";
    }

    @PostMapping
    public String create(@ModelAttribute("academicClass") @Valid AcademicClass academicClass,
                         BindingResult result, Model model) {
        List<AcademicClassDto> academicClassDtoList = academicClassService.findAll();
        List<AcademicClass> academicClassList = AcademicClassMapper.academicClassessList(academicClassDtoList);
        model.addAttribute("academicClasses", academicClassDtoList);

        for (AcademicClass aClass : academicClassList) {
            if (academicClass.getClassNumber().equals(aClass.getClassNumber())) {
                model.addAttribute("duplicated", "Class already exists");
                return "academicClassSection";
            }
        }
        if (result.hasErrors()) {
            return "academicClassSection";
        } else {
            academicClassService.create(academicClass);
            return "redirect:/classes";
        }
    }
    @GetMapping("/{name}/courses")
    public String openAcademicClassForAcademicCourse(@PathVariable("name") String name, Model model){
        List<AcademicCourse> result = new ArrayList<>();
        List<Teacher> resultTeacher=new ArrayList<>();
        List<Teacher> allTeachersInAcademicClass=academicClassService.findAllTeachers(name);
        model.addAttribute("teachers",allTeachersInAcademicClass);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());
        List<AcademicCourse> allAcademicCoursesInAcademicClass = academicClassService.findAllAcademicCourses(name);
        List<AcademicCourse> allAcademicCourses=academicCourseRepository.findAll();
        model.addAttribute("courses", allAcademicCoursesInAcademicClass);
        model.addAttribute("existingAcademicClass", academicClassService.findByName(name));
        if (allAcademicCoursesInAcademicClass.size() == 0) {
            result.addAll(allAcademicCourses);
            model.addAttribute("AcademicCoursesToSelect", result);
            return "academicCourseForAcademicClass";
        } else if (allAcademicCoursesInAcademicClass.size() == allAcademicCourses.size()) {
            return "academicCourseForAcademicClass";
        }else {
            for (AcademicCourse academicCourse : allAcademicCourses) {
                if (!allAcademicCoursesInAcademicClass.contains(academicCourse)) {
                    result.add(academicCourse);
                }
            }
        }
        if (allTeachersInAcademicClass.size() == 0) {
            resultTeacher.addAll(allTeachers);
            model.addAttribute("teachersToSelect", resultTeacher);
            return "academicCourseForAcademicClass";
        } else if (allTeachersInAcademicClass.size() == allTeachers.size()) {
            return "academicCourseForAcademicClass";
        }else {
            for (Teacher teacher : allTeachers) {
                if (!allTeachersInAcademicClass.contains(teacher)) {
                    resultTeacher.add(teacher);
                }
            }
        }
        model.addAttribute("teachersToSelect", resultTeacher);
        model.addAttribute("academicCoursesToSelect", result);
        return "academicCourseForAcademicClass";
    }
    @PostMapping("{name}/courses")
    public String addNewAcademicCourseAndTeacher(@ModelAttribute("existingAcademicClass") AcademicClass academicClass,
                                                 @PathVariable("name") String name, Model model){
        List<AcademicCourse> result = new ArrayList<>();
        List<Teacher> resultTeacher=new ArrayList<>();
        List<AcademicCourse> allAcademicCoursesInAcademicClass = academicClassService.findAllAcademicCourses(name);
        List<Teacher> allTeachersInAcademicClass=academicClassService.findAllTeachers(name);
        model.addAttribute("courses", allAcademicCoursesInAcademicClass);
        model.addAttribute("teachers",allTeachersInAcademicClass);
        List<Teacher> allTeachers = TeacherMapper.toListOfTeachers(teacherService.findAll());
        List<AcademicCourse> allAcademicCourses=academicCourseRepository.findAll();
        if (academicClass.getAcademicCourse().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allAcademicCoursesInAcademicClass.size() == 0) {
                result.addAll(allAcademicCourses);
                model.addAttribute("courseToSelect", result);
                return "academicCourseForAcademicClass";
            } else if (allAcademicCoursesInAcademicClass.size() == allAcademicCourses.size()) {
                return "academicCourseForAcademicClass";
            }else {
                for (AcademicCourse academicCourse : allAcademicCourses) {
                    if (!allAcademicCoursesInAcademicClass.contains(academicCourse)) {
                        result.add(academicCourse);
                    }
                }
            }
            model.addAttribute("courseToSelect", result);
            return "academicCourseForAcademicClass";
        }
        if (academicClass.getTeacher().size() == 0) {
            model.addAttribute("blank", "There is no new selection.");
            if (allTeachersInAcademicClass.size() == 0) {
                resultTeacher.addAll(allTeachers);
                model.addAttribute("teachersToSelect", resultTeacher);
                return "academicCourseForAcademicClass";
            } else if (allTeachersInAcademicClass.size() == allTeachers.size()) {
                return "academicCourseForAcademicClass";
            }else {
                for (Teacher teacher : allTeachers) {
                    if (!allTeachersInAcademicClass.contains(teacher)) {
                        resultTeacher.add(teacher);
                    }
                }
            }
            model.addAttribute("teachersToSelect", resultTeacher);
            return "academicCourseForAcademicClass";

        }
        academicClassService.update(academicClass);
        return "redirect:/classes/" + name + "/courses";

    }
}
