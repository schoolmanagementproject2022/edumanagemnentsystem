package com.epam.edumanagementsystem.util.imageUtil.rest.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveImage(MultipartFile file);

}
