package com.docutools.openweathermap.domain.repositories;

import com.docutools.openweathermap.domain.entities.Location;

import io.reactivex.Observable;

public interface LocationRepository {

    Observable<Location> loadLocation();

}