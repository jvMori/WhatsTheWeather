package com.example.jvmori.myweatherapp.di.module;

import android.content.Context;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.scope.WeatherApplicationScope;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiApixuModule.class, ContextModule.class})
public class DataSourceModule {

    @Provides
    @WeatherApplicationScope
    public WeatherRepository weatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao) {
        return new WeatherRepository(weatherNetworkDataSource, forecastDao);
    }

    @Provides
    @WeatherApplicationScope
    public WeatherNetworkDataSource weatherNetworkDataSource(ApixuApi apixuApi) {
        return new WeatherNetworkDataSourceImpl(apixuApi);
    }

    @Provides
    @WeatherApplicationScope
    public ForecastDao forecastDao(WeatherDatabase weatherDatabase) {
        return weatherDatabase.forecastDao();
    }

    @Provides
    @WeatherApplicationScope
    public WeatherDatabase weatherDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
