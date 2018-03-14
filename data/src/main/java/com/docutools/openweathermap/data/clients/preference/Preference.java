package com.docutools.openweathermap.data.clients.preference;

public interface Preference<T> {

    T get();

    boolean hasValue();

    void set(T t);
}