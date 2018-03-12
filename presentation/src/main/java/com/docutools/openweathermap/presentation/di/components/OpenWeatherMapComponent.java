package com.docutools.openweathermap.presentation.di.components;

import com.docutools.openweathermap.presentation.OpenWeatherMapApplication;
import com.docutools.openweathermap.presentation.di.modules.ApplicationModule;
import com.docutools.openweathermap.presentation.di.modules.GsonModule;
import com.docutools.openweathermap.presentation.di.modules.WebServiceModule;
import com.docutools.openweathermap.presentation.di.modules.bindings.OpenWeatherMapBindingModule;
import com.docutools.openweathermap.presentation.di.scopes.ForApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@ForApplication
@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ApplicationModule.class,
                OpenWeatherMapBindingModule.class,
                WebServiceModule.class,
                GsonModule.class
        }
)
public interface OpenWeatherMapComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(OpenWeatherMapApplication application);

        Builder module(ApplicationModule module);

        OpenWeatherMapComponent build();
    }

    void inject(OpenWeatherMapApplication application);
}