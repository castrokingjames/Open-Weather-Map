package com.docutools.openweathermap.presentation.ui.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.docutools.openweathermap.data.lang.PermissionRequiredException;
import com.docutools.openweathermap.data.lang.Permissions;
import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.presentation.R;
import com.docutools.openweathermap.presentation.ui.fragments.DaytimeFragment;
import com.docutools.openweathermap.presentation.ui.fragments.TodayWeatherFragment;
import com.docutools.openweathermap.presentation.ui.fragments.WeatherDetailFragment;
import com.docutools.openweathermap.presentation.ui.mvvm.MvvmActivity;
import com.docutools.openweathermap.presentation.ui.views.AppSettingsDialog;
import com.docutools.openweathermap.presentation.viewmodels.RootViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Notification;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RootActivity
        extends MvvmActivity<RootViewModel> {

    private final int REQUEST_PERMISSION = 0x000001;
    private final int REQUEST_RESOLUTION = 0x000002;

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

        if (savedInstanceState == null) {

            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);

            viewModel.load();
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
            if (throwable instanceof ApiException) {
                ResolvableApiException exception = (ResolvableApiException) throwable;
                try {
                    exception.startResolutionForResult(this, REQUEST_RESOLUTION);
                } catch (IntentSender.SendIntentException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (throwable instanceof PermissionRequiredException) {
                PermissionRequiredException exception = (PermissionRequiredException) throwable;
                exception.startResolutionForResult(this, REQUEST_PERMISSION);
            } else {
                TextView errorTextView = errorView.findViewById(R.id.error_text_view);
                errorTextView.setText(throwable.getMessage());

                loadingView.setVisibility(View.INVISIBLE);
                contentView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION:

                if (Permissions.hasPermissions(this, permissions))
                    viewModel.load();
                else if (Permissions.somePermissionPermanentlyDenied(this, permissions))
                    new AppSettingsDialog.Builder(this).build().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_RESOLUTION:

                switch (resultCode) {

                    case Activity.RESULT_OK:
                        viewModel.load();
                        break;
                }

                break;

            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:

                if (hasNeededPermissions())
                    viewModel.load();

                break;

            case SearchCityActivity.REQUEST_CODE:

                switch (resultCode) {

                    case SearchCityActivity.RESULT_CODE:
                        onSearchCity(data);
                        break;
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = SearchCityActivity.newIntent(this);
            startActivityForResult(intent, SearchCityActivity.REQUEST_CODE);
        } else if (id == R.id.action_refresh) {
            viewModel.load();
        }

        return super.onOptionsItemSelected(item);
    }


    @NonNull
    @Override
    public RootViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(RootViewModel.class);
    }

    private boolean hasNeededPermissions() {
        return isPermissionGranted(ACCESS_FINE_LOCATION) && isPermissionGranted(ACCESS_COARSE_LOCATION);
    }

    private boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void onSearchCity(Intent data) {
        Place place = (Place) data.getSerializableExtra(Place.class.getSimpleName());
        Intent intent = CityWeatherActivity.newIntent(this, place);
        startActivity(intent);
    }
}
