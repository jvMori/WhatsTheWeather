package com.example.jvmori.myweatherapp.di.modules.app;

import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Module
public class NetworkModule {

    private static final String API_KEY = "8b83631c9a25b07bccfd9329894c4311";

    @Provides
    @ApplicationScope
    public Interceptor interceptor() {
        return chain -> {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
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
}

