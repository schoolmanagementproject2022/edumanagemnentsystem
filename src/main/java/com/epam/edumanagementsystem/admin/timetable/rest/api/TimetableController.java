package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
public class TimetableController {

    private final CoursesForTimetableService coursesService;
    private final AcademicClassService academicClassService;
    private final TimetableService timetableService;

    public TimetableController(CoursesForTimetableService coursesService,
                               AcademicClassService academicClassService, TimetableService timetableService) {
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
    public String get4_1(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) == null) {

            if (coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                    coursesService.delete(course.getId());
                }
                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                    coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {

                if (coursesService.isPresentCoursesForClass(academicClass.getId())) {
                    List<CoursesForTimetable> allCourses = coursesService.getCoursesByAcademicClassId(academicClass.getId());
                    for (CoursesForTimetable course : allCourses) {
                        coursesService.delete(course.getId());
                    }
                    model.addAttribute("class", academicClassName);
                    model.addAttribute("timetable", new Timetable());
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                    model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                    putLessons(model, academicClass.getId());
                    return "timetable4-1";
                }
            }
        }
        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
            if (coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                    coursesService.delete(course.getId());
                }
                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                    coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                //should be deleted
                if (coursesService.isPresentCoursesForClass(academicClass.getId())) {
                    List<CoursesForTimetable> allCourses = coursesService.getCoursesByAcademicClassId(academicClass.getId());

                    model.addAttribute("class", academicClassName);
                    model.addAttribute("timetable", new Timetable());
                    model.addAttribute("courseForTable", new CoursesForTimetableDto());
                    model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                    model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                    putLessons(model, academicClass.getId());
                    return "timetable4-1";
                }
            }
        }
        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
        model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
        putLessons(model, academicClass.getId());
        return "timetable4-1";
    }


    @GetMapping("/classes/{name}/timetable/course")
    public String getPopup(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
        model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/creation";
    }

    @PostMapping("/classes/{name}/timetable/creation")
    public String createTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
                                  @PathVariable("name") String academicClassName, Model model) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = timetable.getStartDate();
        LocalDate endDate = timetable.getEndDate();
        String invalidMsg = "Please, select right dates";
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        CoursesForTimetableDto newCoursesForTimetable = new CoursesForTimetableDto();
        AcademicClass classByName = academicClassService.findByName(academicClassName);

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
                putLessons(model, classByName.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
                putLessons(model, classByName.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
                putLessons(model, timetable.getAcademicClass().getId());
                return "timetable4-1";
            }
        }
        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        } else if (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        if (!coursesService.isPresentCoursesForClass(classByName.getId())) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, classByName);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        timetable.setAcademicClass(classByName);
        timetableService.create(timetable);
        putLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
    }

    @PostMapping("/classes/{name}/timetable/course")
    public String addingLessons(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                BindingResult result, @PathVariable("name") String academicClassName,
                                Model model) {
        AcademicClass getClassByName = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        Timetable newTimetable = new Timetable();

        coursesForTimetableDto.setStatus("Active");
        if (result.hasErrors()) {
            model.addAttribute("timetable", newTimetable);
            model.addAttribute("courses", allAcademicCourses);
            model.addAttribute("academicClass", getClassByName);
            putLessons(model, getClassByName.getId());

            model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
            return "timetable4-1";
        }

        coursesService.create(coursesForTimetableDto);
        model.addAttribute("timetable", newTimetable);
        model.addAttribute("courses", allAcademicCourses);
        putLessons(model, getClassByName.getId());
        model.addAttribute("academicClass", getClassByName);
        return "timetable4-1";
    }

    @GetMapping("/classes/course/delete/{id}/{class}")
    public String delete(@PathVariable("id") Long lessonId, @PathVariable("class") String academicClassName) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (lessonId != null) {
            if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
                coursesService.deleteById(lessonId);
                return "redirect:/classes/" + academicClassName + "/timetable/creation";
            } else {
                coursesService.updateCourseStatusById(lessonId);
                return "redirect:/classes/" + academicClassName + "/timetable/creation";
            }
        }
        return "redirect:/classes/" + academicClassName + "/timetable/preCreation";
    }

    @GetMapping("/classes/{name}/timetable/edit")
    public String openTimetableIfExists(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        if (!timetableService.isPresentTimetableByAcademicClassId(academicClass.getId())) {
            return "redirect:/classes/" + academicClassName + "/timetable";
        }

        putLessons(model, academicClass.getId());
        return "redirect:/classes/{name}/timetable/edit";
    }

    private void duplicatedModelAttributes(Model model, List<AcademicCourse> allAcademicCourses, CoursesForTimetableDto newCoursesForTimetable, AcademicClass classByName) {
        model.addAttribute("courseForTable", newCoursesForTimetable);
        model.addAttribute("academicClass", classByName);
        model.addAttribute("courses", allAcademicCourses);
    }

    private Model putLessons(Model model, Long academicClassId) {
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesForDayAndClass("Monday", academicClassId));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesForDayAndClass("Tuesday", academicClassId));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesForDayAndClass("Wednesday", academicClassId));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesForDayAndClass("Thursday", academicClassId));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesForDayAndClass("Friday", academicClassId));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesForDayAndClass("Saturday", academicClassId));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesForDayAndClass("Sunday", academicClassId));
        return model;
    }
}
