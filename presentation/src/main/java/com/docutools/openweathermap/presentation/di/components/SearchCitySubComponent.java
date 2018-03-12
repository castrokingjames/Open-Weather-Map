package com.docutools.openweathermap.presentation.di.components;

import com.docutools.openweathermap.presentation.di.modules.PlaceModule;
import com.docutools.openweathermap.presentation.di.modules.bindings.SearchCityBindingModule;
import com.docutools.openweathermap.presentation.di.scopes.ForActivity;
import com.docutools.openweathermap.presentation.ui.activities.SearchCityActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ForActivity
@Subcomponent(
        modules = {
                SearchCityBindingModule.class,
                PlaceModule.class
        }
)
public interface SearchCitySubComponent
        extends AndroidInjector<SearchCityActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SearchCityActivity> {
    }
}