package com.epam.edumanagementsystem.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

    @ModelAttribute("allUrl")
    public String allTab(HttpServletRequest request) {
        if (request.getRequestURI().contains("/")) {
            String requestURI = request.getRequestURI();
            return requestURI;
        }
        return request.getRequestURI();
    }

    @ModelAttribute("firstUrl")
    public String firstUrl(HttpServletRequest request) {
        if (request.getRequestURI().contains("/")) {
            String[] path = request.getRequestURI().split("/");
            if (path.length >= 1) {
                return path[path.length - (path.length - 1)];
            }
        }
        return request.getRequestURI();
    }

    @ModelAttribute("middleUrl")
    public String middleUrl(HttpServletRequest request) {
        if (request.getRequestURI().contains("/")) {
            String[] path = request.getRequestURI().split("/");
            if (path.length > 2) {
                String decode = URLDecoder.decode(path[path.length - (path.length - 2)], StandardCharsets.UTF_8);
                return decode;
            } else if (path.length == 2) {
                String decoded = URLDecoder.decode(path[path.length - 1], StandardCharsets.UTF_8);
                return decoded;
            }
        }
        return URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
    }
}