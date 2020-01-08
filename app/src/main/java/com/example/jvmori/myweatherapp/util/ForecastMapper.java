package com.example.jvmori.myweatherapp.util;

import com.example.jvmori.myweatherapp.data.forecast.ForecastDetails;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.data.forecast.response.Forecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForecastMapper {

    private static ForecastEntity forecastEntity(Forecast response) {
        return new ForecastEntity(
                DateConverter.getDayOfWeek(response.getTimeText()),
                null,
                "1",
                "-1",
                new ArrayList<>()
        );
    }

    public static List<ForecastEntity> mapForecasts(List<Forecast> forecastsResponse) {
        HashMap<String, ForecastEntity> forecastHashMap = new HashMap<>();
        List<String> days = new ArrayList<>();
        for (int i = 0; i < forecastsResponse.size(); i++) {
            groupForecastsByDays(forecastsResponse, forecastHashMap, days, i);
        }
        List<ForecastEntity> results = preserveDaysOrder(forecastHashMap, days);
        setForecastMainInfo(results);
        return results;
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

    private static void setForecastMainInfo(List<ForecastEntity> forecasts) {
        for (int i = 0; i < forecasts.size(); i++) {
            List<ForecastDetails> details = forecasts.get(i).getForecastHourlyList();
            double minTemp = Float.POSITIVE_INFINITY;
            double maxTemp = Float.NEGATIVE_INFINITY;
            int maxIndex = -1;
            for (int j = 0; j < details.size(); j++) {
                if (details.get(j).getMainTemp() > maxTemp) {
                    maxTemp = details.get(j).getMainTemp();
                    maxIndex = j;
                }
                if (details.get(j).getMainTemp() < minTemp) {
                    minTemp = details.get(j).getMainTemp();
                }
            }

            forecasts.get(i).setMaxTemp(Integer.toString((int) Math.floor(maxTemp)));
            forecasts.get(i).setMinTemp(Integer.toString((int) Math.floor(minTemp)));
            if (details.size() > 0 && maxIndex > -1)
                forecasts.get(i).setIconUrl(details.get(maxIndex).getIconUrl());
        }
    }

    private static List<ForecastEntity> preserveDaysOrder(HashMap<String, ForecastEntity> forecastHashMap, List<String> days) {
        List<ForecastEntity> result = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            result.add(forecastHashMap.get(days.get(i)));
        }
        return result;
    }

    private static ForecastDetails forecastHourly(Forecast forecast) {
        return new ForecastDetails(
                forecast.getDescriptionList().get(0).getMain(),
                forecast.getDescriptionList().get(0).getDescription(),
                forecast.getDescriptionList().get(0).getIcon(),
                forecast.getMain().getTemp(),
                forecast.getMain().getPressure().toString(),
                forecast.getMain().getHumidity().toString(),
                forecast.getClouds().toString(),
                forecast.getWind().getSpeed().toString(),
                forecast.getTimeText()
        );
    }
}
