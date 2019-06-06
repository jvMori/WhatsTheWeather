package com.example.jvmori.myweatherapp.di_.modules

import com.example.jvmori.myweatherapp.MainActivity
import com.example.jvmori.myweatherapp.di_.modules.main.WeatherViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = [WeatherViewModelsModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}