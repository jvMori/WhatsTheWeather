package com.example.jvmori.myweatherapp.di.component;

import com.example.jvmori.myweatherapp.di.module.AppModule;
import com.example.jvmori.myweatherapp.di.module.NetworkModule;
import com.example.jvmori.myweatherapp.di.module.RoomModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RoomModule.class})
public interface AppComponent {

}
