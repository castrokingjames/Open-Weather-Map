package com.docutools.openweathermap.domain.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Calendars {

    public static Date toDate(long millis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(millis * 1000L);
        return calendar.getTime();
    }
}