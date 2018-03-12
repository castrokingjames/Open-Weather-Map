package com.docutools.openweathermap.domain.repositories;

import com.docutools.openweathermap.domain.entities.Place;

import java.util.List;

import io.reactivex.Observable;

public interface PlaceRepository {

    Observable<Place> geocode(double latitude, double longitude);

    Observable<List<Place>> search(String query);

}
