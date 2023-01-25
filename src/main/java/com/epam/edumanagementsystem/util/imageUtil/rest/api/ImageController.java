package com.epam.edumanagementsystem.util.imageUtil.rest.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final String upload = System.getProperty("user.home") + "/img/";

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(upload + File.separator + imageName);
        byte[] userImageInBytes = in.readAllBytes();
        in.close();
        return userImageInBytes;
    }

}
