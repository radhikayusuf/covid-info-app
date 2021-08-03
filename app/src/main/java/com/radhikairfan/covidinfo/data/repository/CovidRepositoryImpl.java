package com.radhikairfan.covidinfo.data.repository;

import android.content.Context;

import com.radhikairfan.covidinfo.data.model.CovidBaseResponseModel;
import com.radhikairfan.covidinfo.data.storage.BaseCallback;
import com.radhikairfan.covidinfo.data.storage.CovidStorageFactory;
import com.radhikairfan.covidinfo.domain.entity.HomeEntity;
import com.radhikairfan.covidinfo.domain.repository.CovidRepository;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 30/Jul/2021
 **/
public class CovidRepositoryImpl implements CovidRepository {

    private CovidStorageFactory dataSource;

    public CovidRepositoryImpl(Context context) {
        this.dataSource = CovidStorageFactory.getInstance(context);
    }

    @Override
    public void getCovidInformation(String date, String country, BaseCallback<HomeEntity> callback) {
        dataSource.getCovidInformation(country, date, new BaseCallback<CovidBaseResponseModel>() {
            @Override
            public void onSuccess(CovidBaseResponseModel data) {
                HomeEntity mappingData = new HomeEntity(data.getDeaths(), data.getConfirmed(), data.getRecovered());
                callback.onSuccess(mappingData);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }
}
