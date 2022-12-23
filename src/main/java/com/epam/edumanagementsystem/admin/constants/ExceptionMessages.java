package com.epam.edumanagementsystem.admin.constants;

public final class ExceptionMessages {
    public ExceptionMessages() {
        throw new IllegalStateException();
    }

    public static final String EMPTY_FIELD = "Please, fill the required fields";
    public static final String ID_NULL = "Given id is null";
    public static final String NAME_NULL = "The name is null";
    public static final String ACADEMIC_CLASS_BY_ID = "Did not find academic class by given id";

    public static final String ACADEMIC_COURSE_NULL = "Academic course is null";
    public static final String NOT_PRESENT_COURSE_ID = "Does not present academic course by given id";
    public static final String ACADEMIC_COURSE_BY_NAME = "Academic course by given name does not exist";
    public static final String ACADEMIC_YEAR_BY_ID = "Did not find academic year by given id";
    public static final String ACADEMIC_YEAR_NULL = "Academic year is null";

    public static final String VACATION_BY_ID = "Did not find vacation by given id";
}
