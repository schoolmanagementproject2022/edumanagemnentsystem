package com.epam.edumanagementsystem.exception;

import com.epam.edumanagementsystem.teacher.rest.api.TeacherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory
            .getLogger(TeacherController.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({IOException.class, ImageNotFoundException.class})
    public ModelAndView handleIOException(IOException ex) {
        logger.error("Take place IO exception, can't work with image file");
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
