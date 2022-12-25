package com.epam.edumanagementsystem.admin.constants;

public final class GlobalConstants {

    private GlobalConstants() {
        throw new IllegalStateException();
    }

    public static final String MONDAY = "MONDAY";
    public static final int FIELD_MAX_SIZE = 50;
    public static final String REGEXP = "[^@]+@[^@]+\\.[a-zA-Z]{2,3}$";
    public static final String ACADEMIC_COURSE_SECTION = "academicCourseSection";
    public static final String DATE_FORMATTER = "dd-MM-yyyy";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String ALL_COURSES_IN_ACADEMIC_CLASS = "allCoursesInAcademicClass";

}
