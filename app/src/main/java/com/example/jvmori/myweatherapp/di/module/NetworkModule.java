package com.example.jvmori.myweatherapp.di.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Module
public class NetworkModule {

    private static final String API_KEY = "7a5ba9d2d18041f38e0135842190602";

    @Provides
    public Interceptor interceptor() {
        return chain -> {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build();
            Request request = chain.request()
                    .newBuilder().url(url).build();

            return chain.proceed(request);
        };
    }

    @Provides
    public OkHttpClient okHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
    }
}

