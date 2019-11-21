package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.ui.view.fragment.SearchFragment;
import com.example.jvmori.myweatherapp.ui.view.fragment.WeatherFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract WeatherFragment contributeWeatherFragment();


    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();
}
