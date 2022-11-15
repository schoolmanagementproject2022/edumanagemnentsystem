package com.epam.edumanagementsystem.util.imageUtil.impl;

import com.epam.edumanagementsystem.teacher.rest.api.TeacherController;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {


    private String upload = System.getProperty("user.dir")+"/src/main/resources/static/img/";

    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);

    @Override
    public String saveImage(MultipartFile file) {
        String picUrl="";

        if (!file.isEmpty()) {
            picUrl = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                file.transferTo(new File(upload + File.separator + picUrl));
            } catch (IOException e) {
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
                e.printStackTrace();
            }
        }
        logger.info("Error! File name is null!");
    }

}
