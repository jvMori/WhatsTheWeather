package com.example.jvmori.myweatherapp.architectureComponents.util;


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
