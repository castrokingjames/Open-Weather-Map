package com.docutools.openweathermap.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.repositories.PlaceRepository;
import com.docutools.openweathermap.presentation.ui.mvvm.AbstractViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.disposables.Disposable;

public class SearchCityViewModel
        extends AbstractViewModel {

    private MutableLiveData<Notification<List<Place>>> city = new MutableLiveData<>();

    private PlaceRepository placeRepository;

    @Inject
    public SearchCityViewModel(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public void searchCity(String query) {
        Disposable disposable = placeRepository
                .search(query)
                .compose(this::scheduler)
                .materialize()
                .subscribe(notification -> city.setValue(notification));

        disposables.add(disposable);
    }

    public LiveData<Notification<List<Place>>> city() {
        return city;
    }
}