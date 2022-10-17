package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.timetable.mapper.CoursesForTimetableMapper;
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

        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
            if (timetableService.getTimetableByAcademicClassId(academicClass.getId()).getStatus().equalsIgnoreCase("Edit") &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                List<CoursesForTimetable> activeCourses = coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId());
                List<CoursesForTimetable> editCourses = coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable activeCourse : activeCourses) {
                    coursesService.deleteCourseById(activeCourse.getId());
                }
                for (CoursesForTimetable editedCourse : editCourses) {
                    coursesService.updateCourseStatusToActiveById(editedCourse.getId());
                }
                Timetable timetable = timetableService.getTimetableByAcademicClassId(academicClass.getId());
                timetable.setStatus("Active");
                timetableService.updateTimetableDatesAndStatusByAcademicClassId(timetable.getStartDate(), timetable.getEndDate(), timetable.getStatus(), timetable.getAcademicClass().getId());
            }
            if (timetableService.getTimetableByAcademicClassId(academicClass.getId()).getStatus().equalsIgnoreCase("Active")) {
                List<CoursesForTimetable> editCourses = coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable editedCourse : editCourses) {
                    coursesService.deleteCourseById(editedCourse.getId());
                }
                model.addAttribute("timetable", timetableService.getByName(name));
                putLessons(model, academicClass.getId());
                return "timetable4";

            }
        }
        model.addAttribute("timetable", timetableService.getByName(name));
        putLessons(model, academicClass.getId());
        return "timetable4";
    }

    @GetMapping("/classes/{name}/timetable/creation")
    public String get4_1(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) == null &&
                coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
            List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());
            for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                coursesService.deleteCourseById(course.getId());
            }
            model.addAttribute("class", academicClassName);
            model.addAttribute("timetable", new Timetable());
            model.addAttribute("courseForTable", new CoursesForTimetableDto());
            model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
            model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
            putLessons(model, academicClass.getId());
            return "timetable4-1";
        }
        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) == null &&
                coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
            if (coursesService.isPresentCoursesForClass(academicClass.getId())) {
                List<CoursesForTimetable> allCourses = coursesService.getCoursesByAcademicClassId(academicClass.getId());
                for (CoursesForTimetable course : allCourses) {
                    coursesService.deleteCourseById(course.getId());
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
        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
        model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
        putLessons(model, academicClass.getId());
        return "timetable4-1";
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
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putLessons(model, timetable.getAcademicClass().getId());
                return "timetable4-1";
            }
        }
        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        } else if (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        if (!coursesService.isPresentCoursesForClass(academicClass.getId())) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        timetable.setAcademicClass(academicClass);
        timetable.setStatus("Active");
        timetableService.create(timetable);
        putLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
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

    @PostMapping("/classes/{name}/timetable/course")
    public String addingLessons(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                BindingResult result, @PathVariable("name") String academicClassName,
                                Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        Timetable newTimetable = new Timetable();

        if (result.hasErrors()) {
            model.addAttribute("timetable", newTimetable);
            model.addAttribute("courses", allAcademicCourses);
            model.addAttribute("academicClass", academicClass);
            putLessons(model, academicClass.getId());
            model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
            return "timetable4-1";
        }
        coursesForTimetableDto.setStatus("Active");
        coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
        model.addAttribute("timetable", newTimetable);
        model.addAttribute("courses", allAcademicCourses);
        putLessons(model, academicClass.getId());
        model.addAttribute("academicClass", academicClass);
        return "timetable4-1";
    }

    @GetMapping("/classes/course/delete/{id}/{class}")
    public String delete(@PathVariable("id") Long lessonId, @PathVariable("class") String academicClassName) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (lessonId != null) {
            if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
                coursesService.updateCourseStatusById(lessonId);
                return "redirect:/classes/" + academicClassName + "/timetable/edit";
            } else {
                coursesService.updateCourseStatusById(lessonId);
                return "redirect:/classes/" + academicClassName + "/timetable/creation";
            }
        }
        return "redirect:/classes/" + academicClassName + "/timetable/creation";
    }

    @GetMapping("/classes/{name}/timetable/show")
    public String openTimetableIfExists(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
            return "redirect:/classes/" + academicClassName + "/timetable";
        }
        putLessons(model, academicClass.getId());
        return "redirect:/classes/{name}/timetable/creation";
    }


    @GetMapping("/classes/{name}/timetable/editCourse")
    public String getPopupEdit(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
        model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/edit";
    }

    @PostMapping("/classes/{name}/timetable/editCourse")
    public String addingLessonsEdit(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                    BindingResult result, @PathVariable("name") String academicClassName,
                                    Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        Timetable newTimetable = new Timetable();
        Timetable timetable = timetableService.getTimetableByAcademicClassId(academicClass.getId());

        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");

            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                model.addAttribute("courses", allAcademicCourses);
                model.addAttribute("academicClass", academicClass);
                putEditLessons(model, academicClass.getId());
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            model.addAttribute("courses", allAcademicCourses);
            putEditLessons(model, academicClass.getId());
            model.addAttribute("academicClass", academicClass);
            return "timetableEdit";
        }

        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");

            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                model.addAttribute("courses", allAcademicCourses);
                model.addAttribute("academicClass", academicClass);
                putEditLessons(model, academicClass.getId());
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            model.addAttribute("courses", allAcademicCourses);
            putEditLessons(model, academicClass.getId());
            model.addAttribute("academicClass", academicClass);
            return "timetableEdit";
        }
        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                timetable.getStatus().equalsIgnoreCase("Edit")) {
            coursesForTimetableDto.setStatus("Edit");

            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                model.addAttribute("courses", allAcademicCourses);
                model.addAttribute("academicClass", academicClass);
                putEditLessons(model, academicClass.getId());
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                timetableService.updateTimetableStatusByAcademicClassId("Active",academicClass.getId());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            model.addAttribute("courses", allAcademicCourses);
            putEditLessons(model, academicClass.getId());
            model.addAttribute("academicClass", academicClass);
            return "timetableEdit";
        }

        coursesForTimetableDto.setStatus("Edit");
        coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
        timetableService.updateTimetableStatusByAcademicClassId("Active", academicClass.getId());
        model.addAttribute("timetable", newTimetable);
        model.addAttribute("courses", allAcademicCourses);
        putEditLessons(model, academicClass.getId());
        model.addAttribute("academicClass", academicClass);
        return "timetableEdit";
        }




    @GetMapping("/classes/{name}/timetable/edit")
    public String showTimetableEdit(@PathVariable("name") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        Timetable currentTimetable = timetableService.getTimetableByAcademicClassId(academicClass.getId());

        if (currentTimetable.getStatus().equalsIgnoreCase("Active")) {
            if (coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                    coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0) {
                List<CoursesForTimetable> activeLessons = coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable activeLesson : activeLessons) {
                    CoursesForTimetable editLesson = new CoursesForTimetable();
                    editLesson.setDayOfWeek(activeLesson.getDayOfWeek());
                    editLesson.setAcademicCourse(activeLesson.getAcademicCourse());
                    editLesson.setStatus("Edit");
                    editLesson.setAcademicClass(activeLesson.getAcademicClass());
                    coursesService.create(editLesson);
                }
                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                    coursesService.deleteCourseById(course.getId());
                }
                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                    coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
                List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                    coursesService.deleteCourseById(course.getId());
                }

                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0) {

                model.addAttribute("class", academicClassName);
                model.addAttribute("timetable", new Timetable());
                model.addAttribute("courseForTable", new CoursesForTimetableDto());
                model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
                model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            }
        }

        if (currentTimetable.getStatus().equalsIgnoreCase("Edit"))
            if (coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                    coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0) {

                List<CoursesForTimetable> coursesWithActiveStatus = coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId());
                List<CoursesForTimetable> lessonsWithEditStatus = coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId());
                for (CoursesForTimetable activeLesson : coursesWithActiveStatus) {
                    coursesService.deleteCourseById(activeLesson.getId());
                }
                for (CoursesForTimetable editLessons : lessonsWithEditStatus) {
                    editLessons.setStatus("Active");
                    coursesService.create(editLessons);
                }
            }

        model.addAttribute("class", academicClassName);
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("courseForTable", new CoursesForTimetableDto());
        model.addAttribute("courses", academicClassService.findAllAcademicCourses(academicClassName));
        model.addAttribute("academicClass", academicClassService.findByName(academicClassName));
        putLessons(model, academicClass.getId());
        return "timetableEdit";

    }


    @PostMapping("/classes/{name}/timetable/edit")
    public String editTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
                                @PathVariable("name") String academicClassName, Model model) {

        LocalDate now = LocalDate.now();
        LocalDate startDate = timetable.getStartDate();
        LocalDate endDate = timetable.getEndDate();
        String invalidMsg = "Please, select right dates";
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        CoursesForTimetableDto newCoursesForTimetable = new CoursesForTimetableDto();
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                    putEditLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                    putEditLessons(model, timetable.getAcademicClass().getId());
                }
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putEditLessons(model, academicClass.getId());
                return "timetableEdit";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
                putEditLessons(model, timetable.getAcademicClass().getId());
                return "timetableEdit";
            }
        }
        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putEditLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        } else if (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) {
            model.addAttribute("invalid", invalidMsg);
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putEditLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        }

        if (coursesService.isPresentCoursesForClass(academicClass.getId()) &&
                coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0) {

            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            timetableService.updateTimetableStatusByAcademicClassId("Edit", academicClass.getId());
            duplicatedModelAttributes(model, allAcademicCourses, newCoursesForTimetable, academicClass);
            putEditLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        }

        timetable.setAcademicClass(academicClass);
        timetableService.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate, "Edit", academicClass.getId());

        putEditLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
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

    private Model putEditLessons(Model model, Long academicClassId) {
        model.addAttribute("lessonsOfMonday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Monday", "Edit", academicClassId));
        model.addAttribute("lessonsOfTuesday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Tuesday", "Edit", academicClassId));
        model.addAttribute("lessonsOfWednesday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Wednesday", "Edit", academicClassId));
        model.addAttribute("lessonsOfThursday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Thursday", "Edit", academicClassId));
        model.addAttribute("lessonsOfFriday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Friday", "Edit", academicClassId));
        model.addAttribute("lessonsOfSaturday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Saturday", "Edit", academicClassId));
        model.addAttribute("lessonsOfSunday", coursesService.getCoursesByDayOfWeekAndStatusAndAcademicClassId("Sunday", "Edit", academicClassId));
        return model;
    }
}