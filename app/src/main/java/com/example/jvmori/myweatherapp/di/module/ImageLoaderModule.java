package com.example.jvmori.myweatherapp.di.module;

import android.content.Context;

import com.example.jvmori.myweatherapp.di.scope.WeatherApplicationScope;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;
import com.example.jvmori.myweatherapp.util.images.PicassoImgLoader;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {ContextModule.class, NetworkModule.class})
public class ImageLoaderModule
{
    @Provides
    public ILoadImage provideImageLoader(Picasso picasso){
        return new PicassoImgLoader(picasso);
    }

    @Provides
    @WeatherApplicationScope
    public Picasso picasso(Context context, OkHttpClient okHttpClient){
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
