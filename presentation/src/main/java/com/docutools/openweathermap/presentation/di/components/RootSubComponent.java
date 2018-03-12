package com.docutools.openweathermap.presentation.di.components;

import com.docutools.openweathermap.presentation.di.modules.LocationModule;
import com.docutools.openweathermap.presentation.di.modules.PlaceModule;
import com.docutools.openweathermap.presentation.di.modules.WeatherModule;
import com.docutools.openweathermap.presentation.di.modules.bindings.RootBindingModule;
import com.docutools.openweathermap.presentation.di.scopes.ForActivity;
import com.docutools.openweathermap.presentation.ui.activities.RootActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ForActivity
@Subcomponent(
        modules = {
                RootBindingModule.class,
                LocationModule.class,
                WeatherModule.class,
                PlaceModule.class
        }
)
public interface RootSubComponent
        extends AndroidInjector<RootActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RootActivity> {
    }
}