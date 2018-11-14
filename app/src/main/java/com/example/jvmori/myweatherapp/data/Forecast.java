package com.example.jvmori.myweatherapp.data;

public class Forecast
{
    private String date;
    private String day;
    private String tempHigh;
    private String tempLow;
    private String description;

    public Forecast(String date, String day, String tempHigh, String tempLow, String description) {
        this.date = date;
        this.day = day;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(String tempHigh) {
        this.tempHigh = tempHigh;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
