package com.docutools.openweathermap.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.docutools.openweathermap.domain.entities.Location;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.domain.repositories.LocationRepository;
import com.docutools.openweathermap.domain.repositories.WeatherRepository;
import com.docutools.openweathermap.presentation.ui.mvvm.AbstractViewModel;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.disposables.Disposable;

public class RootViewModel
        extends AbstractViewModel {

    private MutableLiveData<Notification<Weather>> weather = new MutableLiveData<>();

    private WeatherRepository weatherRepository;

    @Inject
    public RootViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void load() {
        Disposable disposable = weatherRepository
                .loadWeather()
                .compose(this::scheduler)
                .materialize()
                .subscribe(notification -> weather.setValue(notification));

        disposables.add(disposable);
    }

    public LiveData<Notification<Weather>> weather() {
        return weather;
    }
}