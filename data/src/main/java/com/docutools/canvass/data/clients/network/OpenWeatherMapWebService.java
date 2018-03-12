package com.docutools.canvass.data.clients.network;

import com.docutools.canvass.data.clients.network.services.WeatherService;

import retrofit2.Retrofit;

public class OpenWeatherMapWebService {

    private Retrofit retrofit;

    public OpenWeatherMapWebService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public WeatherService weatherService() {
        return retrofit.create(WeatherService.class);
    }
}