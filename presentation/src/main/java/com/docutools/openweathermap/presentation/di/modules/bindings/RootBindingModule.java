package com.docutools.openweathermap.presentation.di.modules.bindings;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.docutools.openweathermap.presentation.di.keys.ViewModelKey;
import com.docutools.openweathermap.presentation.viewmodels.RootViewModel;
import com.docutools.openweathermap.presentation.viewmodels.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RootBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(RootViewModel.class)
    abstract ViewModel bindRootViewModel(RootViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}