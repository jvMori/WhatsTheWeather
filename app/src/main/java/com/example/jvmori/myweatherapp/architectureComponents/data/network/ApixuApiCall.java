package com.example.jvmori.myweatherapp.architectureComponents.data.network;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//http://api.apixu.com/v1/current.json?key=7a5ba9d2d18041f38e0135842190602&q=Paris&lang=en

public class ApixuApiCall
{
    private static final String API_KEY = "7a5ba9d2d18041f38e0135842190602";
    private static final String BASE_URL = "http://api.apixu.com/v1/";

    final static Interceptor interceptor = chain -> {
        HttpUrl url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build();
        Request request = chain.request()
                .newBuilder().url(url).build();
        return chain.proceed(request);
    };

    public static ApixuApi init(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuApi.class);
    }
}
