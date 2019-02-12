
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.Location;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_weather", indices = {@Index(value = {"mCityName", "isDeviceLocation"}, unique = true)})
public class CurrentWeatherResponse {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private boolean isDeviceLocation;

    @SerializedName("current")
    @Embedded
    public CurrentWeather mCurrentWeatherWeatherEntry;

    @SerializedName("location")
    @Embedded
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

    public boolean isDeviceLocation() {
        return isDeviceLocation;
    }

    public void setDeviceLocation(boolean deviceLocation) {
        isDeviceLocation = deviceLocation;
    }
}
