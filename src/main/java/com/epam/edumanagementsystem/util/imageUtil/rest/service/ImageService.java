package com.epam.edumanagementsystem.util.imageUtil.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String UPLOADED_FOLDER = "C:\\edumanagemnentsystem\\src\\main\\resources\\static\\img\\";

    String saveImage(MultipartFile file, TeacherDto teacherDto);

}
