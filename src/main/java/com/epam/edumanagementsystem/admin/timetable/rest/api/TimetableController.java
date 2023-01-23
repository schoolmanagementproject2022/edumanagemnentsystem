package com.epam.edumanagementsystem.admin.timetable.rest.api;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.mapper.CoursesForTimetableMapper;
import com.epam.edumanagementsystem.admin.timetable.model.dto.CoursesForTimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/classes/")
@Tag(name = "Timetable")
public class TimetableController {

    private final CoursesForTimetableService coursesService;

    private final AcademicClassService academicClassService;

    private final AcademicCourseService academicCourseService;

    private final TimetableService timetableService;
    private static final String TIMETABLE_HTML="timetable4";
    private static final String TIMETABLE_CREATION_HTML="timetable4-1";
    private static final String TIMETABLE_EDIT_HTML="timetableEdit";

    public TimetableController(CoursesForTimetableService coursesService, AcademicClassService academicClassService,
                               AcademicCourseService academicCourseService, TimetableService timetableService) {
        this.coursesService = coursesService;
        this.academicClassService = academicClassService;
        this.academicCourseService = academicCourseService;
        this.timetableService = timetableService;
    }

    @GetMapping("{academicClassName}/timetable")
    @Operation(summary = "Shows timetable page")
    public String openingTimetablePage(@PathVariable("academicClassName") String academicClassName, Model model) {
        boolean creationStatus = false;
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);

        if (timetableService.existTimetableByClassId(academicClass.getId())) {
            if (timetableService.findTimetableByAcademicClassId(academicClass.getId()).getStatus().equalsIgnoreCase("Edit") &&
                    !coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                    !coursesService.getCoursesWithActiveStatusByAcademicClassId(academicClass.getId()).isEmpty()) {
                List<CoursesForTimetable> activeCourses = coursesService.getCoursesWithActiveStatusByAcademicClassId(academicClass.getId());
                List<CoursesForTimetable> editCourses = coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId());
                for (CoursesForTimetable activeCourse : activeCourses) {
                    coursesService.deleteCourseById(activeCourse.getId());
                }
                for (CoursesForTimetable editedCourse : editCourses) {
                    coursesService.updateCourseStatusToActiveById(editedCourse.getId());
                }
                TimetableDto timetableByAcademicClassId = timetableService.findTimetableByAcademicClassId(academicClass.getId());
                timetableByAcademicClassId.setStatus("Active");
                timetableService.updateTimetableDatesAndStatusByAcademicClassId(timetableByAcademicClassId.getStartDate(),
                        timetableByAcademicClassId.getEndDate(), timetableByAcademicClassId.getStatus(), timetableByAcademicClassId.getAcademicClass().getId());
            }
            if (timetableService.findTimetableByAcademicClassId(academicClass.getId()).getStatus().equalsIgnoreCase("Active")) {
                List<CoursesForTimetable> editCourses = coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId());
                for (CoursesForTimetable editedCourse : editCourses) {
                    coursesService.deleteCourseById(editedCourse.getId());
                }
                model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(academicClassName));
                model.addAttribute("creationStatus", creationStatus);
                putLessons(model, academicClass.getId());
                return TIMETABLE_HTML;
            }
        }
        if (timetableService.existTimetableByClassId(academicClass.getId())) {
            model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(academicClassName));
        }
        model.addAttribute("creationStatus", creationStatus);
        putLessons(model, academicClass.getId());
        return TIMETABLE_HTML;
    }

    @GetMapping("{academicClassName}/timetable/created")
    @Operation(summary = "After successful creation of the timetable shows popup message")
    public String openingSuccessPopup(@PathVariable("academicClassName") String academicClassName, Model model) {
        boolean creationStatus = true;
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(academicClassName));
        model.addAttribute("creationStatus", creationStatus);
        putLessons(model, academicClass.getId());
        return TIMETABLE_HTML;
    }

    @GetMapping("{academicClassName}/timetable/creation")
    @Operation(summary = "Shows timetable creation page")
    public String openingTimetableCreationPage(@PathVariable("academicClassName") String academicClassName,
                                               @RequestParam(value = "lessonId", required = false) Long lessonId,
                                               @RequestParam(value = "cancelStatus", required = false, defaultValue = "notCancel") String status,
                                               Model model) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);
        List<CoursesForTimetable> coursesWithNotActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicClassId(academicClass.getId());
        boolean isExist = timetableService.existTimetableByClassId(academicClass.getId());

        if (!isExist && !coursesWithNotActiveStatus.isEmpty() &&
                lessonId == null && !status.equals("CANCEL")) {
            for (CoursesForTimetable course : coursesWithNotActiveStatus) {
                coursesService.deleteCourseById(course.getId());
            }
            sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("lessonId", lessonId);
            putLessons(model, academicClass.getId());
            return TIMETABLE_CREATION_HTML;
        }
        if (!isExist && coursesWithNotActiveStatus.isEmpty() &&
                !coursesService.getCoursesWithActiveStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                lessonId == null && !status.equals("CANCEL")) {
            if (coursesService.isPresentCoursesForClass(academicClass.getId())) {
                List<CoursesForTimetable> allCourses = coursesService.getCoursesByAcademicClassId(academicClass.getId());
                for (CoursesForTimetable course : allCourses) {
                    coursesService.deleteCourseById(course.getId());
                }
                sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("lessonId", lessonId);
                putLessons(model, academicClass.getId());
                return TIMETABLE_CREATION_HTML;
            }
        }
        sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
        sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
        model.addAttribute("lessonId", lessonId);
        putLessons(model, academicClass.getId());
        return TIMETABLE_CREATION_HTML;
    }

    @GetMapping("{academicClassName}/timetable/course")
    public String getAddLessonsPopup(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);

        sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
        sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/creation";
    }

    @GetMapping("course/delete/{id}/{class}")
    @Operation(summary = "Deletes selected lesson from timetable")
    public String deleteLessonFromTimetable(@PathVariable("id") Long lessonId, @PathVariable("class") String academicClassName) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);

        if (lessonId != null) {
            if (timetableService.existTimetableByClassId(academicClass.getId())) {
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
    @Operation(summary = "Shows popup message for deleting a lesson during timetable editing")
    public String showDeletePopUpEdit(@PathVariable("academicClassName") String academicClassName,
                                      @PathVariable("lessonId") Long lessonId,
                                      RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("lessonId", lessonId);
        return "redirect:/classes/" + academicClassName + "/timetable/edit";
    }

    @GetMapping("{academicClassName}/timetable/create/{lessonId}")
    @Operation(summary = "Shows popup message for deleting a lesson during timetable creation")
    public String showDeletePopUpCreate(@PathVariable("academicClassName") String academicClassName,
                                        @PathVariable("lessonId") Long lessonId,
                                        RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("lessonId", lessonId);
        return "redirect:/classes/" + academicClassName + "/timetable/creation";
    }

    @GetMapping("{academicClassName}/timetable/show")
    @Operation(summary = "Shows the timetable for the academic class if one exists")
    public String openTimetableIfExists(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        if (timetableService.findTimetableByAcademicClassId(academicClass.getId()) != null) {
            return "redirect:/classes/" + academicClassName + "/timetable";
        }
        putLessons(model, academicClass.getId());
        return "redirect:/classes/{name}/timetable/creation";
    }

    @GetMapping("{academicClassName}/timetable/editCourse")
    public String openingPopupEdit(@PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);

        sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
        sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
        putLessons(model, academicClass.getId());
        return "redirect:/classes/" + academicClassName + "/timetable/edit";
    }

    @GetMapping("{academicClassName}/timetable/edit")
    @Operation(summary = "Shows page for timetable editing")
    public String openingTimetableEdit(@PathVariable("academicClassName") String academicClassName,
                                       @RequestParam(value = "lessonId", required = false) Long lessonId, Model model) {

        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        TimetableDto currentTimetable = timetableService.findTimetableByAcademicClassId(academicClass.getId());
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);
        List<CoursesForTimetable> activeStatus = coursesService.getCoursesWithActiveStatusByAcademicClassId(academicClass.getId());
        List<CoursesForTimetable> editStatus = coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId());
        List<CoursesForTimetable> notActiveStatus = coursesService.getCoursesWithNotActiveStatusByAcademicClassId(academicClass.getId());

        if (currentTimetable.getStatus().equalsIgnoreCase("Active")) {
            if (!activeStatus.isEmpty() && editStatus.isEmpty() && notActiveStatus.isEmpty()) {
                for (CoursesForTimetable activeLesson : activeStatus) {
                    CoursesForTimetable editedLesson = new CoursesForTimetable();
                    editedLesson.setDayOfWeek(activeLesson.getDayOfWeek());
                    editedLesson.setAcademicCourse(activeLesson.getAcademicCourse());
                    editedLesson.setStatus("Edit");
                    editedLesson.setAcademicClass(activeLesson.getAcademicClass());
                    coursesService.create(CoursesForTimetableMapper.toCoursesForTimetableDto(editedLesson));
                }
                sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                sendToFrontCurrentTimetableAndLessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            }
            if (!activeStatus.isEmpty() && !editStatus.isEmpty() && !notActiveStatus.isEmpty()) {
                for (CoursesForTimetable notActiveLesson : notActiveStatus) {
                    coursesService.deleteCourseById(notActiveLesson.getId());
                }
                sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                sendToFrontCurrentTimetableAndLessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            }
            if (!activeStatus.isEmpty() && editStatus.isEmpty() && !notActiveStatus.isEmpty()) {
                for (CoursesForTimetable notActiveLesson : notActiveStatus) {
                    coursesService.deleteCourseById(notActiveLesson.getId());
                }
                sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                sendToFrontCurrentTimetableAndLessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            }
            if (!activeStatus.isEmpty() && !editStatus.isEmpty() && notActiveStatus.isEmpty()) {
                sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                sendToFrontCurrentTimetableAndLessonId(model, currentTimetable, lessonId);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            }
        }

        if (currentTimetable.getStatus().equalsIgnoreCase("Edit"))
            if (!activeStatus.isEmpty() && !editStatus.isEmpty() && notActiveStatus.isEmpty()) {
                for (CoursesForTimetable activeLesson : activeStatus) {
                    coursesService.deleteCourseById(activeLesson.getId());
                }
                for (CoursesForTimetable editedLesson : editStatus) {
                    editedLesson.setStatus("Active");
                    coursesService.create(CoursesForTimetableMapper.toCoursesForTimetableDto(editedLesson));
                }
            }
        sendToFrontNewTimetableAndAcademicClassName(model, academicClassName);
        sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
        sendToFrontCurrentTimetableAndLessonId(model, currentTimetable, lessonId);
        return TIMETABLE_EDIT_HTML;
    }

    @PostMapping("{academicClassName}/timetable/editCourse")
    @Operation(summary = "Creates lessons for timetable being edited")
    public String addingLessonsEdit(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                    BindingResult result, @PathVariable("academicClassName") String academicClassName,
                                    Model model) {
        Timetable newTimetable = new Timetable();
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        TimetableDto timetable = timetableService.findTimetableByAcademicClassId(academicClass.getId());
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);
        putLessons(model, academicClass.getId());

        if (!coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return TIMETABLE_EDIT_HTML;
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(coursesForTimetableDto);
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return TIMETABLE_EDIT_HTML;
        }

        if (coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                timetable.getStatus().equalsIgnoreCase("Active")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                return TIMETABLE_EDIT_HTML;
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(coursesForTimetableDto);
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return TIMETABLE_EDIT_HTML;
        }
        if (coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                timetable.getStatus().equalsIgnoreCase("Edit")) {
            coursesForTimetableDto.setStatus("Edit");
            if (result.hasErrors()) {
                model.addAttribute("timetable", newTimetable);
                putEditedLessons(model, academicClass.getId());
                sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
                model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
                timetableService.updateTimetableStatusByAcademicClassId("Active", academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            }
            coursesForTimetableDto.setStatus("Edit");
            coursesService.create(coursesForTimetableDto);
            model.addAttribute("timetable", newTimetable);
            putEditedLessons(model, academicClass.getId());
            sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
            model.addAttribute("timetable", timetable);
            return TIMETABLE_EDIT_HTML;
        }

        coursesForTimetableDto.setStatus("Edit");
        coursesService.create(coursesForTimetableDto);
        timetableService.updateTimetableStatusByAcademicClassId("Active", academicClass.getId());
        model.addAttribute("timetable", newTimetable);
        putEditedLessons(model, academicClass.getId());
        sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
        return TIMETABLE_EDIT_HTML;
    }

    @PostMapping("{academicClassName}/timetable/creation")
    @Operation(summary = "Creates timetable for academic class")
    public String createTimetable(@ModelAttribute("timetable") @Valid TimetableDto timetableDto, BindingResult result,
                                  @PathVariable("academicClassName") String academicClassName, Model model) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = timetableDto.getStartDate();
        LocalDate endDate = timetableDto.getEndDate();
        String invalidMsg = "Please, select right dates";
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);

        if (ChronoUnit.DAYS.between(startDate, endDate) < 7){
            invalidMsg = "Timetable duration can't be less than 7 days";
            model.addAttribute("invalid", invalidMsg);
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            putLessons(model, academicClass.getId());
            return TIMETABLE_CREATION_HTML;
        }
        if (result.hasErrors()) {
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                    putLessons(model, timetableDto.getAcademicClass().getId());
                }
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putLessons(model, academicClass.getId());
                return TIMETABLE_CREATION_HTML;
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                    putLessons(model, timetableDto.getAcademicClass().getId());
                }
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putLessons(model, academicClass.getId());
                return TIMETABLE_CREATION_HTML;
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putLessons(model, timetableDto.getAcademicClass().getId());
                return TIMETABLE_CREATION_HTML;
            }
        }

        Period diffOfDate = Period.between(endDate, startDate);
        if ((startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now)) ||
                (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) || (startDate.equals(endDate))) {
            model.addAttribute("invalid", invalidMsg);
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            putLessons(model, timetableDto.getAcademicClass().getId());
            return TIMETABLE_CREATION_HTML;
        }

        if (!coursesService.isPresentCoursesForClass(academicClass.getId())) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            putLessons(model, timetableDto.getAcademicClass().getId());
            return TIMETABLE_CREATION_HTML;
        }

        timetableDto.setAcademicClass(AcademicClassMapper.toAcademicClass(academicClass));
        timetableDto.setStatus("Active");
        timetableService.create(timetableDto);
        putLessons(model, timetableDto.getAcademicClass().getId());
        return "redirect:/classes/" + timetableDto.getAcademicClass().getClassNumber() + "/timetable/created";
    }

    @PostMapping("{academicClassName}/timetable/course")
    @Operation(summary = "Adds lessons to timetable")
    public String addingLessonsIntoTimetable(@ModelAttribute("courseForTable") @Valid CoursesForTimetableDto coursesForTimetableDto,
                                             BindingResult result, @PathVariable("academicClassName") String academicClassName, Model model) {
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);

        if (result.hasErrors()) {
            model.addAttribute("timetable", new TimetableDto());
            sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
            putLessons(model, academicClass.getId());
            model.addAttribute("dayOfWeek", coursesForTimetableDto.getDayOfWeek());
            return TIMETABLE_CREATION_HTML;
        }

        coursesForTimetableDto.setStatus("Active");
        coursesService.create(coursesForTimetableDto);
        model.addAttribute("timetable", new TimetableDto());
        putLessons(model, academicClass.getId());
        sendToFrontAllAcademicCoursesAndAcademicClass(model, allAcademicCourses, academicClass);
        return TIMETABLE_CREATION_HTML;
    }

    @PostMapping("{academicClassName}/timetable/edit")
    @Operation(summary = "Edits timetable for academic class")
    public String editTimetable(@ModelAttribute("timetable") @Valid TimetableDto timetable, BindingResult result,
                                @PathVariable("academicClassName") String academicClassName, Model model) {

        LocalDate now = LocalDate.now();
        LocalDate startDate = timetable.getStartDate();
        LocalDate endDate = timetable.getEndDate();
        String invalidMsg = "Please, select right dates";
        Set<AcademicCourseDto> allAcademicCourses = academicCourseService.findAllAcademicCoursesInClassByName(academicClassName);
        AcademicClassDto academicClass = academicClassService.findByClassNumber(academicClassName);

        if (result.hasErrors()) {
            if (ChronoUnit.DAYS.between(startDate, endDate) < 7){
                invalidMsg = "Timetable duration can't be less than 7 days";
                model.addAttribute("invalid", invalidMsg);
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, timetable.getAcademicClass().getId());
                return TIMETABLE_EDIT_HTML;
            }
            if (!result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                if (startDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                    putEditedLessons(model, timetable.getAcademicClass().getId());
                }
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            } else if (result.hasFieldErrors("startDate") && !result.hasFieldErrors("endDate")) {
                if (endDate.isBefore(now)) {
                    model.addAttribute("invalid", invalidMsg);
                    sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                    putEditedLessons(model, timetable.getAcademicClass().getId());
                }
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, academicClass.getId());
                return TIMETABLE_EDIT_HTML;
            } else if (result.hasFieldErrors("startDate") && result.hasFieldErrors("endDate")) {
                sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
                putEditedLessons(model, timetable.getAcademicClass().getId());
                return TIMETABLE_EDIT_HTML;
            }
        }
        Period diffOfDate = Period.between(endDate, startDate);
        if (startDate.isAfter(endDate) || startDate.isBefore(now) || endDate.isBefore(now) ||
                (diffOfDate.getYears() <= -1 && diffOfDate.getMonths() <= 0) || (startDate.equals(endDate))) {
            model.addAttribute("invalid", invalidMsg);
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            putEditedLessons(model, timetable.getAcademicClass().getId());
            return TIMETABLE_EDIT_HTML;
        }
        if (coursesService.isPresentCoursesForClass(academicClass.getId()) &&
                !coursesService.getCoursesWithActiveStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                coursesService.getCoursesWithEditStatusByAcademicClassId(academicClass.getId()).isEmpty() &&
                coursesService.getCoursesWithNotActiveStatusByAcademicClassId(academicClass.getId()).isEmpty()) {
            model.addAttribute("noLessonInTimetable", "Please, select Courses");
            sendToFrontAllAcademicCoursesNewCourseForTimetableAndAcademicClass(model, allAcademicCourses, academicClass);
            putEditedLessons(model, timetable.getAcademicClass().getId());
            return TIMETABLE_EDIT_HTML;
        }

        timetable.setAcademicClass(AcademicClassMapper.toAcademicClass(academicClass));
        timetableService.updateTimetableDatesAndStatusByAcademicClassId(startDate, endDate, "Edit", academicClass.getId());
        putEditedLessons(model, timetable.getAcademicClass().getId());
        return "redirect:/classes/" + timetable.getAcademicClass().getClassNumber() + "/timetable";
    }
}