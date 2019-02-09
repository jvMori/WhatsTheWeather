package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import android.util.Log;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherNetworkDataSourceImpl  {

    private MutableLiveData<CurrentWeatherResponse> _currentWeather;

    public WeatherNetworkDataSourceImpl(){
        _currentWeather = new MutableLiveData<>();
    }

    public LiveData<CurrentWeatherResponse> currentWeather() { return _currentWeather; }

    public LiveData<CurrentWeatherResponse> fetchWeather(String location, String lang){
        _currentWeather = new MutableLiveData<>();
        ApixuApi service = ApixuApiCall.init();
        service.getCurrentWeather(location, lang).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (!response.isSuccessful()){
                    return;
                }
                if (response.body() != null)
                     _currentWeather.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Log.i("Error", "errror");
            }
        });
        return _currentWeather;
    }
}
