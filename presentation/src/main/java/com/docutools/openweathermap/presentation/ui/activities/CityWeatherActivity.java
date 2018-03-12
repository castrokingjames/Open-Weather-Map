package com.docutools.openweathermap.presentation.ui.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.presentation.R;
import com.docutools.openweathermap.presentation.ui.fragments.DaytimeFragment;
import com.docutools.openweathermap.presentation.ui.fragments.TodayWeatherFragment;
import com.docutools.openweathermap.presentation.ui.fragments.WeatherDetailFragment;
import com.docutools.openweathermap.presentation.ui.mvvm.MvvmActivity;
import com.docutools.openweathermap.presentation.viewmodels.CityWeatherViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Notification;

public class CityWeatherActivity
        extends MvvmActivity<CityWeatherViewModel> {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    private View loadingView;
    private View contentView;
    private View errorView;
    private TodayWeatherFragment todayWeatherFragment;
    private DaytimeFragment daytimeFragment;
    private WeatherDetailFragment weatherDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        if (!getResources().getBoolean(R.bool.IS_TABLET)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_root);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        loadingView = findViewById(R.id.loading);
        contentView = findViewById(R.id.content);
        errorView = findViewById(R.id.error);

        viewModel
                .weather()
                .observe(this, this::onLoadWeather);

        todayWeatherFragment = new TodayWeatherFragment();
        daytimeFragment = new DaytimeFragment();
        weatherDetailFragment = new WeatherDetailFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.daytime_container, daytimeFragment)
                .commit();

        fragmentManager
                .beginTransaction()
                .replace(R.id.today_weather_container, todayWeatherFragment)
                .commit();

        fragmentManager
                .beginTransaction()
                .replace(R.id.weather_detail_container, weatherDetailFragment)
                .commit();

        toolbar.setNavigationOnClickListener(v -> finish());

        if (savedInstanceState == null) {

            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);

            Intent intent = getIntent();
            Place place = (Place) intent.getSerializableExtra(Place.class.getSimpleName());

            viewModel.load(place);
        }
    }

    private void onLoadWeather(Notification<Weather> notification) {
        if (notification.isOnNext()) {

            Weather weather = notification.getValue();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Weather.class.getSimpleName(), weather);

            todayWeatherFragment.setArguments(bundle);
            todayWeatherFragment.show();

            daytimeFragment.setArguments(bundle);
            daytimeFragment.show();

            weatherDetailFragment.setArguments(bundle);
            weatherDetailFragment.show();

            loadingView.setVisibility(View.INVISIBLE);
            contentView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
        } else if (notification.isOnError()) {
            Throwable throwable = notification.getError();
            TextView errorTextView = errorView.findViewById(R.id.error_text_view);
            errorTextView.setText(throwable.getMessage());

            loadingView.setVisibility(View.INVISIBLE);
            contentView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public CityWeatherViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(CityWeatherViewModel.class);
    }

    public static Intent newIntent(Context context, Place place) {
        Intent intent = new Intent(context, CityWeatherActivity.class);
        intent.putExtra(Place.class.getSimpleName(), place);
        return intent;
    }
}
