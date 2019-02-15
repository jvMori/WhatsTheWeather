package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.SearchResponse;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource  {

    private MutableLiveData<SearchResponse> results;
    private ApixuApi service;

    public WeatherNetworkDataSourceImpl(){
        results = new MutableLiveData<>();
        service = ApixuApiCall.init();
    }

    @Override
    public Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters) {
        return service.getForecast(weatherParameters.getLocation(), weatherParameters.getLang(), weatherParameters.getDays());
    }

    @Override
    public LiveData<SearchResponse> searchCity(String cityName) {
        service.searchCity(cityName).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(!response.isSuccessful())
                    return;
                if(response.body() != null)
                    results.postValue(response.body());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
        return results;
    }
}
