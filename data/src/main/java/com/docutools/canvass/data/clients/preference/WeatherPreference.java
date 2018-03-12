package com.docutools.canvass.data.clients.preference;

import android.content.SharedPreferences;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.domain.utils.Calendars;

public class WeatherPreference
        implements Preference<Weather> {

    private final String KEY_WEATHER = "KEY_WEATHER";
    private final String KEY_CODE = "KEY_CODE";
    private final String KEY_TEMPERATURE = "KEY_TEMPERATURE";
    private final String KEY_CLOUD = "KEY_CLOUD";
    private final String KEY_WIND = "KEY_WIND";
    private final String KEY_HUMIDITY = "KEY_HUMIDITY";
    private final String KEY_VISIBILITY = "KEY_VISIBILITY";
    private final String KEY_PRESSURE = "KEY_PRESSURE";
    private final String KEY_DESCRIPTION = "KEY_SUNRISE";
    private final String KEY_SUNRISE = "KEY_DESCRIPTION";
    private final String KEY_SUNSET = "KEY_SUNSET";
    private final String KEY_CITY = "KEY_CITY";
    private final String KEY_COUNTRY = "KEY_COUNTRY";

    private SharedPreferences sharedPreferences;

    public WeatherPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Weather get() {
        String code = sharedPreferences.getString(KEY_CODE, null);
        float temperature = sharedPreferences.getFloat(KEY_TEMPERATURE, 0f);
        int cloud = sharedPreferences.getInt(KEY_CLOUD, 0);
        float wind = sharedPreferences.getFloat(KEY_WIND, 0);
        int humidity = sharedPreferences.getInt(KEY_HUMIDITY, 0);
        int visibility = sharedPreferences.getInt(KEY_VISIBILITY, 0);
        float pressure = sharedPreferences.getFloat(KEY_PRESSURE, 0);
        String description = sharedPreferences.getString(KEY_DESCRIPTION, "");
        long sunrise = sharedPreferences.getLong(KEY_SUNRISE, 0);
        long sunset = sharedPreferences.getLong(KEY_SUNSET, 0);
        String city = sharedPreferences.getString(KEY_CITY, null);
        String country = sharedPreferences.getString(KEY_COUNTRY, null);
        return new Weather(code, temperature, cloud, wind, humidity, visibility, pressure, description, Calendars.toDate(sunrise), Calendars.toDate(sunset), new Place(city, country));
    }

    @Override
    public boolean hasValue() {
        return sharedPreferences.getBoolean(KEY_WEATHER, false);
    }

    @Override
    public void set(Weather weather) {
        sharedPreferences
                .edit()
                .putBoolean(KEY_WEATHER, true)
                .putString(KEY_CODE, weather.code)
                .putFloat(KEY_TEMPERATURE, weather.temperature)
                .putInt(KEY_CLOUD, weather.cloud)
                .putFloat(KEY_WIND, weather.wind)
                .putInt(KEY_HUMIDITY, weather.humidity)
                .putInt(KEY_VISIBILITY, weather.visibility)
                .putFloat(KEY_PRESSURE, weather.pressure)
                .putString(KEY_DESCRIPTION, weather.description)
                .putLong(KEY_SUNRISE, weather.sunrise.getTime())
                .putLong(KEY_SUNSET, weather.sunset.getTime())
                .putString(KEY_CITY, weather.place.city)
                .putString(KEY_COUNTRY, weather.place.code)
                .commit();
    }
}
