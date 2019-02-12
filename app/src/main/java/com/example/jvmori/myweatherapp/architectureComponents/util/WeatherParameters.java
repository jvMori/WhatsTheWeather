package com.example.jvmori.myweatherapp.architectureComponents.util;


public class WeatherParameters
{
    private String location;
    private boolean isDeviceLocation;
    private String lang;

    public WeatherParameters(String location, boolean isDeviceLocation, String lang) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
        this.lang = lang;
    }
    public WeatherParameters(String location, boolean isDeviceLocation) {
        this.location = location;
        this.isDeviceLocation = isDeviceLocation;
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

}
