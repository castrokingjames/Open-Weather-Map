package com.docutools.openweathermap.domain.utils;

public class Temperatures {

    public static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.16f;
    }
}