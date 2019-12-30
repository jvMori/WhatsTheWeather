package com.example.jvmori.myweatherapp.util;

import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.data.forecast.ForecastHourly;
import com.example.jvmori.myweatherapp.data.forecast.response.Forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForecastMapper {

    private static ForecastEntity forecastEntity(Forecast response) {
        return new ForecastEntity(
                DateConverter.getDayOfWeek(response.getTimeText()),
                response.getDescriptionList().get(0).getMain(),
                response.getDescriptionList().get(0).getDescription(),
                response.getDescriptionList().get(0).getIcon(),
                Integer.toString((int)Math.floor(response.getMain().getTemp())),
                response.getMain().getPressure().toString(),
                response.getMain().getHumidity().toString(),
                response.getClouds().toString(),
                response.getWind().getSpeed().toString(),
                new ArrayList<>()
        );
    }

    public static ArrayList mapForecasts(List<Forecast> forecastsResponse) {
        HashMap<String, ForecastEntity> forecastHashMap = new HashMap<>();
        for (int i = 0; i < forecastsResponse.size(); i++) {
            String key = DateConverter.getDayOfWeek(forecastsResponse.get(i).getTimeText());
            ForecastEntity value = forecastEntity(forecastsResponse.get(i));
            if (forecastHashMap.containsKey(key)) {
                forecastHashMap.get(key).getForecastHourlyList().add(new ForecastHourly(
                        forecastsResponse.get(i).getDescriptionList().get(0).getIcon(),
                        Double.toString(forecastsResponse.get(i).getMain().getTemp()),
                        forecastsResponse.get(i).getTimeText()
                ));
            } else {
                forecastHashMap.put(key, value);
            }
        }
        return new ArrayList<>(forecastHashMap.values());
    }
}
