package com.docutools.openweathermap.presentation.ui.mvvm.delegate;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

public class ActivityMvvmDelegateImpl<VM extends ViewModel>
        implements ActivityMvvmDelegate {

    private Activity activity;
    private MvvmDelegateCallback<VM> delegateCallback;

    public ActivityMvvmDelegateImpl(Activity activity, MvvmDelegateCallback<VM> delegateCallback) {
        if (activity == null) {
            throw new NullPointerException("Activity is null!");
        }

        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.activity = activity;
        this.delegateCallback = delegateCallback;
    }

    @Override
    public void onCreate(Bundle bundle) {
        VM vm = delegateCallback.createViewModel();
        delegateCallback.setViewModel(vm);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }
}