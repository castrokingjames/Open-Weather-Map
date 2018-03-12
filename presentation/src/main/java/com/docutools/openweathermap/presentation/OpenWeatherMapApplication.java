package com.docutools.openweathermap.presentation;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.docutools.openweathermap.presentation.di.components.DaggerOpenWeatherMapComponent;
import com.docutools.openweathermap.presentation.di.modules.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;

public class OpenWeatherMapApplication
        extends Application
        implements HasActivityInjector {

    @Inject
    protected DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerOpenWeatherMapComponent
                .builder()
                .application(this)
                .module(new ApplicationModule(this))
                .build()
                .inject(this);

        RxJavaPlugins.setErrorHandler(e -> Log.e("OpenWeatherMap", e.getMessage()));
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}