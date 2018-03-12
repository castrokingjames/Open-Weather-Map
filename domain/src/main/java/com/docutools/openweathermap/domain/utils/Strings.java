package com.docutools.openweathermap.domain.utils;

public class Strings {

    public static String capitalize(String input) {
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }
}
