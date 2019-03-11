package com.example.jvmori.myweatherapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import androidx.room.TypeConverter;

public class LocalDateConverter
{
    @TypeConverter
    public static LocalDate fromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate zdt) {
        return zdt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
