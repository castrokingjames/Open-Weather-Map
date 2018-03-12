package com.docutools.canvass.data.clients.preference;

public interface Preference<T> {

    T get();

    boolean hasValue();

    void set(T t);
}