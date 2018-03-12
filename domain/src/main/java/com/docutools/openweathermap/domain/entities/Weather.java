package com.docutools.openweathermap.domain.entities;

import java.io.Serializable;
import java.util.Date;

public class Weather
        implements Serializable {

    public String code;
    public float temperature;
    public int cloud;
    public float wind;
    public int humidity;
    public int visibility;
    public float pressure;
    public String description;
    public Date sunrise;
    public Date sunset;
    public Place place;

    public Weather(String code, float temperature, int cloud, float wind, int humidity, int visibility, float pressure, String description, Date sunrise, Date sunset, Place place) {
        this.code = code;
        this.temperature = temperature;
        this.cloud = cloud;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
        this.description = description;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.place = place;
    }
}