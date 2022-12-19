package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.CoursesForTimetableService;
import com.epam.edumanagementsystem.admin.timetable.rest.service.TimetableService;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.epam.edumanagementsystem.admin.timetable.rest.api.UtilForTimetableController.putLessons;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;
    private final TimetableService timetableService;
    private final CoursesForTimetableService coursesForTimetableService;

    @Autowired
    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository, TimetableService timetableService, CoursesForTimetableService coursesForTimetableService) {
        this.academicClassRepository = academicClassRepository;
        this.timetableService = timetableService;
        this.coursesForTimetableService = coursesForTimetableService;
    }

    @Override
    public AcademicClass create(AcademicClass academicClass) {
        if (academicClass == null) {
            throw new NullPointerException("Please, fill the required fields");
        }
        return academicClassRepository.save(academicClass);
    }

    @Override
    public AcademicClassDto getById(Long id) {
        Optional<AcademicClass> classById = academicClassRepository.findById(id);
        if (classById.isPresent()) {
            return AcademicClassMapper.toDto(classById.get());
        }
        return new AcademicClassDto();
    }

    @Override
    public AcademicClass findByName(String name) {
        return academicClassRepository.findByClassNumber(name);
    }

    @Override
    public AcademicClass update(AcademicClass academicClass) {
        AcademicClass updateAcademicClass = findByName(academicClass.getClassNumber());
        if (academicClass.getClassNumber() != null) {
            updateAcademicClass.setClassNumber(academicClass.getClassNumber());
        }
        if (academicClass.getAcademicCourseSet() != null) {
            Set<AcademicCourse> academicCourse = academicClass.getAcademicCourseSet();
            for (AcademicCourse academicCourse1 : academicCourse) {
                updateAcademicClass.getAcademicCourseSet().add(academicCourse1);
            }
        }

        if (academicClass.getTeacher() != null) {
            Set<Teacher> teacher = academicClass.getTeacher();
            for (Teacher teachers : teacher)
                updateAcademicClass.getTeacher().add(teachers);
        }

        if (academicClass.getClassroomTeacher() != null) {
            updateAcademicClass.setClassroomTeacher(academicClass.getClassroomTeacher());
        }

        return create(updateAcademicClass);
    }

    @Override
    public List<AcademicCourse> findAllAcademicCourses(String name) {
        List<AcademicCourse> listOfCourses = new ArrayList<>();
        Set<AcademicCourse> academicCourseSet = findByName(name).getAcademicCourseSet();
        for (AcademicCourse course : academicCourseSet) {
            listOfCourses.add(course);
        }
        return listOfCourses;
    }

    @Override
    public Set<Teacher> findAllTeachers(String name) {
        return findByName(name).getTeacher();
    }

    @Override
    public Set<Teacher> findAllTeacher() {
        Set<Teacher> teachersByAcademicClass = new HashSet<>();
        List<AcademicClass> academicClasses = academicClassRepository.findAll();
        for (AcademicClass academicClass : academicClasses) {
            Set<Teacher> result = academicClass.getTeacher();
            teachersByAcademicClass.addAll(result);
        }
        return teachersByAcademicClass;

    }

    @Override
    public LocalDate recurs(LocalDate localDate) {
        if (!localDate.getDayOfWeek().toString().equals("MONDAY")) {
            localDate = localDate.minusDays(1);
            localDate = recurs(localDate);
        }
        return localDate;
    }

    @Override
    public List<AcademicClassDto> findAll() {
        List<AcademicClass> academicClassList = academicClassRepository.findAll();
        return AcademicClassMapper.academicClassDtoList(academicClassList);
    }

    @Override
    public Set<AcademicClass> findAcademicClassByTeacherId(Long id) {
        return academicClassRepository.findAcademicClassByTeacherId(id);
    }

    @Override
    public void openJournal(String date, String startDate, String name, Model model) {
        if (date != null) {
            startDate = date;
        }
        AcademicClass academicClassByName = findByName(name);

        LocalDate timetableStartDate = timetableService.findTimetableByAcademicClassName(name).getStartDate();
        LocalDate timetableEndDate = timetableService.findTimetableByAcademicClassName(name).getEndDate();
        model.addAttribute("startDateInput", timetableStartDate);
        model.addAttribute("endDateInput", timetableEndDate);
        LocalDate journalStartDate = null;

        if (startDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localdate = LocalDate.parse(startDate, formatter);
            if (!localdate.isAfter(timetableEndDate) && !localdate.isBefore(timetableStartDate)) {
                journalStartDate = localdate;
            } else if (!localdate.isAfter(timetableStartDate)) {
                journalStartDate = timetableStartDate;
            } else if (!localdate.isBefore(timetableEndDate)) {
                journalStartDate = timetableEndDate;
            }
        } else {
            if (timetableStartDate.isAfter(LocalDate.now())) {
                journalStartDate = timetableStartDate;
            } else {
                journalStartDate = LocalDate.now();
            }
        }
        journalStartDate = recurs(journalStartDate);
        List<AcademicCourse> academicCoursesInClass = findAllAcademicCourses(name);
        model.addAttribute("allCoursesInAcademicClass", academicCoursesInClass);
        boolean existDay = false;
        for (int i = 0; i < 7; i++) {
            existDay = getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate, existDay);
            journalStartDate = journalStartDate.plusDays(1);
        }
        if (!existDay) {
            if (journalStartDate.isAfter(timetableEndDate)) {
                journalStartDate = journalStartDate.minusDays(14);
            }
            for (int i = 0; i < 7; i++) {
                getCoursesInWeekDays(model, journalStartDate, academicClassByName, timetableStartDate, timetableEndDate, existDay);
                journalStartDate = journalStartDate.plusDays(1);
            }
        }
        String journalStartDateToString = journalStartDate.toString();
        model.addAttribute("month", journalStartDate.getMonth());
        model.addAttribute("year", journalStartDate.getYear());
        model.addAttribute("startDate", journalStartDateToString);
    }

    @Override
    public void doNotOpenJournal_timetableIsNotExist(String date, String startDate, String className, Model model) {
        if (date != null) {
            startDate = date;
        }
        model.addAttribute("timetable", timetableService.findTimetableByAcademicClassName(className));
        model.addAttribute("creationStatus", false);
        putLessons(model, findByName(className).getId());
    }

    private boolean getCoursesInWeekDays(Model model, LocalDate journalStartDate, AcademicClass academicClassByName,
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