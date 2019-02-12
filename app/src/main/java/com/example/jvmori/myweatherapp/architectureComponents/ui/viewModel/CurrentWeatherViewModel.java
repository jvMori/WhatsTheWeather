package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.WeatherRepository;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<CurrentWeather> currentWeatherLiveData;
    private Application application;

    public CurrentWeatherViewModel(@NonNull  Application application) {
        super(application);
        this.application = application;
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
        currentWeatherLiveData = new MutableLiveData<>();
    }
    public LiveData<CurrentWeatherResponse> getCurrentWeather(WeatherParameters weatherParameters, WeatherRepository.OnFailure onFailure)  {
        return weatherRepository.initWeatherData(weatherParameters, onFailure);
    }

    public LiveData<List<CurrentWeatherResponse>> getAllWeather(){
        return weatherRepository.getAllWeather();
    }

}
