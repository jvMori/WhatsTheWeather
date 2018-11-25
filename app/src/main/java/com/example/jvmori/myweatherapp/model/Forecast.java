package com.example.jvmori.myweatherapp.model;

import java.io.Serializable;

public class Forecast implements Serializable
{
    private String date;
    private  String code;
    private String day;
    private String tempHigh;
    private String tempLow;
    private String description;

    public Forecast(String date, String code, String day, String tempHigh, String tempLow, String description) {
        this.date = date;
        this.code = code;
        this.day = day;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
