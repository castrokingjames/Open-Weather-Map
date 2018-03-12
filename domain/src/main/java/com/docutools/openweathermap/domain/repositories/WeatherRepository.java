package com.docutools.openweathermap.domain.repositories;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.entities.Weather;

import io.reactivex.Observable;

public interface WeatherRepository {

    Observable<Weather> loadWeather();

    Observable<Weather> loadWeather(Place place);
}