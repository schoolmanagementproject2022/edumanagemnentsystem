package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.NOT_PRESENT_COURSE_ID;

@Service
public class AcademicCourseServiceImpl implements AcademicCourseService {

    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassService academicClassService;
    private final CoursesForTimetableService coursesForTimetableService;
    private final Logger logger = Logger.getLogger(AcademicCourseServiceImpl.class.getName());

    public AcademicCourseServiceImpl(AcademicCourseRepository academicCourseRepository,
                                     AcademicClassService academicClassService,
                                     CoursesForTimetableService coursesForTimetableService) {
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassService = academicClassService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public AcademicCourseDto findByName(String name) {
        logger.info("Finding Academic Course by Name");
        return AcademicCourseMapper.toDto(academicCourseRepository.findByName(name).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public AcademicCourseDto findById(Long id) {
        logger.info("Finding Academic Course by Id");
        return AcademicCourseMapper.toDto(academicCourseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_PRESENT_COURSE_ID)));
    }

    @Override
    public AcademicCourseDto save(AcademicCourseDto academicCourse) {
        logger.info("Saving Academic Course");
        return AcademicCourseMapper.toDto(academicCourseRepository.save(AcademicCourseMapper.toAcademicCourse(academicCourse)));
    }

    @Override
    public List<AcademicCourseDto> findAll() {
        logger.info("Finding All Academic Courses");
        return AcademicCourseMapper.toListOfAcademicCourseDto(academicCourseRepository.findAll());
    }

    @Override
    public AcademicCourseDto update(AcademicCourseDto academicCourse) {
        logger.info("Updating Academic Course");
        AcademicCourseDto academicCourseByNameDto = findByName(academicCourse.getName());
        academicCourseByNameDto.getTeachers().addAll(academicCourse.getTeachers());
        return save(academicCourseByNameDto);
    }

    @Override
    public Set<AcademicCourseDto> findAllByTeacherId(Long id) {
        logger.info("Saving Academic Course");
        return AcademicCourseMapper.toSetOfAcademicCourseDto(academicCourseRepository.findAllByTeachersId(id));
    }

    @Override
    public List<AcademicCourseDto> findAllAcademicCoursesInClassByName(String name) {
        logger.info("Finding All Academic Courses in Class by Name");
        return new ArrayList<>(AcademicCourseMapper.toSetOfAcademicCourseDto(academicClassService.findByClassNumber(name).getAcademicCourse()));
    }

    @Override
    public void checkCourseDuplication(AcademicCourseDto academicCourseDto, BindingResult bindingResult, Model model) {
        if (findAll().stream().anyMatch(name -> name.getName().equalsIgnoreCase(academicCourseDto.getName()))) {
            bindingResult.addError(new ObjectError("academicCourse", "Duplicate course name"));
            model.addAttribute("duplicated", "An Academic Course with the same name already exists");
        }
    }

    //todo
    private boolean showCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClass academicClassByName,
                                          LocalDate timetableStartDate, LocalDate timetableEndDate, boolean existDay) {

        String deyOfWeek = journalStartDate.getDayOfWeek().toString();
        model.addAttribute(deyOfWeek, journalStartDate);
        String day = StringUtils.capitalize(deyOfWeek.toLowerCase(Locale.ROOT));
        List<String> coursesByDayOfWeekAndStatusAndAcademicClassId = coursesForTimetableService
                .getCoursesNamesByDayOfWeekAndStatusAndAcademicClassId(day, "Active", academicClassByName.getId());
        model.addAttribute(deyOfWeek.toLowerCase(Locale.ROOT), coursesByDayOfWeekAndStatusAndAcademicClassId);
        if (!coursesByDayOfWeekAndStatusAndAcademicClassId.isEmpty() &&
                !journalStartDate.isBefore(timetableStartDate) && !journalStartDate.isAfter(timetableEndDate)) {
            model.addAttribute(day, true);
            existDay = true;
        } else {
            model.addAttribute(day, false);
        }
        return existDay;
    }

}