package com.docutools.openweathermap.data.clients.network.responses;

import java.util.List;

public class GetWeatherResponse {

    public CoordResponse coord;
    public List<WeatherResponse> weather;
    public String base;
    public MainResponse main;
    public int visibility;
    public WindResponse wind;
    public CloudsResponse clouds;
    public long dt;
    public SysResponse sys;
    public long id;
    public String name;
    public int cod;

    public class CoordResponse {

        public float lon;
        public float lan;
    }

    public class WeatherResponse {

        public long id;
        public String main;
        public String description;
        public String icon;
    }

    public class MainResponse {

        public float temp;
        public float pressure;
        public int humidity;
        public float temp_min;
        public float temp_max;
    }

    public class WindResponse {

        public float speed;
        public float deg;
    }

    public class CloudsResponse {

        public int all;
    }

    public class SysResponse {

        public long type;
        public long id;
        public float message;
        public String country;
        public long sunrise;
        public long sunset;
    }
}