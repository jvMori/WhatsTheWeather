package com.example.jvmori.myweatherapp.application;

import android.app.Application;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.component.DaggerWeatherApplicationComponent;
import com.example.jvmori.myweatherapp.di.component.WeatherApplicationComponent;
import com.example.jvmori.myweatherapp.di.module.ContextModule;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

public class WeatherApplication extends Application
{
    private WeatherRepository weatherRepository;
    private ILoadImage iLoadImage;

    @Override
    public void onCreate() {
        super.onCreate();
        WeatherApplicationComponent component = DaggerWeatherApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        weatherRepository = component.weatherRepository();
        iLoadImage = component.imageLoader();
    }

    public WeatherRepository weatherRepository(){
        return weatherRepository;
    }
    public ILoadImage imageLoader(){return iLoadImage;}
}
