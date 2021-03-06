package com.example.jvmori.myweatherapp.di.modules.main;

import android.content.Context;
import android.location.LocationManager;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItem;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItemOnSwipe;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @MainActivityScope
    public DeleteLocationItem provideDeleteLocationItem(LocationAdapter locationAdapter) {
        return new DeleteLocationItemOnSwipe(locationAdapter);
    }

    @Provides
    @MainActivityScope
    public LocationAdapter locationAdapter() {
        return new LocationAdapter();
    }

    @Provides
    @MainActivityScope
    public LocationManager provideLocationManager(MainActivity mainActivity) {
        return (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @MainActivityScope
    FusedLocationProviderClient fusedLocationProviderClient(MainActivity mainActivity) {
        return LocationServices.getFusedLocationProviderClient(mainActivity);
    }

    @Provides
    @MainActivityScope
    public LocationRequest provideLocationRequest() {
        return new LocationRequest()
                .setInterval(1000 * 15)
                .setFastestInterval(1000 * 15)
                .setSmallestDisplacement(500) //5km
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

}
