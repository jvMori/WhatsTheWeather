package com.example.jvmori.myweatherapp.util;


import androidx.annotation.Nullable;

public class WeatherParameters
{
    private String location;
    private boolean isDeviceLocation;
    private String lang;
    private String days;

    public WeatherParameters(String location, boolean isDeviceLocation, String lang, String days) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
        this.lang = lang;
        this.days = days;
    }
    public WeatherParameters(String location, boolean isDeviceLocation, String days) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
        this.lang = "en";
        this.days = days;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof WeatherParameters))
            return false;
        WeatherParameters param = (WeatherParameters) obj;
        return param.location.equals(this.location) &&
                param.isDeviceLocation == this.isDeviceLocation &&
                param.days.equals(this.days) &&
                param.lang.equals(this.lang);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDeviceLocation() {
        return isDeviceLocation;
    }

    public String getLang() {
        return lang;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
