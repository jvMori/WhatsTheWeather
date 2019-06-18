package com.example.jvmori.myweatherapp.di.component;

import android.app.Application;
import com.example.jvmori.myweatherapp.application.BaseApplication;
import com.example.jvmori.myweatherapp.di.modules.ActivityBuildersModule;
import com.example.jvmori.myweatherapp.di.modules.ViewModelFactoryModule;
import com.example.jvmori.myweatherapp.di.modules.app.DataSourceModule;
import com.example.jvmori.myweatherapp.di.modules.app.ImageLoaderModule;
import com.example.jvmori.myweatherapp.di.modules.main.MainFragmentBuildersModule;
import com.example.jvmori.myweatherapp.di.modules.main.WeatherFragmentModule;
import com.example.jvmori.myweatherapp.di.modules.main.WeatherViewModelsModule;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                DataSourceModule.class,
                ImageLoaderModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
        @Component.Builder
        interface Builder{
                AppComponent build();
                @BindsInstance
                Builder application(Application application);
        }
}
