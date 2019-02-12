package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;

    public CurrentWeatherViewModel(@NonNull  Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
    }
    public LiveData<CurrentWeatherResponse> getCurrentWeather(WeatherParameters weatherParameters, WeatherRepository.OnFailure onFailure)  {
        return weatherRepository.initWeatherData(weatherParameters, onFailure);
    }

    public LiveData<List<CurrentWeatherResponse>> getWeather(){
        return weatherRepository.getWeatherExceptDeviceLoc();
    }

    public LiveData<List<CurrentWeatherResponse>> getAllWeather(){ return  weatherRepository.getAllWeather();}

}
