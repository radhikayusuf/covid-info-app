package com.radhikairfan.covidinfo.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.radhikairfan.covidinfo.BuildConfig;
import com.radhikairfan.covidinfo.data.model.CovidBaseResponseModel;
import com.radhikairfan.covidinfo.data.service.ApiService;
import com.radhikairfan.covidinfo.data.service.CovidService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 02/Aug/2021
 **/
public class CovidStorageFactory {

    private static CovidStorageFactory INSTANCE = null;

    private final SharedPreferences preferences;
    private final CovidService service;
    private final Gson gson = new Gson();

    private CovidStorageFactory(Context context) {
        preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        service = ApiService.createService("https://unikom-incovid.herokuapp.com");
    }

    private CovidBaseResponseModel getCacheInformation(String country, String date) {
        try {
            String data = preferences.getString(date + country, "");
            return gson.fromJson(data, CovidBaseResponseModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void getCovidInformation(String country, String date, BaseCallback<CovidBaseResponseModel> callback) {
        CovidBaseResponseModel cache = getCacheInformation(country, date);
        if (cache == null) {
            fetchDataFromServer(country, date, callback);
        } else {
            callback.onSuccess(cache);
        }
    }

    private void fetchDataFromServer(String country, String date, BaseCallback<CovidBaseResponseModel> callback) {
        service.getCovidInformation(date, country).enqueue(new Callback<CovidBaseResponseModel>() {
            @Override
            public void onResponse(Call<CovidBaseResponseModel> call, Response<CovidBaseResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && !isAllCasesZero(response.body())) {
                    preferences.edit().putString(date + country, gson.toJson(response.body())).apply();
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Belum ada data kasus untuk tanggal "+ date  +" di negara " + country);
                }
            }

            @Override
            public void onFailure(Call<CovidBaseResponseModel> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    private boolean isAllCasesZero(CovidBaseResponseModel body) {
        return body.getRecovered() == 0 && body.getDeaths() == 0 && body.getConfirmed() == 0;
    }

    public static CovidStorageFactory getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CovidStorageFactory(context);
        }
        return INSTANCE;
    }
}
