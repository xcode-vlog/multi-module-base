package com.lime.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {}

    public static Instant toInstant(Object src) {
        return switch (src) {
            case Integer i-> Instant.ofEpochSecond(i);
            case Long l -> Instant.ofEpochMilli(l);
            case Date d -> d.toInstant();
            default -> throw new IllegalArgumentException("wrong type need to Integer|Long|Date");
        };
    }
    public static LocalDateTime toLocalDateTime(Object src) {
        return switch(src) {
            case LocalDateTime ldt -> ldt;
            default -> LocalDateTime.ofInstant(toInstant(src), ZoneId.systemDefault()) ;
        };
    }
}
