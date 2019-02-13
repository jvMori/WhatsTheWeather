package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeatherEntry;
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
    public LiveData<CurrentWeatherEntry> getCurrentWeather(WeatherParameters weatherParameters, WeatherRepository.OnFailure onFailure)  {
        return weatherRepository.initWeatherData(weatherParameters, onFailure);
    }

    public LiveData<List<CurrentWeatherEntry>> getWeather(){
        return weatherRepository.getWeatherExceptDeviceLoc();
    }

    public LiveData<List<CurrentWeatherEntry>> getAllWeather(){ return  weatherRepository.getAllWeather();}

}
