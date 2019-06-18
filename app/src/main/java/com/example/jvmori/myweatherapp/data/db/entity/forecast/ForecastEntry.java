
package com.example.jvmori.myweatherapp.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.data.db.entity.Location;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "forecast", primaryKeys = {"mCityName"})
public class ForecastEntry {
    public boolean isDeviceLocation;

    private Long timestamp;

    @SerializedName("current")
    @Embedded
    private CurrentWeather currentWeather;

    @SerializedName("location")
    @Embedded
    @NonNull
    private Location mLocation = new Location();

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
