
package com.example.jvmori.myweatherapp.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.util.LocalDateConverter;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class FutureWeather {

    @SerializedName("date")
    @TypeConverters(LocalDateConverter.class)
    private String mDate;

    @SerializedName("day")
    @Embedded
    private Day mDay;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Day getDay() {
        return mDay;
    }

    public void setDay(Day day) {
        mDay = day;
    }

    public String getDayOfWeek(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d");
        LocalDate localDate = LocalDate.parse(mDate);
        return  formatter.format(localDate);
    }

}
