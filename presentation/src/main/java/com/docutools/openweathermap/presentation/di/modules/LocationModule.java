package com.docutools.openweathermap.presentation.di.modules;

import android.content.Context;

import com.docutools.openweathermap.data.managers.LocationManager;
import com.docutools.openweathermap.domain.repositories.LocationRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    public LocationRepository providesLocationRepository(Context context) {
        return new LocationManager(context);
    }
}