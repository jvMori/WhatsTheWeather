package com.example.jvmori.myweatherapp.di.module;

import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.di.scope.WeatherApplicationScope;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ApiApixuModule {

    private static final String BASE_URL = "http://api.apixu.com/v1/";

    @Provides
    @WeatherApplicationScope
    public ApixuApi apixuApi(Retrofit retrofit){
        return retrofit.create(ApixuApi.class);
    }

    @Provides
    @WeatherApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
