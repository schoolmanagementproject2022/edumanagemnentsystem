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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.*;

@Controller
@RequestMapping("/classes/")
public class TimetableController {

    private final CoursesForTimetableService coursesService;

    private final AcademicClassService academicClassService;

    private final TimetableService timetableService;

    public TimetableController(CoursesForTimetableService coursesService, AcademicClassService academicClassService,
                               TimetableService timetableService) {
        this.coursesService = coursesService;
        this.academicClassService = academicClassService;
        this.timetableService = timetableService;
    }

    @GetMapping("{academicClassName}/timetable")
    public String openingTimetablePage(@PathVariable("academicClassName") String academicClassName, Model model) {
        boolean creationStatus = false;
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

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
                model.addAttribute("timetable", timetableService.getByName(academicClassName));
                model.addAttribute("creationStatus", creationStatus);
                putLessons(model, academicClass.getId());
                return "timetable4";
            }
        }
        model.addAttribute("timetable", timetableService.getByName(academicClassName));
        model.addAttribute("creationStatus", creationStatus);
        putLessons(model, academicClass.getId());
        return "timetable4";
    }

    @GetMapping("{academicClassName}/timetable/created")
    public String openingSuccessPopup(@PathVariable("academicClassName") String academicClassName, Model model) {
        boolean creationStatus = true;
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        model.addAttribute("timetable", timetableService.getByName(academicClassName));
        model.addAttribute("creationStatus", creationStatus);
        putLessons(model, academicClass.getId());
        return "timetable4";
    }

    @GetMapping("{academicClassName}/timetable/creation")
    public String openingTimetableCreationPage(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        Timetable timetableByAcademicClassId = timetableService.getTimetableByAcademicClassId(academicClass.getId());
        List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());

        if (timetableByAcademicClassId == null &&
                coursesWithNotActiveStatus.size() != 0) {
            for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                coursesService.deleteCourseById(course.getId());
            }
            newTimetable_academicClassName(model, academicClassName);
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putLessons(model, academicClass.getId());
            return "timetable4-1";
        }
        if (timetableByAcademicClassId == null && coursesWithNotActiveStatus.size() == 0 &&
                coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0) {
            if (coursesService.isPresentCoursesForClass(academicClass.getId())) {
                List<CoursesForTimetable> allCourses = coursesService.getCoursesByAcademicClassId(academicClass.getId());
                for (CoursesForTimetable course : allCourses) {
                    coursesService.deleteCourseById(course.getId());
                }
                newTimetable_academicClassName(model, academicClassName);
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            }
        }
        newTimetable_academicClassName(model, academicClassName);
        allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
        putLessons(model, academicClass.getId());
        return "timetable4-1";
    }

    @GetMapping("{academicClassName}/timetable/course")
    public String getAddLessonsPopup(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);

        newTimetable_academicClassName(model, academicClassName);
        allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/creation";
    }

    @GetMapping("course/delete/{id}/{class}")
    public String deleteLessonFromTimetable(@PathVariable("id") Long lessonId, @PathVariable("class") String academicClassName) {
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

    @GetMapping("{academicClassName}/timetable/edit/{lessonId}")
    public String showDeletePopUp(@PathVariable("academicClassName") String academicClassName, @PathVariable("lessonId") Long lessonId,
                                  RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("lessonId", lessonId);
        return "redirect:/classes/" + academicClassName + "/timetable/edit";
    }

    @GetMapping("{academicClassName}/timetable/show")
    public String openTimetableIfExists(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        if (timetableService.getTimetableByAcademicClassId(academicClass.getId()) != null) {
            return "redirect:/classes/" + academicClassName + "/timetable";
        }
        putLessons(model, academicClass.getId());
        return "redirect:/classes/{name}/timetable/creation";
    }

    @GetMapping("{academicClassName}/timetable/editCourse")
    public String openingPopupEdit(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);

        newTimetable_academicClassName(model, academicClassName);
        allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/edit";
    }

    @GetMapping("{academicClassName}/timetable/edit")
    public String openingTimetableEdit(@PathVariable("academicClassName") String academicClassName,
                                       @RequestParam(value = "lessonId", required = false) Long lessonId, Model model) {

        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        Timetable currentTimetable = timetableService.getTimetableByAcademicClassId(academicClass.getId());
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        List<CoursesForTimetable> activeStatus = coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId());
        List<CoursesForTimetable> editStatus = coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId());
        List<CoursesForTimetable> notActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId());

        if (currentTimetable.getStatus().equalsIgnoreCase("Active")) {
            if (activeStatus.size() != 0 && editStatus.size() == 0 && notActiveStatus.size() == 0) {
                for (CoursesForTimetable activeLesson : activeStatus) {
                    CoursesForTimetable editedLesson = new CoursesForTimetable();
                    editedLesson.setDayOfWeek(activeLesson.getDayOfWeek());
                    editedLesson.setAcademicCourse(activeLesson.getAcademicCourse());
                    editedLesson.setStatus("Edit");
                    editedLesson.setAcademicClass(activeLesson.getAcademicClass());
                    coursesService.create(editedLesson);
                }
                newTimetable_academicClassName(model, academicClassName);
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                currentTimetable_LessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (activeStatus.size() != 0 && editStatus.size() != 0 && notActiveStatus.size() != 0) {
                for (CoursesForTimetable notActiveLesson : notActiveStatus) {
                    coursesService.deleteCourseById(notActiveLesson.getId());
                }
                newTimetable_academicClassName(model, academicClassName);
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                currentTimetable_LessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (activeStatus.size() != 0 && editStatus.size() == 0 && notActiveStatus.size() != 0) {
                for (CoursesForTimetable notActiveLesson : notActiveStatus) {
                    coursesService.deleteCourseById(notActiveLesson.getId());
                }
                newTimetable_academicClassName(model, academicClassName);
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                currentTimetable_LessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            }
            if (activeStatus.size() != 0 && editStatus.size() != 0 && notActiveStatus.size() == 0) {
                newTimetable_academicClassName(model, academicClassName);
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                currentTimetable_LessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            }
        }

        if (currentTimetable.getStatus().equalsIgnoreCase("Edit"))
            if (activeStatus.size() != 0 && editStatus.size() != 0 && notActiveStatus.size() == 0) {
                for (CoursesForTimetable activeLesson : activeStatus) {
                    coursesService.deleteCourseById(activeLesson.getId());
                }
                for (CoursesForTimetable editedLesson : editStatus) {
                    editedLesson.setStatus("Active");
                    coursesService.create(editedLesson);
                }
            }
        newTimetable_academicClassName(model, academicClassName);
        allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
        currentTimetable_LessonId(model, currentTimetable, lessonId);
        return "timetableEdit";
    }

    @PostMapping("{academicClassName}/timetable/editCourse")
    public String addingLessonsEdit(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                    BindingResult result, @PathVariable("academicClassName") String academicClassName,
                                    Model model) {
        Timetable newTimetable = new Timetable();
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        Timetable timetable = timetableService.getTimetableByAcademicClassId(academicClass.getId());
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        putLessons(model, academicClass.getId());

        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return "timetableEdit";
        }

        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return "timetableEdit";
        }
        if (coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                timetable.getStatus().equalsIgnoreCase("Edit")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                timetableService.updateTimetableStatusByAcademicClassId("Active", academicClass.getId());
                return "timetableEdit";
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return "timetableEdit";
        }

        coursesForTimetableDto.setStatus("Edit");
        coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
        timetableService.updateTimetableStatusByAcademicClassId("Active", academicClass.getId());
        model.addAttribute("timetable", newTimetable);
        putEditedLessons(model, academicClass.getId());
        allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
        return "timetableEdit";
    }

    @PostMapping("{academicClassName}/timetable/creation")
    public String createTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
                                  @PathVariable("academicClassName") String academicClassName, Model model) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = timetable.getStartDate();
        LocalDate endDate = timetable.getEndDate();
        String invalidMsg = "Please, select right dates";
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);
        AcademicClass academicClass = academicClassService.findByName(academicClassName);

        if (!coursesService.isPresentCoursesForClass(academicClass.getId())) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                    putLessons(model, timetable.getAcademicClass().getId());
                }
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putLessons(model, academicClass.getId());
                return "timetable4-1";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putLessons(model, timetable.getAcademicClass().getId());
                return "timetable4-1";
            }
        }

        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        } else if (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) {
            model.addAttribute("invalid", invalidMsg);
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putLessons(model, timetable.getAcademicClass().getId());
            return "timetable4-1";
        }

        timetable.setAcademicClass(academicClass);
        timetable.setStatus("Active");
        timetableService.create(timetable);
        putLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable/created";
    }

    @PostMapping("{academicClassName}/timetable/course")
    public String addingLessonsIntoTimetable(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                             BindingResult result, @PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClass academicClass = academicClassService.findByName(academicClassName);
        List<AcademicCourse> allAcademicCourses = academicClassService.findAllAcademicCourses(academicClassName);

        if (result.hasErrors()) {
            model.addAttribute("timetable", new Timetable());
            allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
            putLessons(model, academicClass.getId());
            model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
            return "timetable4-1";
        }

        coursesForTimetableDto.setStatus("Active");
        coursesService.create(CoursesForTimetableMapper.toCoursesForTimetable(coursesForTimetableDto));
        model.addAttribute("timetable", new Timetable());
        putLessons(model, academicClass.getId());
        allAcademicCourses_academicClass(model, allAcademicCourses, academicClass);
        return "timetable4-1";
    }

    @PostMapping("{academicClassName}/timetable/edit")
    public String editTimetable(@ModelAttribute("timetable") @Valid Timetable timetable, BindingResult result,
                                @PathVariable("academicClassName") String academicClassName, Model model) {

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
                    allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                    putEditedLessons(model, timetable.getAcademicClass().getId());
                }
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                    putEditedLessons(model, timetable.getAcademicClass().getId());
                }
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, academicClass.getId());
                return "timetableEdit";
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, timetable.getAcademicClass().getId());
                return "timetableEdit";
            }
        }
        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) {
            model.addAttribute("invalid", invalidMsg);
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putEditedLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        } else if (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) {
            model.addAttribute("invalid", invalidMsg);
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putEditedLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        }
        if (coursesService.isPresentCoursesForClass(academicClass.getId()) &&
                coursesService.getCoursesWithActiveStatusByAcademicCourseId(academicClass.getId()).size() != 0 &&
                coursesService.getCoursesWithEditStatusByAcademicCourseId(academicClass.getId()).size() == 0 &&
                coursesService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClass.getId()).size() == 0) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            allAcademicCourses_newCourseForTimetable_academicClass(model, allAcademicCourses, academicClass);
            putEditedLessons(model, timetable.getAcademicClass().getId());
            return "timetableEdit";
        }

        timetable.setAcademicClass(academicClass);
        timetableService.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate, "Edit", academicClass.getId());
        putEditedLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
    }
}