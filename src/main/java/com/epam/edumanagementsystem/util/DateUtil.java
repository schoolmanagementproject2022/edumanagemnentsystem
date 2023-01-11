package com.epam.edumanagementsystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.MONDAY;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException();
    }

    public static String convert(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATTER);
        return simpleDateFormat.format(date);
    }

    public static LocalDate recurs(LocalDate localDate) {
        if (!localDate.getDayOfWeek().toString().equals(MONDAY)) {
            localDate = localDate.minusDays(1);
            localDate = recurs(localDate);
        }
        return localDate;
    }

}
