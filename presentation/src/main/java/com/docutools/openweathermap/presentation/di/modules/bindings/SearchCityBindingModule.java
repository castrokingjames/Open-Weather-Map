package com.docutools.openweathermap.presentation.di.modules.bindings;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.docutools.openweathermap.presentation.di.keys.ViewModelKey;
import com.docutools.openweathermap.presentation.viewmodels.SearchCityViewModel;
import com.docutools.openweathermap.presentation.viewmodels.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SearchCityBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchCityViewModel.class)
    abstract ViewModel bindSearchCityViewModel(SearchCityViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}