package com.docutools.openweathermap.presentation.ui.mvvm.delegate;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public interface MvvmDelegateCallback<VM extends ViewModel> {

    @NonNull
    VM createViewModel();

    VM getViewModel();

    void setViewModel(VM viewModel);
}