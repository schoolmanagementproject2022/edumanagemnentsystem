package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/teacherByCourseId")
public class TeacherInCourse {
    private final TeacherService teacherService;
    private  final AcademicCourseService academicCourseService;
    private  final AcademicClassService academicClassService;

    public TeacherInCourse(TeacherService teacherService, AcademicCourseService academicCourseService, AcademicClassService academicClassService) {
        this.teacherService = teacherService;
        this.academicCourseService = academicCourseService;
        this.academicClassService = academicClassService;
    }
    @GetMapping()
    public @ResponseBody
    Set<Teacher> findAllAgencies(@RequestParam(value = "teachers", required = true)String name) {
        return academicCourseService.findAcademicCourseByAcademicCourseName(name).getTeacher();
    }
}
