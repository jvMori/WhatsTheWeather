package com.example.jvmori.myweatherapp.data.forecast;

public class ForecastHourly {
    private String iconUrl;
    private String mainTemp;
    private String hour;

    public ForecastHourly(String iconUrl, String mainTemp, String hour) {
        this.iconUrl = iconUrl;
        this.mainTemp = mainTemp;
        this.hour = hour;
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
