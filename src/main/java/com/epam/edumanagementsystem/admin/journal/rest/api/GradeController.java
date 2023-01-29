package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.rest.service.GradeService;
import org.springframework.stereotype.Controller;

@Controller
public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }
}
