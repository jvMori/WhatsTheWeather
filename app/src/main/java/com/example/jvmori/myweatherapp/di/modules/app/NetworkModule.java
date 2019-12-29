package com.example.jvmori.myweatherapp.di.modules.app;

import com.example.jvmori.myweatherapp.data.network.Api;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String API_KEY = "de9ae73d4c60863925bc392529501ab7";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String UNITS = "metric";


    @Provides
    @ApplicationScope
    public Interceptor interceptor() {
        return chain -> {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("units", UNITS)
                    .addQueryParameter("appid", API_KEY)
                    .build();
            Request request = chain.request()
                    .newBuilder().url(url).build();

            return chain.proceed(request);
        };
    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Provides
    @ApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public Api apixuApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }
}

