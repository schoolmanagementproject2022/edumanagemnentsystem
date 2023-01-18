package com.epam.edumanagementsystem.util.imageUtil.rest.service;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {
   @Ignore
    @Test
    void imageIsValid() {
        File file = new File("C:\\edumanagemnentsystem\\src\\main\\resources\\static\\img\\avatar.png");
        FileInputStream input = null;
        try {
            MultipartFile multipartFile = new MockMultipartFile("picture",
                    file.getName(), "png", IOUtils.toByteArray(input));
            input = new FileInputStream(file);
            assertFalse(multipartFile.getBytes().length > 2097152);
            assertEquals(multipartFile.getContentType(), "png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Ignore
    @Test
    void imageSizeValidation() {
        File file = new File("C:\\edumanagemnentsystem\\src\\test\\java\\com\\epam\\edumanagementsystem\\util\\img\\Meteosat7-full-scan.jpg");
        try {
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("picture",
                    file.getName(), "jpg", IOUtils.toByteArray(input));

            assertTrue(multipartFile.getBytes().length > 2097152);
            assertEquals(multipartFile.getContentType(), "jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Ignore
    @Test
    void imageFormatValidation() throws IOException {
        File file = new File("C:\\edumanagemnentsystem\\src\\test\\java\\com\\epam\\edumanagementsystem\\util\\img\\test.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(),
                "txt", IOUtils.toByteArray(input));
        assertFalse(multipartFile.getBytes().length > 2097152);
        assertNotEquals(multipartFile.getContentType(), "jpg");
        assertNotEquals(multipartFile.getContentType(), "png");
        assertNotEquals(multipartFile.getContentType(), "jpeg");
    }
}