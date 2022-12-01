package com.epam.edumanagementsystem.util.imageUtil.impl;

import com.epam.edumanagementsystem.teacher.rest.api.TeacherController;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String upload_path;

    private final String upload = System.getProperty("user.home") + "/img/";

    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);

    @Override
    public String saveImage(MultipartFile file) {
        String picUrl="";

        if (!file.isEmpty()) {
            File fileToCreate = new File(upload_path);
            if (!fileToCreate.exists()) {
                fileToCreate.mkdir();
            }
            picUrl = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                file.transferTo(new File(upload_path + File.separator + picUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picUrl;
    }

    @Override
    public void deleteImage(String fileName) {
        if (fileName != null) {
            try {
                Path path = Paths.get(upload_path + fileName);
                Files.delete(path);

                logger.info("You successfully deleted file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("Error! File name is null!");
    }

}
