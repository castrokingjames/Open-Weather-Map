package com.docutools.openweathermap.presentation.ui.mvvm;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.docutools.openweathermap.presentation.ui.mvvm.delegate.FragmentMvvmDelegate;
import com.docutools.openweathermap.presentation.ui.mvvm.delegate.FragmentMvvmDelegateImpl;
import com.docutools.openweathermap.presentation.ui.mvvm.delegate.MvvmDelegateCallback;

public abstract class MvvmFragment<VM extends ViewModel>
        extends Fragment
        implements MvvmDelegateCallback<VM> {

    protected FragmentMvvmDelegate mvvmDelegate;
    protected VM viewModel;

    @NonNull
    protected FragmentMvvmDelegate<VM> getMvvmDelegate() {
        if (mvvmDelegate == null) {
            mvvmDelegate = new FragmentMvvmDelegateImpl(this, this);
        }

        return mvvmDelegate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvvmDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvvmDelegate().onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvvmDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvvmDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvvmDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvvmDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvvmDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvvmDelegate().onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvvmDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvvmDelegate().onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvvmDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvvmDelegate().onSaveInstanceState(outState);
    }

    @Override
    public VM getViewModel() {
        return viewModel;
    }

    @Override
    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}