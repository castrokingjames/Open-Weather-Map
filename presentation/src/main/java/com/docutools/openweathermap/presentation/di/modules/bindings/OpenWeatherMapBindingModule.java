package com.docutools.openweathermap.presentation.di.modules.bindings;

import android.app.Activity;

import com.docutools.openweathermap.presentation.di.components.CityWeatherSubComponent;
import com.docutools.openweathermap.presentation.di.components.RootSubComponent;
import com.docutools.openweathermap.presentation.di.components.SearchCitySubComponent;
import com.docutools.openweathermap.presentation.ui.activities.CityWeatherActivity;
import com.docutools.openweathermap.presentation.ui.activities.RootActivity;
import com.docutools.openweathermap.presentation.ui.activities.SearchCityActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
        subcomponents = {
                RootSubComponent.class,
                SearchCitySubComponent.class,
                CityWeatherSubComponent.class
        }
)
public abstract class OpenWeatherMapBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(RootActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindRootActivityInjectorFactory(RootSubComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(SearchCityActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSearchCityActivityInjectorFactory(SearchCitySubComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(CityWeatherActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindCityWeatherActivityInjectorFactory(CityWeatherSubComponent.Builder builder);

}