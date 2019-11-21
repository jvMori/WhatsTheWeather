package com.example.jvmori.myweatherapp.data.current;

import androidx.lifecycle.LiveData;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Maybe;
import retrofit2.http.Query;

public interface CurrentWeatherRepository {
    LiveData<CurrentWeatherUI> getCurrentWeatherByCity(@Query("q") String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);
}
