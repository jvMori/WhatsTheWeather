package com.example.jvmori.myweatherapp.di.modules;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.di.modules.main.WeatherViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
            modules = {WeatherViewModelsModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
