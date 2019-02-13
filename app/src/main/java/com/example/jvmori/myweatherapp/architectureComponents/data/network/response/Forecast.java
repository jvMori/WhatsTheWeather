
package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("forecastday")
    private List<Forecastday> mForecastday;

    public List<Forecastday> getForecastday() {
        return mForecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        mForecastday = forecastday;
    }

}
