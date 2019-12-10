package com.example.jvmori.myweatherapp.data.current;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "current_weather", primaryKeys = {"city_name"})
public class CurrentWeatherUI {
    @ColumnInfo(name = "city_name")
    @NonNull
    private String city = "";
    private String country;
    private String mainDescription;
    private String detailedDescription;
    private String iconUrl;
    private String mainTemp;
    private String pressure;
    private String humidity;
    private String cloudness;
    private String windSpeed;
    private Long timestamp;

    public CurrentWeatherUI(Long timestamp, String city, String country, String mainDescription, String detailedDescription, String iconUrl, String mainTemp, String pressure, String humidity,  String cloudness, String windSpeed) {
        this.timestamp = timestamp;
        this.city = city;
        this.country = country;
        this.mainDescription = mainDescription;
        this.detailedDescription = detailedDescription;
        this.iconUrl = iconUrl;
        this.mainTemp = mainTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudness = cloudness;
        this.windSpeed = windSpeed;
    }

    public String getDate(){
        //TODO: convert timestamp to date
        return "Friday 11 June 2020";
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(String mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudness() {
        return cloudness;
    }

    public void setCloudness(String cloudness) {
        this.cloudness = cloudness;
    }
}
