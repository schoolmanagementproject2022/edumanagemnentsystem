package com.epam.edumanagementsystem.util;

public class AppConstants {

    private AppConstants(){
    }

    public static final int FIELD_MAX_SIZE = 50;

    public static final int MAX_FILE_SIZE = 2 * 2048 * 2048;

    public static final int MAX_REQUEST_SIZE = 2 * 2048 * 2048;

    public static final String DUPLICATED = "duplicated";

    public static final String PASSWORD_PATTERN =
            "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&])[A-Za-z\\\\d()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&]{9,50}";

    public static final String EMAIL_PATTERN =
            "^([^\\,;:\\'\"\\[\\]<>!@ #)($%}~&*+\\-=?\\/^_`\\\\{|\\.](?!.*[!#$%&*+-/=?^_`{|]{2})[a-zA-Z0-9!#$%&*+-/=?^_`{|]{0,50}[^\\,;:\\'\"\\[\\]<>!@ #)($%}~&*+\\-=?\\/^_`\\\\{|\\.]+)@([a-zA-Z0-9\\-]{1,50})\\.([a-z]{2,50})$";

    public static final String PROPERTY_PATTERN =
            "^[A-Za-z0-9-<> _`*,:|()]+$";
}
