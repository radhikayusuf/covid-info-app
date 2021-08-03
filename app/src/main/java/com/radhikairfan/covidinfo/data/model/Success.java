package com.radhikairfan.covidinfo.data.model;

public class Success<T> extends Result<T> {
    public T data;

    public Success(T data) {
        this.data = data;
    }
}
