package com.docutools.openweathermap.domain.entities;

import java.io.Serializable;

public class Place
        implements Serializable {

    public String city;
    public String code;

    public Place(String city, String code) {
        this.city = city;
        this.code = code;
    }
}