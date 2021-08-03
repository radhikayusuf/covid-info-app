package com.radhikairfan.covidinfo.data.storage;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 02/Aug/2021
 **/
public interface BaseCallback<T> {

    void onSuccess(T data);

    void onError(String message);
}
