package com.example.jvmori.myweatherapp.util;

import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.data.forecast.response.Forecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastMapper {

    private static ForecastEntity forecastEntity (Forecast response){
        return new ForecastEntity(
                response.getTimeText(),
                response.getDescriptionList().get(0).getIcon(),
                response.getMain().getTempMax().toString(),
                response.getMain().getTempMin().toString()
        );
    }

    public static List<ForecastEntity> mapForecasts(List<Forecast> forecastsResponse){
        List<ForecastEntity> forecasts = new ArrayList<>();
        for (int i = 0; i < forecastsResponse.size(); i++) {
            forecasts.add(
                   forecastEntity(forecastsResponse.get(i))
            );
        }
        return forecasts;
    }
}
