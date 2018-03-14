package com.docutools.openweathermap.presentation.di.modules;

import android.content.Context;

import com.docutools.openweathermap.data.managers.PlaceManager;
import com.docutools.openweathermap.domain.repositories.PlaceRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceModule {

    @Provides
    public PlaceRepository providesPlaceRepository(Context context) {
        return new PlaceManager(context);
    }
}