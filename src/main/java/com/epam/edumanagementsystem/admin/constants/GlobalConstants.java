package com.epam.edumanagementsystem.admin.constants;

public final class GlobalConstants {

    private GlobalConstants() {
        throw new IllegalStateException();
    }

    public static final String MONDAY = "MONDAY";
    public static final String CRON = "0 0 22 1/1 * ?";
    public static final int FIELD_MAX_SIZE = 50;
    public static final String REGEXP = "[^@]+@[^@]+\\.[a-zA-Z]{2,3}$";
    public static final String ACADEMIC_COURSE_SECTION = "academicCourseSection";
    public static final String DATE_FORMATTER_JOURNAL = "yyyy-MM-dd";
    public static final String DATE_FORMATTER = "dd-MM-yyyy";
    public static final String ALL_COURSES_IN_ACADEMIC_CLASS = "allCoursesInAcademicClass";

    public static final String USED_SYMBOLS = "<>-_`*,:|() symbols can be used.";
    public static final String CLASS_EXISTS = "Class already exists";

    //URLS
    public static final String ACADEMIC_CLASSES_REDIRECT = "redirect:/classes/";
    public static final String VACATION_REDIRECT = "redirect:/vacations";
    public static final String ACADEMIC_COURSES_URL = "/courses";
    public static final String STUDENTS_URL = "/students";
    public static final String CLASSROOM_URL = "/classroom";

    //HTML
    public static final String ACADEMIC_CLASSES_SECTION = "academicClassSection";
    public static final String ACADEMIC_COURSE_FOR_ACADEMIC_CLASS = "academicCourseForAcademicClass";
    public static final String TEACHERS_FOR_ACADEMIC_CLASSES = "teachersForAcademicClass";
    public static final String CLASSROOM_TEACHER_SECTION = "classroomTeacherSection";
    public static final String ACADEMIC_CLASS_SECTION_FOR_STUDENTS = "academicClassSectionForStudents";
    public static final String VACATION_SECTION = "vacationSection";
    public static final String ACADEMIC_YEAR_SECTION = "academicYearSection";


}
