package com.example.jvmori.myweatherapp.application;

import android.app.Application;

import com.example.jvmori.myweatherapp.di.component.AppComponent;
import com.example.jvmori.myweatherapp.di.component.DaggerAppComponent;
import com.example.jvmori.myweatherapp.di.module.AppModule;
import com.example.jvmori.myweatherapp.di.module.RoomModule;

public class WeatherApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

    private AppComponent initDagger(Application app){
        return  DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .roomModule(new RoomModule(app))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
