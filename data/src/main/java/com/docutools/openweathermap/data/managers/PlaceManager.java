package com.docutools.openweathermap.data.managers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.repositories.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class PlaceManager
        implements PlaceRepository {

    private Geocoder geocoder;

    public PlaceManager(Context context) {
        this.geocoder = new Geocoder(context);
    }

    @Override
    public Observable<Place> geocode(double latitude, double longitude) {
        return Observable.create(e -> {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                Place place = new Place(address.getLocality(), address.getCountryCode());
                e.onNext(place);
            }

            e.onComplete();
        });
    }

    @Override
    public Observable<List<Place>> search(String query) {
        return Observable.create(e -> {
            List<Place> places = new ArrayList<>();
            List<Address> addresses = geocoder.getFromLocationName(query, 5);
            if (addresses.size() > 0) {
                for (Address address : addresses) {
                    if (address.getLocality() == null)
                        continue;

                    Place place = new Place(address.getLocality(), address.getCountryCode());
                    places.add(place);
                }
            }

            e.onNext(places);
            e.onComplete();
        });
    }
}