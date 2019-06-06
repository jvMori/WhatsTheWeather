package com.example.jvmori.myweatherapp.di.modules.app;

import android.app.Application;

import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiApixuModule.class})
public class DataSourceModule {

    @Provides
    @ApplicationScope
    public WeatherRepository weatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao) {
        return new WeatherRepository(weatherNetworkDataSource, forecastDao);
    }

    @Provides
    @ApplicationScope
    public WeatherNetworkDataSource weatherNetworkDataSource(ApixuApi apixuApi) {
        return new WeatherNetworkDataSourceImpl(apixuApi);
    }

    @Provides
    @ApplicationScope
    public ForecastDao forecastDao(WeatherDatabase weatherDatabase) {
        return weatherDatabase.forecastDao();
    }

    @Provides
    @ApplicationScope
    public WeatherDatabase weatherDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
