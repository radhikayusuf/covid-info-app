package com.radhikairfan.covidinfo.domain.repository;

import com.radhikairfan.covidinfo.data.storage.BaseCallback;
import com.radhikairfan.covidinfo.domain.entity.HomeEntity;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 30/Jul/2021
 **/
public interface CovidRepository {

    void getCovidInformation(String date, String country, BaseCallback<HomeEntity> callback);
}
