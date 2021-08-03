package com.radhikairfan.covidinfo.data.service;

import com.radhikairfan.covidinfo.data.model.CovidBaseResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 30/Jul/2021
 **/
public interface CovidService {

    @GET("/")
    Call<CovidBaseResponseModel> getCovidInformation(
            @Query("date") String date,
            @Query("country") String country
    );
}
