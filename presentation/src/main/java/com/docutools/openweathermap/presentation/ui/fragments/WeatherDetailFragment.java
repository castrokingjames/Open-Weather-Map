package com.docutools.openweathermap.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.presentation.R;

public class WeatherDetailFragment
        extends Fragment {

    private TextView feelsLikeTextView;
    private TextView chanceOfRainTextView;
    private TextView windTextView;
    private TextView humidityTextView;
    private TextView visibilityTextView;
    private TextView pressureTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        feelsLikeTextView = view.findViewById(R.id.feels_like_text_view);
        chanceOfRainTextView = view.findViewById(R.id.chance_of_rain_text_view);
        windTextView = view.findViewById(R.id.wind_text_view);
        humidityTextView = view.findViewById(R.id.humidity_text_view);
        visibilityTextView = view.findViewById(R.id.visibility_text_view);
        pressureTextView = view.findViewById(R.id.pressure_text_view);
        return view;
    }

    public void show() {
        Bundle bundle = getArguments();
        Weather weather = (Weather) bundle.getSerializable(Weather.class.getSimpleName());
        feelsLikeTextView.setText(String.format("%.2f°", weather.temperature));
        chanceOfRainTextView.setText(weather.cloud + "%");
        windTextView.setText(weather.wind + " kph");
        humidityTextView.setText(weather.humidity + "%");
        visibilityTextView.setText((weather.visibility / 1000) + " km");
        pressureTextView.setText(weather.pressure + " mbar");
    }
}