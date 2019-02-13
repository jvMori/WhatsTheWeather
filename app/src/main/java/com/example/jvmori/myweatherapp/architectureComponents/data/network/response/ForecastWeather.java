
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import com.google.gson.annotations.SerializedName;

public class ForecastWeather {

    @SerializedName("current")
    private CurrentWeather mCurrent;
    @SerializedName("forecast")
    private Forecast mForecast;
    @SerializedName("location")
    private Location mLocation;

    public CurrentWeather getCurrent() {
        return mCurrent;
    }

    public void setCurrent(CurrentWeather current) {
        mCurrent = current;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

}
