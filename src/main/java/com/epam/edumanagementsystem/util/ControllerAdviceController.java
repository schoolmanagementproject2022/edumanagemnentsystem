package com.epam.edumanagementsystem.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdviceController {

    @ModelAttribute("activeTab")
    public String activeTab(HttpServletRequest request) {
        if (request.getRequestURI().contains("/")) {
            String[] path = request.getRequestURI().split("/");
            if (path.length >= 1) {
                return path[path.length - 1];
            }
        }
        return request.getRequestURI();
    }
}