package com.radhikairfan.covidinfo.domain.usecase;

import com.radhikairfan.covidinfo.data.storage.BaseCallback;
import com.radhikairfan.covidinfo.domain.entity.HomeEntity;
import com.radhikairfan.covidinfo.domain.repository.CovidRepository;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 30/Jul/2021
 **/
public class HomeUseCase {

    private CovidRepository repository;

    public HomeUseCase(CovidRepository repository) {
        this.repository = repository;
    }

    public void getHomeDashboardData(String date, String country, BaseCallback<HomeEntity> callback) {
        String finalDate = date != null && !date.trim().isEmpty() ? date : "";
        String finalCountry = country != null && !country.trim().isEmpty() ?  country : "";
        repository.getCovidInformation(finalDate, finalCountry, callback);
    }
}
