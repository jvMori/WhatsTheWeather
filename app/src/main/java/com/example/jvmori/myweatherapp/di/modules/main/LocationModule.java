package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.LocationProviderImpl;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule  {

    @ApplicationScope
    @Provides
    public LocationProvider provideLocation(){
        return new LocationProviderImpl();
    }
}
