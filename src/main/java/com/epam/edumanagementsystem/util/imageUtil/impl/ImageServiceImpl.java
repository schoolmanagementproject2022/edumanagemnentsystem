package com.epam.edumanagementsystem.util.imageUtil.impl;

import com.epam.edumanagementsystem.teacher.rest.api.TeacherController;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);

    @Override
    public String saveImage(MultipartFile file) {
        String contentType = file.getContentType();
        String[] split;
        if (contentType != null) {
            split = contentType.split("/");
        } else {
            throw new NullPointerException("Name is null");
        }
        String fileRealName = System.currentTimeMillis() + "." + split[1];

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDir + fileRealName);
                Files.write(path, bytes);

                logger.info("You successfully uploaded file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fileRealName;
    }

}
