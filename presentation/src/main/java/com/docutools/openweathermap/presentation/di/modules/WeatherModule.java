package com.docutools.openweathermap.presentation.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.docutools.canvass.data.clients.network.services.WeatherService;
import com.docutools.canvass.data.clients.preference.Preference;
import com.docutools.canvass.data.clients.preference.WeatherPreference;
import com.docutools.canvass.data.managers.WeatherManager;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.domain.repositories.LocationRepository;
import com.docutools.openweathermap.domain.repositories.PlaceRepository;
import com.docutools.openweathermap.domain.repositories.WeatherRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    public WeatherRepository providesWeatherRepository(LocationRepository locationRepository,
                                                       PlaceRepository placeRepository,
                                                       WeatherService weatherService,
                                                       Preference<Weather> preference) {
        return new WeatherManager(locationRepository, placeRepository, weatherService, preference);
    }

    @Provides
    public Preference<Weather> providesPreference(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return new WeatherPreference(sharedPreferences);
    }
}