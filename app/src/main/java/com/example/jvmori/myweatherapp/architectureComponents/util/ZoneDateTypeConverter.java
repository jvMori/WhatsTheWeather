package com.example.jvmori.myweatherapp.architectureComponents.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import androidx.room.TypeConverter;

public class ZoneDateTypeConverter
{
    @TypeConverter
    public static ZonedDateTime fromEpochSecond(long date) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneOffset.UTC);
    }

    @TypeConverter
    public static long fromZonedDateTime(ZonedDateTime zdt) {
        return zdt.toEpochSecond();
    }
}
