
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import java.util.List;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.FutureWeatherEntry;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("forecastday")
    private List<FutureWeatherEntry> mFutureWeatherEntry;

    public List<FutureWeatherEntry> getForecastday() {
        return mFutureWeatherEntry;
    }

    public void setForecastday(List<FutureWeatherEntry> futureWeatherEntry) {
        mFutureWeatherEntry = futureWeatherEntry;
    }

}
