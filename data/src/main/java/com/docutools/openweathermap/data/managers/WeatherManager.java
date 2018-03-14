package com.docutools.openweathermap.data.managers;

import com.docutools.openweathermap.data.clients.network.responses.GetWeatherResponse;
import com.docutools.openweathermap.data.clients.network.services.WeatherService;
import com.docutools.openweathermap.data.clients.preference.Preference;
import com.docutools.openweathermap.data.BuildConfig;
import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.domain.entities.Weather;
import com.docutools.openweathermap.domain.repositories.LocationRepository;
import com.docutools.openweathermap.domain.repositories.PlaceRepository;
import com.docutools.openweathermap.domain.repositories.WeatherRepository;
import com.docutools.openweathermap.domain.utils.Calendars;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class WeatherManager
        implements WeatherRepository {

    private LocationRepository locationRepository;
    private PlaceRepository placeRepository;
    private WeatherService weatherService;
    private Preference<Weather> preference;

    public WeatherManager(LocationRepository locationRepository,
                          PlaceRepository placeRepository,
                          WeatherService weatherService,
                          Preference<Weather> preference) {
        this.locationRepository = locationRepository;
        this.placeRepository = placeRepository;
        this.weatherService = weatherService;
        this.preference = preference;
    }

    @Override
    public Observable<Weather> loadWeather() {
        Observable<Weather> network = locationRepository
                .loadLocation()
                .flatMap(location -> placeRepository.geocode(location.latitude, location.longitude))
                .flatMap(place -> weatherService.weather(place.city + "," + place.code, BuildConfig.OWM_TOKEN))
                .flatMap(this::transform)
                .flatMap(this::save)
                .onErrorResumeNext(onErrorResumeNext());

        Observable<Weather> cache = cache();

        return Observable.merge(network, cache);
    }


    @Override
    public Observable<Weather> loadWeather(Place place) {
        return weatherService
                .weather(place.city + "," + place.code, BuildConfig.OWM_TOKEN)
                .flatMap(this::transform);
    }

    private Observable<Weather> transform(GetWeatherResponse response) {
        return Observable.defer(() -> {
            float temperature = response.main.temp;
            int cloud = response.clouds.all;
            float wind = response.wind.speed;
            int humidity = response.main.humidity;
            int visibility = response.visibility;
            float pressure = response.main.pressure;
            String description = "";
            String code = "";

            if (response.weather.size() > 0) {
                GetWeatherResponse.WeatherResponse weatherResponse = response.weather.get(0);
                description = weatherResponse.description;
                code = weatherResponse.icon;
            }

            long sunrise = response.sys.sunrise;
            long sunset = response.sys.sunset;
            String city = response.name;
            String country = response.sys.country;

            Weather weather = new Weather(code, temperature, cloud, wind, humidity, visibility, pressure, description, Calendars.toDate(sunrise), Calendars.toDate(sunset), new Place(city, country));

            return Observable.just(weather);
        });
    }

    private Observable<Weather> save(Weather weather) {
        return Observable.defer(() -> {

            preference.set(weather);

            return Observable.just(weather);
        });
    }

    private Observable<Weather> cache() {
        return Observable.defer(() -> {
            if (preference.hasValue()) {
                Weather weather = preference.get();
                return Observable.just(weather);
            }

            return Observable.empty();
        });
    }

    public Function<Throwable, Observable<Weather>> onErrorResumeNext() {
        return throwable -> {
            if (throwable instanceof UnknownHostException) {
                return cache();
            } else {
                return Observable.error(throwable);
            }
        };
    }
}