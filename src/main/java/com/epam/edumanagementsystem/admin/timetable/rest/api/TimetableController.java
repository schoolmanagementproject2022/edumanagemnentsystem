package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class TimetableController {

    private final AcademicCourseService academicCourseService;
    private final CoursesForTimetableService coursesService;
    private final AcademicClassService academicClassService;
    private final TimetableService timetableService;


    public TimetableController(AcademicCourseService academicCourseService,
                               CoursesForTimetableService coursesService,
                               AcademicClassService academicClassService, TimetableService timetableService) {

        this.academicCourseService = academicCourseService;
        this.coursesService = coursesService;
        this.academicClassService = academicClassService;
        this.timetableService = timetableService;
    }

    @GetMapping("/classes/{name}/timetable")
    public String get4(@PathVariable("name") String name, Model model) {
        model.addAttribute("timetable", timetableService.getByName(name));
        return "timetable4";
    }

    @GetMapping("/classes/{name}/timetable/creation")
    public String get4_1(@PathVariable("name") String name, Model model) {
        model.addAttribute("courses", academicCourseService.findAll());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("academicClass", academicClassService.findByName(name));
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("class", name);
        return "timetable4-1";
    }

    @PostMapping("/classes/timetable/creation")
    public String createTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
                                  @RequestParam("class") String thisClass,
                                  Model model) {
        LocalDate startDate = timetable.getStartDate();
        LocalDate endDate = timetable.getEndDate();
        LocalDate now = LocalDate.now();
        String invalidMsg = "Please, select right dates";

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("academicClass", academicClassService.findByName(thisClass));
                    model.addAttribute("courses", academicCourseService.findAll());
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("academicClass", academicClassService.findByName(thisClass));
                    model.addAttribute("courses", academicCourseService.findAll());
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("academicClass", academicClassService.findByName(thisClass));
                model.addAttribute("courses", academicCourseService.findAll());
                model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
                return "timetable4-1";
            }
        }
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            model.addAttribute("courseForTable", new CoursesForTimetableDto());
            model.addAttribute("academicClass", academicClassService.findByName(thisClass));
            model.addAttribute("courses", academicCourseService.findAll());
            model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
            return "timetable4-1";
        }
//        else if (endDate.getYear() - 1 > now.getYear()) {//end-start =12  max=12  10.05.2022-10.15.2022
//            if (endDate.getMonth().getValue() - now.getMonth().getValue() > 0){
//                model.addAttribute("invalid", invalidMsg);
//                model.addAttribute("courseForTable", new CoursesForTimetableDto());
//                model.addAttribute("academicClass", academicClassService.findByName("5A"));
//                model.addAttribute("courses", academicCourseService.findAll());
//                model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
//            }
//            return "timetable4-1";
//        }
        AcademicClass byName = academicClassService.findByName(thisClass);
        timetable.setAcademicClass(byName);
        timetableService.create(timetable);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
        return "redirect:/timetable";
    }

    @PostMapping("/classes/{name}/timetable/course")
    public String post4_1(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                          BindingResult result, @PathVariable("name") String name,
                          Model model) {

        if (result.hasErrors()) {
            model.addAttribute("courses", academicCourseService.findAll());
            model.addAttribute("timetable", new Timetable());
            model.addAttribute("academicClass", academicClassService.findByName(name));
            return "timetable4-1";
        }

        coursesService.create(coursesForTimetableDto);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courses", academicCourseService.findAll());
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForMonday("Monday"));
        model.addAttribute("academicClass", academicClassService.findByName(name));
        return "timetable4-1";
    }

    @GetMapping("/classes/course/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        if (id != null) {
            coursesService.delete(id);
        }
        return "redirect:/timetable/creation";
    }
}
