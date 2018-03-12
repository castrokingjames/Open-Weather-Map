package com.docutools.openweathermap.presentation.ui.mvvm.delegate;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

public interface ActivityMvvmDelegate<VM extends ViewModel> {

    void onCreate(Bundle bundle);

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    void onRestart();

    void onContentChanged();

    void onSaveInstanceState(Bundle outState);

    void onPostCreate(Bundle savedInstanceState);
}
