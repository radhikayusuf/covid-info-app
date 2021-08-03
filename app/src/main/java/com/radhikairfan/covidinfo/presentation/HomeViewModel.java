package com.radhikairfan.covidinfo.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.radhikairfan.covidinfo.data.model.Error;
import com.radhikairfan.covidinfo.data.model.Loading;
import com.radhikairfan.covidinfo.data.model.Result;
import com.radhikairfan.covidinfo.data.model.Success;
import com.radhikairfan.covidinfo.data.storage.BaseCallback;
import com.radhikairfan.covidinfo.domain.entity.HomeEntity;
import com.radhikairfan.covidinfo.domain.usecase.HomeUseCase;
import com.radhikairfan.covidinfo.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 02/Aug/2021
 **/
class HomeViewModel extends ViewModel {

    private final MutableLiveData<Result<HomeEntity>> _dashboardData = new MutableLiveData<>();
    public LiveData<Result<HomeEntity>> dashboardData = _dashboardData;

    private final MutableLiveData<String> _date = new MutableLiveData<>();
    public LiveData<String> date = _date;

    private final MutableLiveData<String> _country = new MutableLiveData<>("Indonesia");
    public LiveData<String> country = _country;

    private List<String> countryList = new ArrayList<>();

    private final HomeUseCase useCase;

    public HomeViewModel(HomeUseCase useCase) {
        this.useCase = useCase;
    }

    public void onCreate() {
        setDate(DateUtils.dateToString(DateUtils.longToDate(System.currentTimeMillis() - 86400000), DateUtils.STANDARD_FULL_MONTH_TIME_FORMAT));
    }

    private void fetchData(String date, String country) {
        _dashboardData.postValue(new Loading());
        useCase.getHomeDashboardData(
                DateUtils.reformatStringDate(date, DateUtils.STANDARD_FULL_MONTH_TIME_FORMAT, DateUtils.STANDARD_TIME_FORMAT),
                country, new BaseCallback<HomeEntity>() {
            @Override
            public void onSuccess(HomeEntity data) {
                _dashboardData.postValue(new Success(data));
            }

            @Override
            public void onError(String message) {
                _dashboardData.postValue(new Error(message));
            }
        });
    }

    public void setDate(String date) {
        _date.postValue(date);
        fetchData(date, country.getValue());
    }

    public void setCountry(String country) {
        _country.postValue(country);
        fetchData(date.getValue(), country);
    }

    public int getSelectedCountryIndex() {
        Integer index = Collections.binarySearch(countryList, country.getValue() == null ? "" : country.getValue());
        return index == null ? 0 : index;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }
}
