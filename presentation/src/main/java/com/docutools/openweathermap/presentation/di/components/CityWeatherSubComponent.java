package com.docutools.openweathermap.presentation.di.components;

import com.docutools.openweathermap.presentation.di.modules.LocationModule;
import com.docutools.openweathermap.presentation.di.modules.PlaceModule;
import com.docutools.openweathermap.presentation.di.modules.WeatherModule;
import com.docutools.openweathermap.presentation.di.modules.bindings.CityWeatherBindingModule;
import com.docutools.openweathermap.presentation.di.scopes.ForActivity;
import com.docutools.openweathermap.presentation.ui.activities.CityWeatherActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ForActivity
@Subcomponent(
        modules = {
                CityWeatherBindingModule.class,
                LocationModule.class,
                WeatherModule.class,
                PlaceModule.class
        }
)
public interface CityWeatherSubComponent
        extends AndroidInjector<CityWeatherActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CityWeatherActivity> {
    }
}