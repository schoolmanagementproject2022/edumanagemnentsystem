package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementtimetabe.model.entity.AcademicClass;
import com.epam.edumanagementtimetabe.rest.service.AcademicClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    private final AcademicCourseService academicCourseService;

    private final CoursesForTimetableService courseService;

    private final AcademicClassService academicClassService;

    private final TimetableService timetableService;

    public TimetableController(AcademicCourseService academicCourseService, CoursesForTimetableService courseService, AcademicClassService academicClassService, TimetableService timetableService) {
        this.academicCourseService = academicCourseService;
        this.courseService = courseService;
        this.academicClassService = academicClassService;
        this.timetableService = timetableService;
    }

    @GetMapping
    public String get4(Model model) {
        model.addAttribute("timetable", "timetable");
        return "timetable4";
    }

    @GetMapping("/creation")
    public String get4_1(Model model) {
        model.addAttribute("courses", academicCourseService.findAll());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("timetable", new TimetableDto());
        model.addAttribute("lessonsOfMonday", courseService.getCoursesForMonday("Monday"));
        return "timetable4-1";
    }

    @PostMapping()
    public String createTimetable(@ModelAttribute("timetable") TimetableDto timetableDto,
                                  @RequestParam("class") String thisClass, Model model) {
        AcademicClass byName = academicClassService.findByName(thisClass);
        timetableDto.setAcademicClass(byName);
        timetableService.create(timetableDto);
        model.addAttribute("lessonsOfMonday", courseService.getCoursesForMonday("Monday"));
        return "redirect:/timetable";
    }

    @PostMapping("/creation")
    public String post4_1(@ModelAttribute("courseForTable") CoursesForTimetableDto coursesForTimetableDto,
                          @RequestParam("nameOfDay") String nameOfDay, @RequestParam("class") String thisClass,
                          BindingResult result, Model model) {

        model.addAttribute("courses", academicCourseService.findAll());
        AcademicClass byName = academicClassService.findByName(thisClass);
        coursesForTimetableDto.setDayOfWeek(nameOfDay);
        coursesForTimetableDto.setAcademicClass(byName);
        courseService.create(coursesForTimetableDto);
        model.addAttribute("lessonsOfMonday", courseService.getCoursesForMonday("Monday"));
        return "timetable4-1";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            courseService.renameById(id);
        }
        return "redirect:/timetable/creation";
    }
}
