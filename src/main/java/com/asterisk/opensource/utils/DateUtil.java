package com.asterisk.opensource.utils;

import com.google.common.base.Preconditions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/9
 * @Time 9:45
 * @Description
 * @Since 1.0.0
 */
public class DateUtil {

    private DateUtil() {
        //No Construct
    }

    public static Date toDate(LocalDate localDate) {
        Preconditions.checkNotNull(localDate,"localDate should't be null");
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        Preconditions.checkNotNull(localDateTime,"localDateTime should't not be null");
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        Preconditions.checkNotNull(date,"Date should't be null");
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        Preconditions.checkNotNull(date,"Date should't be null");
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
