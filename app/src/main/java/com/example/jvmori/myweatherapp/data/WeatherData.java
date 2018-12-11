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
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeatherData
{
    private Locations locItem;

    public void getResponse(final WeatherAsyncResponse callback, final OnErrorResponse errorCallback, String location)
    {
        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", location);
        String url = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String city, tempUnit, currTemp, description, code;
                        String currMinTemp = "";
                        String currMaxTemp = "";
                        try {
                            JSONObject channel = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            JSONArray forecasts = item.getJSONArray("forecast");
                            JSONObject location = channel.getJSONObject("location");
                            tempUnit = channel.getJSONObject("units").getString("temperature");

                            //valid city name
                            if (location != null){
                                city = location.getString("city");
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
                                Calendar calendar = Calendar.getInstance();
                                long currTime = calendar.getTimeInMillis();
                                CurrentWeather currentWeather = new CurrentWeather(city, code, currTemp, description, currMinTemp, currMaxTemp);

                                locItem = new Locations();
                                locItem.setId(city);
                                locItem.setUpdateTime(currTime);
                                locItem.setCurrentWeather(currentWeather);
                                locItem.setForecasts(forecastArrayList);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if (callback != null && locItem != null) callback.processFinished(locItem);
                        else if (errorCallback != null) {
                            errorCallback.displayErrorMessage("Not valid city name!");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (errorCallback != null) errorCallback.displayErrorMessage(error.getMessage());
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
