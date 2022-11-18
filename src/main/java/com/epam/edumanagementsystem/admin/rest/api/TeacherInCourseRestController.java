package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/teacherByCourseId")
@Tag(name = "Teachers in course")
public class TeacherInCourseRestController {

    private final AcademicCourseService academicCourseService;

    public TeacherInCourseRestController(AcademicCourseService academicCourseService) {
        this.academicCourseService = academicCourseService;
    }

    @GetMapping("/teacherByCourseId")
    @GetMapping()
    @Operation(summary = "Gets the list of the teachers for multi select after the selection of the course")
    public @ResponseBody Set<Teacher> findAllTeacherInCourseById(@RequestParam(value = "teachers", required = true) Long id) {
        return academicCourseService.findByID(id).getTeacher();
    }

}