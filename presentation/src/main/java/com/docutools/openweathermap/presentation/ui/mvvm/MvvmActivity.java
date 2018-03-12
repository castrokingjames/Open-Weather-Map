package com.docutools.openweathermap.presentation.ui.mvvm;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.docutools.openweathermap.presentation.ui.mvvm.delegate.ActivityMvvmDelegate;
import com.docutools.openweathermap.presentation.ui.mvvm.delegate.ActivityMvvmDelegateImpl;
import com.docutools.openweathermap.presentation.ui.mvvm.delegate.MvvmDelegateCallback;

public abstract class MvvmActivity<VM extends ViewModel>
        extends AppCompatActivity
        implements MvvmDelegateCallback<VM> {

    protected ActivityMvvmDelegate mvvmDelegate;
    protected VM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvvmDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvvmDelegate().onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvvmDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvvmDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvvmDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvvmDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvvmDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvvmDelegate().onRestart();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvvmDelegate().onContentChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvvmDelegate().onPostCreate(savedInstanceState);
    }

    @NonNull
    protected ActivityMvvmDelegate<VM> getMvvmDelegate() {
        if (mvvmDelegate == null) {
            mvvmDelegate = new ActivityMvvmDelegateImpl(this, this);
        }

        return mvvmDelegate;
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