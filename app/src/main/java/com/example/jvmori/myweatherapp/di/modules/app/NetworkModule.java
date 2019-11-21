package com.example.jvmori.myweatherapp.di.modules.app;

import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
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

    private static final String API_KEY = "7a5ba9d2d18041f38e0135842190602";
    private static final String BASE_URL = "http://api.apixu.com/v1/";

    @Provides
    @ApplicationScope
    public WeatherNetworkDataSource weatherNetworkDataSource(ApixuApi apixuApi) {
        return new WeatherNetworkDataSourceImpl(apixuApi);
    }

    @Provides
    @ApplicationScope
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
    public ApixuApi apixuApi(Retrofit retrofit){
        return retrofit.create(ApixuApi.class);
    }
}

