package com.docutools.openweathermap.presentation.ui.mvvm.delegate;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public interface FragmentMvvmDelegate<VM extends ViewModel> {

  void onCreate(Bundle saved);

  void onDestroy();

  void onViewCreated(View view, @Nullable Bundle savedInstanceState);

  void onDestroyView();

  void onPause();

  void onResume();

  void onStart();

  void onStop();

  void onActivityCreated(Bundle savedInstanceState);

  void onAttach(Activity activity);

  void onDetach();

  void onSaveInstanceState(Bundle outState);
}
