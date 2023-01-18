package com.epam.edumanagementsystem.util.imageUtil.rest.service.impl;

import com.epam.edumanagementsystem.exception.ImageNotFoundException;
import com.epam.edumanagementsystem.teacher.rest.api.TeacherController;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
    private final String upload = System.getProperty("user.home") + "/img/";

    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);

    @Override
    public String saveImage(MultipartFile file) {
        String picUrl = "";

        if (!file.isEmpty()) {
            File fileToCreate = new File(upload);
            if (!fileToCreate.exists()) {
                fileToCreate.mkdir();
            }
            picUrl = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                file.transferTo(new File(upload + File.separator + picUrl));
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new ImageNotFoundException();
            }
        }
        return picUrl;
    }

    @Override
    public void deleteImage(String fileName) {
        if (fileName != null) {
            try {
                Path path = Paths.get(upload + fileName);
                Files.delete(path);

                logger.info("You successfully deleted file");
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw  new ImageNotFoundException();
            }
        } else {
            logger.error("Error! File name is null!");
            throw new ImageNotFoundException();
        }
    }

    public void checkMultipartFile(MultipartFile multipartFile, String status,
                                   Model model) {
        if (!multipartFile.isEmpty()) {
            validateImage(multipartFile, model);
        }
        if (status.equals("validationFail")) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
        }
    }


    public void validateImage(MultipartFile multipartFile, Model model) {
        try {
            if (multipartFile.getBytes().length > 2097152) {
                model.addAttribute("size", "File size exceeds maximum 2mb limit");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ImageNotFoundException();
        }
        if (!Objects.requireNonNull(multipartFile.getContentType()).equals("image/jpg")
                && !multipartFile.getContentType().equals("image/jpeg")
                && !multipartFile.getContentType().equals("image/png")) {
            model.addAttribute("formatValidationMessage", "Only PNG, JPEG and JPG files are allowed.");
        }
    }

}
