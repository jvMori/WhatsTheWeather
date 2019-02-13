
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity;

import com.example.jvmori.myweatherapp.architectureComponents.util.LocalDateConverter;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "forecast", indices = {@Index (value = {"mDate"}, unique = true)})
public class FutureWeatherEntry {
    @PrimaryKey()
    private String mCityName;

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

    public String getmCityName() {
        return mCityName;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }
}
