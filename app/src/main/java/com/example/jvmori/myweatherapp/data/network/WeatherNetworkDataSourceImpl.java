package com.example.jvmori.myweatherapp.data.network;

import android.util.Log;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource {

    private MutableLiveData<List<Search>> results;
    private ApixuApi service;

    public WeatherNetworkDataSourceImpl() {
        results = new MutableLiveData<>();
        service = ApixuApiCall.init();
    }

    @Override
    public Maybe<ForecastEntry> fetchWeather(WeatherParameters weatherParameters) {
        return service.getForecast(
                weatherParameters.getLocation(),
                weatherParameters.getLang(),
                weatherParameters.getDays());
    }

    @Override
    public LiveData<List<Search>> searchCity(String cityName) {
        service.searchCity(cityName).enqueue(new Callback<List<Search>>() {
            @Override
            public void onResponse(Call<List<Search>> call, Response<List<Search>> response) {
                if (!response.isSuccessful())
                    return;
                if (response.body() != null)
                    results.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Search>> call, Throwable t) {
                Log.i("Failed", "Failed to fetch data!");
            }
        });
        return results;
    }
}
