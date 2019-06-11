package com.example.jvmori.myweatherapp.di.modules;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.di.modules.main.MainFragmentBuildersModule;
import com.example.jvmori.myweatherapp.di.modules.main.WeatherViewModelsModule;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
            modules = {
                    WeatherViewModelsModule.class,
                    MainFragmentBuildersModule.class
            }
    )
    abstract MainActivity contributeMainActivity();
}
