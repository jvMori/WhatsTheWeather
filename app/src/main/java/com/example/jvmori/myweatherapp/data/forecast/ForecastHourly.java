package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.util.Const;

public class ForecastHourly {
    private String mainDescription;
    private String detailedDescription;
    private String iconUrl;
    private String mainTemp;
    private String pressure;
    private String humidity;
    private String cloudness;
    private String windSpeed;
    private String hour;

    public ForecastHourly(String mainDescription, String detailedDescription, String iconUrl, String mainTemp, String pressure, String humidity, String cloudness, String windSpeed, String hour) {
        this.mainDescription = mainDescription;
        this.detailedDescription = detailedDescription;
        this.iconUrl = iconUrl;
        this.mainTemp = mainTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudness = cloudness;
        this.windSpeed = windSpeed;
        this.hour = hour;
    }

    public String getFullIconUrl() {
        return Const.baseIconUrl + iconUrl + "@2x.png";
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
