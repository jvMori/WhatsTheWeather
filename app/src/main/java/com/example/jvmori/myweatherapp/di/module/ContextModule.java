package com.example.jvmori.myweatherapp.di.module;

import android.content.Context;

import com.example.jvmori.myweatherapp.di.scope.WeatherApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule
{
    private final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    @WeatherApplicationScope
    public Context getContext(){
        return context;
    }
}
