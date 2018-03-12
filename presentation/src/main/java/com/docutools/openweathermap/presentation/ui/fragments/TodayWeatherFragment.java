package com.docutools.openweathermap.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.domain.utils.Strings;
import com.docutools.openweathermap.presentation.R;

import java.util.HashMap;
import java.util.Map;

public class TodayWeatherFragment
        extends Fragment {

    private static Map<String, Integer> MAP = new HashMap<>();

    static {
        MAP.put("01d", R.drawable.ic_clear_sky_black_75dp);
        MAP.put("01n", R.drawable.ic_clear_sky_black_75dp);

        MAP.put("02d", R.drawable.ic_few_clouds_black_75dp);
        MAP.put("02n", R.drawable.ic_few_clouds_black_75dp);

        MAP.put("03d", R.drawable.ic_scattered_clouds_black_75dp);
        MAP.put("03n", R.drawable.ic_scattered_clouds_black_75dp);

        MAP.put("04d", R.drawable.ic_broken_clouds_black_75dp);
        MAP.put("04n", R.drawable.ic_broken_clouds_black_75dp);

        MAP.put("09d", R.drawable.ic_shower_rain_black_75dp);
        MAP.put("09n", R.drawable.ic_shower_rain_black_75dp);

        MAP.put("10d", R.drawable.ic_rain_black_75dp);
        MAP.put("10n", R.drawable.ic_rain_black_75dp);

        MAP.put("11d", R.drawable.ic_thunderstorm_black_75dp);
        MAP.put("11n", R.drawable.ic_thunderstorm_black_75dp);

        MAP.put("13d", R.drawable.ic_snow_black_75dp);
        MAP.put("13n", R.drawable.ic_snow_black_75dp);

        MAP.put("50d", R.drawable.ic_mist_black_75dp);
        MAP.put("50n", R.drawable.ic_mist_black_75dp);
    }

    private TextView cityTextView;
    private TextView descriptionTextView;
    private TextView temperatureTextView;
    private ImageView iconImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        cityTextView = view.findViewById(R.id.city_text_view);
        descriptionTextView = view.findViewById(R.id.description_text_view);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        iconImageView = view.findViewById(R.id.icon_image_view);
        return view;
    }

    public void show() {
        Bundle bundle = getArguments();
        Weather weather = (Weather) bundle.getSerializable(Weather.class.getSimpleName());
        cityTextView.setText(weather.place.city + ", " + weather.place.code);
        descriptionTextView.setText(Strings.capitalize(weather.description));
        temperatureTextView.setText(String.format("%.2f°", weather.temperature));

        if (MAP.containsKey(weather.code)) {
            int resId = MAP.get(weather.code);
            iconImageView.setImageResource(resId);
        }
    }
}