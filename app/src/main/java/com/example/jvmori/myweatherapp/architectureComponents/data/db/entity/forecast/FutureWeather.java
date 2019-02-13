
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.architectureComponents.util.LocalDateConverter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

public class FutureWeather {

    @SerializedName("date")
    @TypeConverters(LocalDateConverter.class)
    private LocalDate mDate;

    @SerializedName("day")
    @Embedded
    private Day mDay;

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public Day getDay() {
        return mDay;
    }

    public void setDay(Day day) {
        mDay = day;
    }

}
