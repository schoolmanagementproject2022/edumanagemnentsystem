package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/teacherByCourseId")
public class TeacherInCourseRestController {
    private final AcademicCourseService academicCourseService;

    public TeacherInCourseRestController(AcademicCourseService academicCourseService) {
        this.academicCourseService = academicCourseService;
    }

    @GetMapping()
    public @ResponseBody Set<Teacher> findAllAgencies(@RequestParam(value = "teachers", required = true) Long id) {
        return academicCourseService.findByID(id).getTeacher();
    }
}
