package com.docutools.openweathermap.presentation.di.modules;

import com.docutools.openweathermap.data.clients.network.OpenWeatherMapWebService;
import com.docutools.openweathermap.data.clients.network.services.WeatherService;
import com.docutools.openweathermap.presentation.BuildConfig;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WebServiceModule {

    @Provides
    @Singleton
    public Retrofit providesRetrofit(Gson gson) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.OWM_URL)
                .client(client)
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    public OpenWeatherMapWebService openWeatherMapWebService(Retrofit retrofit) {
        return new OpenWeatherMapWebService(retrofit);
    }

    @Singleton
    @Provides
    public WeatherService providesWeatherService(OpenWeatherMapWebService webService) {
        return webService.weatherService();
    }
}