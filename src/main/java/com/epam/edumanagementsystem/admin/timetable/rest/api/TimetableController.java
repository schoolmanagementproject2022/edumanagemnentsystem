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
        AcademicClass academicClass = academicClassService.findByName(name);
        Long academicClassId = academicClass.getId();
        model.addAttribute("timetable", timetableService.getByName(name));
        putLessons(model, academicClassId);
        return "timetable4";
    }

    @GetMapping("/classes/{name}/timetable/creation")
    public String get4_1(@PathVariable("name") String name, Model model) {
        AcademicClass academicClass = academicClassService.findByName(name);

        model.addAttribute("courses", academicCourseService.findAll());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("academicClass", academicClassService.findByName(name));
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",academicClass.getId()));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",academicClass.getId()));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",academicClass.getId()));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",academicClass.getId()));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",academicClass.getId()));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",academicClass.getId()));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",academicClass.getId()));
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
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",timetable.getAcademicClass().getId()));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
                    model.addAttribute("courses", academicCourseService.findAll());
                    model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",timetable.getAcademicClass().getId()));
                    model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",timetable.getAcademicClass().getId()));
                }
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
                model.addAttribute("courses", academicCourseService.findAll());
                model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",timetable.getAcademicClass().getId()));
                model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",timetable.getAcademicClass().getId()));
                return "timetable4-1";
            }
        }
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            model.addAttribute("courseForTable", new CoursesForTimetableDto());
            model.addAttribute("academicClass", academicClassService.findByName(timetable.getAcademicClass().getClassNumber()));
            model.addAttribute("courses", academicCourseService.findAll());
            model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",timetable.getAcademicClass().getId()));
            model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",timetable.getAcademicClass().getId()));
            return "timetable4-1";
        }
        AcademicClass byName = academicClassService.findByName(timetable.getAcademicClass().getClassNumber());
        timetable.setAcademicClass(byName);
        timetableService.create(timetable);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",timetable.getAcademicClass().getId()));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",timetable.getAcademicClass().getId()));
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
            model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",byName.getId()));
            model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",byName.getId()));
            model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",byName.getId()));
            model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",byName.getId()));
            model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",byName.getId()));
            model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",byName.getId()));
            model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",byName.getId()));
            return "timetable4-1";
        }

        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courses", academicCourseService.findAll());
        coursesService.create(coursesForTimetableDto);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",coursesForTimetableDto.getAcademicClass().getId()));

        model.addAttribute("academicClass", academicClassService.findByName(name));
        AcademicClass byName = academicClassService.findByName(name);
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday", byName.getId()));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday", byName.getId()));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday", byName.getId()));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday", byName.getId()));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday", byName.getId()));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday", byName.getId()));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday", byName.getId()));
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

        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",academicClass.getId()));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",academicClass.getId()));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",academicClass.getId()));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",academicClass.getId()));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",academicClass.getId()));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",academicClass.getId()));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",academicClass.getId()));
        return "redirect:/classes/{name}/timetable/edit";
    }

    private Model putLessons(Model model, Long academicClassId) {
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday",academicClassId));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday",academicClassId));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday",academicClassId));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday",academicClassId));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday",academicClassId));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday",academicClassId));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday",academicClassId));
        return model;
    }
}
