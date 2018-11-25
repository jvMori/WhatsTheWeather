package com.example.jvmori.myweatherapp.data;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jvmori.myweatherapp.controller.AppController;
import com.example.jvmori.myweatherapp.model.CurrentWeather;
import com.example.jvmori.myweatherapp.model.Forecast;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherData
{
    private Locations locItem;

    public void getResponse(final WeatherAsyncResponse callback, String location)
    {
        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", location);
        String url = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String location, tempUnit, currTemp, description, code;
                        String currMinTemp = "";
                        String currMaxTemp = "";
                        try {
                            JSONObject channel = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            JSONArray forecasts = item.getJSONArray("forecast");

                            tempUnit = channel.getJSONObject("units").getString("temperature");

                            location = channel.getJSONObject("location").getString("city");
                            currTemp =condition.getString("temp");
                            description = condition.getString("text");
                            code = condition.getString("code");

                            ArrayList<Forecast> forecastArrayList = new ArrayList<>();
                            for (int i = 0; i < forecasts.length(); i++) {
                                JSONObject forecast = forecasts.getJSONObject(i);
                                String date, foreCode, day, tempHigh, tempLow, desc;
                                if (i <= 0){
                                    currMinTemp = forecast.getString("low");
                                    currMaxTemp = forecast.getString("high");
                                }else{
                                    date = forecast.getString("date");
                                    foreCode = forecast.getString("code");
                                    day = forecast.getString("day");
                                    tempHigh = forecast.getString("high");
                                    tempLow = forecast.getString("low");
                                    desc = forecast.getString("text");
                                    Forecast forecastItem = new Forecast(date, foreCode, day, tempHigh, tempLow, desc);
                                    forecastArrayList.add(forecastItem);
                                }
                            }

                            CurrentWeather currentWeather = new CurrentWeather(location, code, currTemp, description, currMinTemp, currMaxTemp);

                            locItem = new Locations();
                            locItem.setId(location);
                            locItem.setCurrentWeather(currentWeather);
                            locItem.setForecasts(forecastArrayList);

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        if (callback != null) callback.processFinished(locItem);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public int containsName(final List<Locations> list, final String name){
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null && list.get(i).getId().equals(name))
                    return i;
            }
        }
        return -1;
    }

}
