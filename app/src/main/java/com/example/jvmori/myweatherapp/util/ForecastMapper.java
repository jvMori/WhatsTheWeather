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
                new ArrayList<>()
        );
    }

    public static List<ForecastEntity> mapForecasts(List<Forecast> forecastsResponse) {
        HashMap<String, ForecastEntity> forecastHashMap = new HashMap<>();
        List<String> days = new ArrayList<>();
        for (int i = 0; i < forecastsResponse.size(); i++) {
            groupForecastsByDays(forecastsResponse, forecastHashMap, days, i);
        }
        return preserveDaysOrder(forecastHashMap, days);
    }

    private static void groupForecastsByDays(List<Forecast> forecastsResponse, HashMap<String, ForecastEntity> forecastHashMap, List<String> days, int i) {
        String key = DateConverter.getDayOfWeek(forecastsResponse.get(i).getTimeText());
        if (forecastHashMap.containsKey(key)) {
           forecastHashMap.get(key).getForecastHourlyList().add(forecastHourly(forecastsResponse.get(i)));
        } else {
            days.add(key);
            forecastHashMap.put(key, forecastEntity(forecastsResponse.get(i)));
        }
    }

    private static List<ForecastEntity> preserveDaysOrder(HashMap<String, ForecastEntity> forecastHashMap, List<String> days) {
        List<ForecastEntity> result = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            result.add(forecastHashMap.get(days.get(i)));
        }
        return result;
    }

    private static ForecastHourly forecastHourly(Forecast forecast){
        return new ForecastHourly(
                forecast.getDescriptionList().get(0).getMain(),
                forecast.getDescriptionList().get(0).getDescription(),
                forecast.getDescriptionList().get(0).getIcon(),
                Integer.toString((int) Math.floor(forecast.getMain().getTemp())),
                forecast.getMain().getPressure().toString(),
                forecast.getMain().getHumidity().toString(),
                forecast.getClouds().toString(),
                forecast.getWind().getSpeed().toString(),
                forecast.getTimeText()
        );
    }
}
