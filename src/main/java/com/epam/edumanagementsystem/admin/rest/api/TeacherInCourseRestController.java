package com.epam.edumanagementsystem.admin.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@RestController
public class TeacherInCourseRestController {
    private final AcademicCourseService academicCourseService;
    @Value("${upload.dir}")
    private String uploadDir;

    public TeacherInCourseRestController(AcademicCourseService academicCourseService) {
        this.academicCourseService = academicCourseService;
    }

    @GetMapping("/teacherByCourseId")
    public @ResponseBody Set<Teacher> findAllTeacherInCourseById(@RequestParam(value = "teachers", required = true) Long id) {
        return academicCourseService.findByID(id).getTeacher();
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + picUrl);
        return IOUtils.toByteArray(in);
    }
}