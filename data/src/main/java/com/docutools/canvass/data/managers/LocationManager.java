package com.docutools.canvass.data.managers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.docutools.canvass.data.lang.PermissionRequiredException;
import com.docutools.openweathermap.domain.entities.Location;
import com.docutools.openweathermap.domain.repositories.LocationRepository;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;

import io.reactivex.Observable;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationManager
        implements LocationRepository {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private Context context;

    public LocationManager(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Location> loadLocation() {

        return Observable.create(e -> {

            boolean hasAccessFineLocation = ActivityCompat.checkSelfPermission(context,
                    ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean hasAccessCoarseLocation = ActivityCompat.checkSelfPermission(context,
                    ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (hasAccessFineLocation && hasAccessCoarseLocation) {
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
                locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);

                Task<LocationSettingsResponse> task = LocationServices
                        .getSettingsClient(context)
                        .checkLocationSettings(builder.build());

                task.addOnCompleteListener(
                        result -> {

                            try {
                                result.getResult(ApiException.class);

                                FusedLocationProviderClient client = LocationServices
                                        .getFusedLocationProviderClient(context);

                                LocationCallback locationCallback = new LocationCallback() {

                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        android.location.Location location = locationResult.getLastLocation();
                                        new Thread(() -> e.onNext(new Location(location.getLatitude(), location.getLongitude()))).start();
                                        client.removeLocationUpdates(this);

                                    }
                                };

                                client.requestLocationUpdates(
                                        locationRequest,
                                        locationCallback,
                                        Looper.getMainLooper()
                                );

                            } catch (ApiException ae) {
                                ae.printStackTrace();
                                e.onError(ae);
                            }
                        }
                );
            } else {
                PermissionRequiredException exception = new PermissionRequiredException(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION);
                e.onError(exception);
            }
        });
    }
}