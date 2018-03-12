package com.docutools.openweathermap.domain.entities;

public class Location {

    public double latitude;
    public double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}