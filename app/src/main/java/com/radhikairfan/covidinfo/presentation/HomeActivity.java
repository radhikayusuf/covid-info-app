package com.radhikairfan.covidinfo.presentation;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.radhikairfan.covidinfo.R;
import com.radhikairfan.covidinfo.data.model.Error;
import com.radhikairfan.covidinfo.data.model.Result;
import com.radhikairfan.covidinfo.data.model.Success;
import com.radhikairfan.covidinfo.data.repository.CovidRepositoryImpl;
import com.radhikairfan.covidinfo.domain.entity.HomeEntity;
import com.radhikairfan.covidinfo.domain.usecase.HomeUseCase;
import com.radhikairfan.covidinfo.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private HomeUseCase useCase;
    private HomeViewModel viewModel;

    private TextView positiveCaseView;
    private TextView recoveryCaseView;
    private TextView deadCaseView;

    private DatePickerDialog mDatePickerDialog;
    private AlertDialog countryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        observeVM();
        viewModel.onCreate();
    }

    private void initComponent() {
        useCase = new HomeUseCase(new CovidRepositoryImpl(this));
        viewModel = new HomeViewModel(useCase);
        viewModel.setCountryList(Arrays.asList(getResources().getStringArray(R.array.array_country)));

        positiveCaseView = ((TextView) getView(R.id.contentPositive));
        deadCaseView = ((TextView) getView(R.id.contentDeath));
        recoveryCaseView = ((TextView) getView(R.id.contentRecover));
        ((Toolbar) getView(R.id.toolbar)).setOnMenuItemClickListener(item -> {
            countryDialog.show();
            return false;
        });
        getView(R.id.changeDate).setOnClickListener(view -> {
            mDatePickerDialog.show();
        });

        countryDialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(
                        R.array.array_country, viewModel.getSelectedCountryIndex(),
                        (dialogInterface, index) -> {
                            viewModel.setCountry(viewModel.getCountryList().get(index));
                            dialogInterface.dismiss();
                        })
                .create();

        mDatePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            int finalMonth = month + 1;
            String date = year + "-" + ((finalMonth) < 10 ? "0" + finalMonth : finalMonth) + "-" + day;
            viewModel.setDate(DateUtils.reformatStringDate(date, DateUtils.STANDARD_TIME_FORMAT, DateUtils.STANDARD_FULL_MONTH_TIME_FORMAT));
        }, Integer.parseInt(DateUtils.getThisYear()), Integer.parseInt(DateUtils.getThisMonth()), Integer.parseInt(DateUtils.getTodayDate()));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 86400000);
    }

    private void observeVM() {
        viewModel.dashboardData.observe(this, result -> renderDashboard(result));
        viewModel.date.observe(this, result -> renderDateComponent(result));
        viewModel.country.observe(this, result -> renderCountryComponent(result));
    }

    private void renderDashboard(Result<HomeEntity> result) {
        changeProgressBarCondition(false);
        if (result instanceof Success) {
            HomeEntity data = ((Success<HomeEntity>) result).data;
            bindDashboardData(data);
        } else if (result instanceof Error) {
            Toast.makeText(this, ((Error<HomeEntity>) result).message, Toast.LENGTH_LONG).show();
        } else {
            changeProgressBarCondition(true);
        }
    }

    private void renderCountryComponent(String data) {
        MenuItem menu = ((Toolbar) getView(R.id.toolbar)).getMenu().findItem(R.id.content);
        menu.setTitle(data);
    }

    private void renderDateComponent(String data) {
        ((TextView) getView(R.id.dateContent)).setText(data);
    }

    private void bindDashboardData(HomeEntity data) {
        positiveCaseView.setText(data.getPositiveCases() + "");
        deadCaseView.setText(data.getDeadCases() + "");
        recoveryCaseView.setText(data.getRecoverCase() + "");
    }

    private void changeProgressBarCondition(boolean isShow) {
        int progressVisibility = isShow ? View.VISIBLE : View.INVISIBLE;
        int componentVisibility = !isShow ? View.VISIBLE : View.INVISIBLE;

        getView(R.id.progressDeath).setVisibility(progressVisibility);
        getView(R.id.progressPositive).setVisibility(progressVisibility);
        getView(R.id.progressRecover).setVisibility(progressVisibility);

        getView(R.id.parentDeath).setVisibility(componentVisibility);
        getView(R.id.parentPositive).setVisibility(componentVisibility);
        getView(R.id.parentRecover).setVisibility(componentVisibility);
    }

    private View getView(@IdRes int id) {
        return findViewById(id);
    }
}