package com.epam.edumanagementsystem.admin.journal.rest.api;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.admin.journal.rest.service.ClassworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.HomeworkService;
import com.epam.edumanagementsystem.admin.journal.rest.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/agendas/check/")
public class CheckAgendasController {

    private final ClassworkService classworkService;
    private final HomeworkService homeworkService;
    private final TestService testService;

    public CheckAgendasController(ClassworkService classworkService, HomeworkService homeworkService,
                                  TestService testService) {
        this.classworkService = classworkService;
        this.homeworkService = homeworkService;
        this.testService = testService;
    }

    @GetMapping("{classId}/{courseId}/{date}")
    public @ResponseBody
    SaveAgendaDto checkingAgendas(@PathVariable(value = "classId", required = false) Long classId,
                                 @PathVariable(value = "courseId", required = false) Long courseId,
                                 @PathVariable(value = "date", required = false) String date) {
        SaveAgendaDto saveAgendaDto = new SaveAgendaDto();
        Classwork classWorkOfCourse = classworkService.getClassWorkOfCourse(LocalDate.parse(date), classId, courseId);
        Homework homeworkOfCourse = homeworkService.getHomeworkOfCourse(LocalDate.parse(date), classId, courseId);
        Test testOfCourse = testService.getTestOfCourse(LocalDate.parse(date), classId, courseId);
        if (homeworkOfCourse != null) {
            saveAgendaDto.setHomework(homeworkOfCourse.getHomework());
        }
        if (classWorkOfCourse != null) {
            saveAgendaDto.setClasswork(classWorkOfCourse.getClasswork());
        }
        if (testOfCourse != null) {
            saveAgendaDto.setTest(testOfCourse.getTest());
        }
        return saveAgendaDto;
    }

}
