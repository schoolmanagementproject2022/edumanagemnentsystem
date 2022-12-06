package com.epam.edumanagementsystem.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataValidationTest {

    private List<String> invalidPasswords;
    private List<String> validPasswords;
    private List<String> blankPasswords;
    private List<String> invalidEmails;
    private List<String> validEmails;
    private List<String> blankEmails;
    private String PATTERN_MAIL;
    private String PASSWORD_PATTERN;

    @BeforeEach
    void setUp() {
        validPasswords = List.of("1pA#6mG%5qE@", "2wT&1rU)7uY@", "4mZ)5rX(5gW(", "0dJ(7eD@2qE)",
                "6uP&6jV$2qP%", "8sN*8pT&7nH(");
        invalidPasswords = List.of("a", "Aa2#", "123456789", "aSdFgHkjKl", "1H23h4H5", "H+G&f=d*TJ");
        blankPasswords = List.of("", " ");
        PASSWORD_PATTERN
                = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&])[A-Za-z\\\\d()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&]{9,50}";
        validEmails = List.of("user@mail.ru", "user@ml.com", "u*s*er@mail.ru", "user@ma-il.ru", "user@mail.com", "USER@mail.ru",
                "user@MAIL.ru", "us@ma.ru", "37@mail.ru");
        invalidEmails = List.of("user.mail.ru", "user mailru", "$user@mail.com", "user*@mail.am", "us$er@mailru", "us$er@ru",
                "us$er@.ru", "us$*er@mail.com", "user@mail.r", "u@mail.ru", "user$mail.com", "user@*mail.ru", "user@ma&il.am", "user@mail.RU", "    @mail.ru");
        blankEmails = List.of("", " ");
        PATTERN_MAIL
                = "^([^! #$%&*+\\-/=?^_`{|\\.](?!.*[!#$%&*+-/=?^_`{|]{2})[a-zA-Z0-9!#$%&*+-/=?^_`{|]{0,50}[^! #$%&*+\\-/=?^_`{|\\.@]+)@([a-zA-Z0-9\\-]{1,50})\\.([a-z]{2,50})$";

    }

    @Test
    void passwordIsValid() {
        for (String validPassword : validPasswords) {
            assertTrue(validPassword.matches(PASSWORD_PATTERN));
        }
    }

    @Test
    void passwordIsNotBlank() {
        for (String validPassword : validPasswords) {
            assertFalse(validPassword.isBlank());
        }
    }

    @Test
    void passwordIsInvalid() {
        for (String invalidPassword : invalidPasswords) {
            assertFalse(invalidPassword.matches(PASSWORD_PATTERN));
        }
    }

    @Test
    void passwordIsBlank() {
        for (String blankPassword : blankPasswords) {
            assertTrue(blankPassword.isBlank());
        }
    }

    @Test
    void emailsIsValid() {
        for (String validEmail : validEmails) {
            assertFalse(validEmail.isBlank());
            assertTrue(validEmail.matches(PATTERN_MAIL));
        }
    }

    @Test
    void emailsIsInvalid() {
        for (String invalidEmail : invalidEmails) {
            assertFalse(invalidEmail.isBlank());
            assertFalse(invalidEmail.matches(PATTERN_MAIL));
        }
    }

    @Test
    void emailIsBlank() {
        for (String blankEmail : blankEmails) {
            assertTrue(blankEmail.isBlank());
        }
    }

    @Test
    void imageIsValid() throws IOException {
        File file = new File("C:\\edumanagemnentsystem\\src\\main\\resources\\static\\img\\avatar.png");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("picture",
                file.getName(), "png", IOUtils.toByteArray(input));
        assertFalse(multipartFile.getBytes().length > 2097152);
        assertEquals(multipartFile.getContentType(), "png");
    }

    @Test
    void imageSizeValidation() throws IOException {
        File file = new File("C:\\edumanagemnentsystem\\src\\test\\java\\com\\epam\\edumanagementsystem\\util\\img\\Meteosat7-full-scan.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("picture",
                file.getName(), "jpg", IOUtils.toByteArray(input));
        assertTrue(multipartFile.getBytes().length > 2097152);
        assertEquals(multipartFile.getContentType(), "jpg");
    }

    @Test
    void imageFormatValidation() throws IOException {
        File file = new File("C:\\edumanagemnentsystem\\src\\test\\java\\com\\epam\\edumanagementsystem\\util\\img\\test.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "txt", IOUtils.toByteArray(input));
        assertFalse(multipartFile.getBytes().length > 2097152);
        assertNotEquals(multipartFile.getContentType(), "jpg");
        assertNotEquals(multipartFile.getContentType(), "png");
        assertNotEquals(multipartFile.getContentType(), "jpeg");
    }

}