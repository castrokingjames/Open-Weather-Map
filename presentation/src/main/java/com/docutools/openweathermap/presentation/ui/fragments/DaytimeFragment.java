package com.docutools.openweathermap.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.presentation.R;

import java.text.SimpleDateFormat;

public class DaytimeFragment
        extends Fragment {

    private static final SimpleDateFormat DAY_TIME_FORMAT = new SimpleDateFormat("hh:mm aaa");

    private TextView sunriseTextView;
    private TextView sunsetTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daytime, container, false);
        sunriseTextView = view.findViewById(R.id.sunrise_text_view);
        sunsetTextView = view.findViewById(R.id.sunset_text_view);
        return view;
    }

    public void show() {
        Bundle bundle = getArguments();
        Weather weather = (Weather) bundle.getSerializable(Weather.class.getSimpleName());
        sunriseTextView.setText(DAY_TIME_FORMAT.format(weather.sunrise));
        sunsetTextView.setText(DAY_TIME_FORMAT.format(weather.sunset));
    }
}