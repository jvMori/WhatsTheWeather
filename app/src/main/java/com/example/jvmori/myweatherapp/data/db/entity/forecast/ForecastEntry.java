
package com.example.jvmori.myweatherapp.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.data.db.entity.Location;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "forecast", indices = {@Index(value = {"mCityName"}, unique = true)})
public class ForecastEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public boolean isDeviceLocation;

    @SerializedName("current")
    @Embedded
    private CurrentWeather currentWeather;

    @SerializedName("location")
    @Embedded
    private Location mLocation;

    @SerializedName("forecast")
    @Embedded
    private Forecast mForecast;

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

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
