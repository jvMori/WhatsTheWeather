package com.example.jvmori.myweatherapp.architectureComponents.util;

import java.time.ZonedDateTime;

public class WeatherParameters
{
    private String location;
    private boolean isDeviceLocation;
    private String lang;
    private ZonedDateTime lastFetchedTime;

    public WeatherParameters(String location, boolean isDeviceLocation, ZonedDateTime lastFetchedTime, String lang) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
        this.lastFetchedTime = lastFetchedTime;
        this.lang = lang;
    }
    public WeatherParameters(String location, boolean isDeviceLocation, ZonedDateTime lastFetchedTime) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
        this.lastFetchedTime = lastFetchedTime;
        this.lang = "en";
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

    public void setDeviceLocation(boolean deviceLocation) {
        isDeviceLocation = deviceLocation;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ZonedDateTime getLastFetchedTime() {
        return lastFetchedTime;
    }

    public void setLastFetchedTime(ZonedDateTime lastFetchedTime) {
        this.lastFetchedTime = lastFetchedTime;
    }
}
