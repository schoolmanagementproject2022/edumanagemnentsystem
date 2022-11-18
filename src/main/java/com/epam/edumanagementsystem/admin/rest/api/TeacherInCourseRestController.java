package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TeacherInCourseRestController {

    private final AcademicCourseService academicCourseService;

    public TeacherInCourseRestController(AcademicCourseService academicCourseService) {
        this.academicCourseService = academicCourseService;
    }

    @GetMapping("/teacherByCourseId")
    public @ResponseBody Set<Teacher> findAllTeacherInCourseById(@RequestParam(value = "teachers", required = true) Long id) {
        return academicCourseService.findByID(id).getTeacher();
    }

}