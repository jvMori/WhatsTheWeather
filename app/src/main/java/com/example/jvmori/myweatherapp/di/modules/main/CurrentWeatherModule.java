package com.example.jvmori.myweatherapp.di.modules.main;

import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepositoryImpl;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.network.Api;
import com.example.jvmori.myweatherapp.di.ViewModelKey;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class CurrentWeatherModule {

    @Provides
    @MainActivityScope
    public CurrentWeatherNetworkDataSource currentWeatherNetworkDataSource(Api api) {
        return new CurrentWeatherNetworkDataSourceImpl(api);
    }

    @Provides
    @MainActivityScope
    public  CurrentWeatherRepository currentWeatherRepository (Api api, CurrentWeatherDao currentWeatherDao){
        return new CurrentWeatherRepositoryImpl(api, currentWeatherDao);
    }

    @Provides
    @MainActivityScope
    public CurrentWeatherDao currentWeatherDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.currentWeatherDao();
    }

}
