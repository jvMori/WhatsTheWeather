package com.example.jvmori.myweatherapp.di.modules.app;

import android.app.Application;

import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;
import com.example.jvmori.myweatherapp.util.images.PicassoImgLoader;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {NetworkModule.class})
public class ImageLoaderModule
{
    @Provides
    public ILoadImage provideImageLoader(Picasso picasso){
        return new PicassoImgLoader(picasso);
    }

    @Provides
    @ApplicationScope
    public Picasso picasso(Application context, OkHttpClient okHttpClient){
        return new Picasso.Builder(context.getApplicationContext())
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
