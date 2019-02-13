
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import com.google.gson.annotations.SerializedName;

public class Forecastday {
    @SerializedName("date")
    private String mDate;
    @SerializedName("day")
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

}
