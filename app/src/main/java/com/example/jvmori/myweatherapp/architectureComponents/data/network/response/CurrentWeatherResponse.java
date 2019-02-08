
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.Location;
import com.google.gson.annotations.SerializedName;

public class CurrentWeatherResponse {

    @SerializedName("current")
    private CurrentWeather mCurrentWeatherWeatherEntry;
    @SerializedName("location")
    private Location mLocation;

    public CurrentWeather getCurrent() {
        return mCurrentWeatherWeatherEntry;
    }

    public void setCurrent(CurrentWeather currentWeather) {
        mCurrentWeatherWeatherEntry = currentWeather;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

}
