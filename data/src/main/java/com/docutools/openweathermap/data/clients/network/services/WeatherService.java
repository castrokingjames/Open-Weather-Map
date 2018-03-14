package com.docutools.openweathermap.data.clients.network.services;

import com.docutools.openweathermap.data.clients.network.responses.GetWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    Observable<GetWeatherResponse> weather(@Query("q") String q, @Query("appid") String token);
}