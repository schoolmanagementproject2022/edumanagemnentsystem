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
import java.util.List;
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
        AcademicClass byName = academicClassService.findByName(name);
        model.addAttribute("timetable", timetableService.getByName(name));
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(byName)));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(byName)));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(byName)));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(byName)));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(byName)));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(byName)));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(byName)));
        return "timetable4";
    }

    @GetMapping("/classes/{name}/timetable/creation")
    public String get4_1(@PathVariable("name") String name, Model model) {
        AcademicClass byName = academicClassService.findByName(name);
        model.addAttribute("courses", academicCourseService.findAll());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("academicClass", academicClassService.findByName(name));
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(byName)));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(byName)));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(byName)));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(byName)));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(byName)));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(byName)));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(byName)));
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("class", name);
        return "timetable4-1";
    }

    @PostMapping("/classes/timetable/creation")
    public String createTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
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
                    model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
                    model.addAttribute("courses", academicCourseService.findAll());
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(timetable.getAcademicClass())));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
                    model.addAttribute("courses", academicCourseService.findAll());
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(timetable.getAcademicClass())));
                    model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(timetable.getAcademicClass())));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
                model.addAttribute("courses", academicCourseService.findAll());
                model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(timetable.getAcademicClass())));
                model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(timetable.getAcademicClass())));
                return "timetable4-1";
            }
        }
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            model.addAttribute("courseForTable", new CoursesForTimetableDto());
            model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
            model.addAttribute("courses", academicCourseService.findAll());
            model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(timetable.getAcademicClass())));
            model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(timetable.getAcademicClass())));
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
        AcademicClass byName = academicClassService.findByName(timetable.getAcademicClass().getClassNumber());
        timetable.setAcademicClass(byName);
        timetableService.create(timetable);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(timetable.getAcademicClass())));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(timetable.getAcademicClass())));
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
    }

    @PostMapping("/classes/{name}/timetable/course")
    public String post4_1(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                          BindingResult result, @PathVariable("name") String name,
                          Model model) {

        if (result.hasErrors()) {
            model.addAttribute("courses", academicCourseService.findAll());
            model.addAttribute("timetable", new Timetable());
            AcademicClass byName = academicClassService.findByName(name);
            model.addAttribute("academicClass", byName);
            model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(byName)));
            model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(byName)));
            model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(byName)));
            model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(byName)));
            model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(byName)));
            model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(byName)));
            model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(byName)));
            return "timetable4-1";
        }

        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courses", academicCourseService.findAll());

        coursesService.create(coursesForTimetableDto);
        AcademicClass byName = academicClassService.findByName(name);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(byName)));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(byName)));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(byName)));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(byName)));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(byName)));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(byName)));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(byName)));
        model.addAttribute("academicClass", byName);

        return "timetable4-1";
    }

    @GetMapping("/classes/course/delete/{id}/{class}")
    public String delete(@PathVariable("id") Long id, @PathVariable("class") String className) {
        if (id != null) {
            coursesService.delete(id);
        }
        return "redirect:/classes/" +  className + "/timetable/creation";
    }


    @GetMapping("/classes/{name}/timetable/edit")
    public String openTimetableIfExists(@PathVariable("name") String academicClassName,
                                        Model model) {

        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        if (!timetableService.isPresentTimetableByAcademicClassId(academicClass.getId())) {

            return "redirect:/classes/" + academicClassName + "/timetable";

        }

        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClassId("Monday", List.of(academicClass)));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClassId("Tuesday", List.of(academicClass)));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClassId("Wednesday", List.of(academicClass)));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClassId("Thursday", List.of(academicClass)));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClassId("Friday", List.of(academicClass)));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClassId("Saturday", List.of(academicClass)));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClassId("Sunday", List.of(academicClass)));
        return "redirect:/classes/{name}/timetable/edit";
    }
}
