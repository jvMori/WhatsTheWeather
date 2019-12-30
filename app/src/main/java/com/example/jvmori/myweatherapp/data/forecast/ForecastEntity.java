package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.util.Const;

import java.util.List;

public class ForecastEntity {
    private String dayOfWeek;
    private String mainDescription;
    private String detailedDescription;
    private String iconUrl;
    private String mainTemp;
    private String pressure;
    private String humidity;
    private String cloudness;
    private String windSpeed;
    private List<ForecastHourly> forecastHourlyList;

    public ForecastEntity(String dayOfWeek, String mainDescription, String detailedDescription, String iconUrl, String mainTemp, String pressure, String humidity, String cloudness, String windSpeed, List<ForecastHourly> forecastHourlyList) {
        this.dayOfWeek = dayOfWeek;
        this.mainDescription = mainDescription;
        this.detailedDescription = detailedDescription;
        this.iconUrl = iconUrl;
        this.mainTemp = mainTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudness = cloudness;
        this.windSpeed = windSpeed;
        this.forecastHourlyList = forecastHourlyList;
    }

    public String getFullIconUrl() {
        return Const.baseIconUrl + iconUrl + "@2x.png";
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public String getCloudness() {
        return cloudness;
    }

    public void setCloudness(String cloudness) {
        this.cloudness = cloudness;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<ForecastHourly> getForecastHourlyList() {
        return forecastHourlyList;
    }

    public void setForecastHourlyList(List<ForecastHourly> forecastHourlyList) {
        this.forecastHourlyList = forecastHourlyList;
    }
}
