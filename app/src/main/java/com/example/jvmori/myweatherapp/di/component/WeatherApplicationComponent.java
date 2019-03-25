package com.example.jvmori.myweatherapp.di.component;

import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.module.DataSourceModule;
import com.example.jvmori.myweatherapp.di.module.ImageLoaderModule;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import dagger.Component;

@Component(modules = {DataSourceModule.class, ImageLoaderModule.class})
public interface WeatherApplicationComponent
{
    WeatherRepository weatherRepository();
    ILoadImage imageLoader();
}
