package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.ui.view.fragment.CustomWeatherFragment;
import com.example.jvmori.myweatherapp.ui.view.fragment.SearchFragment;
import com.example.jvmori.myweatherapp.ui.view.fragment.GeoWeatherFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract GeoWeatherFragment contributeGeoWeatherFragment();

    @ContributesAndroidInjector
    abstract CustomWeatherFragment contributeCustomWeatherFragment();


    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();
}
