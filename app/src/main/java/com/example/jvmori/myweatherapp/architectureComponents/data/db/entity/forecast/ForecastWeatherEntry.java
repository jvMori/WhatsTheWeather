
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.Location;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "forecast", indices = {@Index(value = {"mDate"}, unique = true)})
public class ForecastWeatherEntry {

    @SerializedName("forecast")
    @Embedded
    private Forecast mForecast;

    @SerializedName("location")
    @Embedded
    private Location mLocation;

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
