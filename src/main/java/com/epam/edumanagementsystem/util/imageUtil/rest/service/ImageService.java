package com.epam.edumanagementsystem.util.imageUtil.rest.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveImage(MultipartFile file);

    void deleteImage(String name);

    void checkMultipartFile(MultipartFile multipartFile, String status, Model model);

    void validateImage(MultipartFile multipartFile, Model model);

}
