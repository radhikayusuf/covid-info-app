package com.radhikairfan.covidinfo.data.model;

public class Error<T> extends Result<T> {
    public String message;

    public Error(String message) {
        this.message = message;
    }
}
