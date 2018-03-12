package com.docutools.openweathermap.presentation.ui.mvvm.delegate;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public class FragmentMvvmDelegateImpl<VM extends ViewModel>
        implements FragmentMvvmDelegate<VM> {

    private Fragment fragment;
    private MvvmDelegateCallback<VM> delegateCallback;

    public FragmentMvvmDelegateImpl(Fragment fragment, MvvmDelegateCallback<VM> delegateCallback) {
        if (fragment == null) {
            throw new NullPointerException("Fragment is null!");
        }

        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.fragment = fragment;
        this.delegateCallback = delegateCallback;
    }


    @Override
    public void onCreate(Bundle saved) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        VM vm = delegateCallback.createViewModel();
        delegateCallback.setViewModel(vm);
    }

    @Override
    public void onDestroyView() {

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
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Activity activity) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
