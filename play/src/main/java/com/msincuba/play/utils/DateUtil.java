package com.msincuba.play.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
    }

    public static Date convert(LocalDate localDate) {
        return localDate != null ? Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static Date convert(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static LocalDate asLocalDate(Date date) {
        return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

}
