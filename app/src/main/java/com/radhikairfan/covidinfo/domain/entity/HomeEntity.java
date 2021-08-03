package com.radhikairfan.covidinfo.domain.entity;

/**
 * Created by Radhika Yusuf Alifiansyah
 * on 30/Jul/2021
 **/
public class HomeEntity {
    private int deadCases = 0;
    private int positiveCases = 0;
    private int recoverCase = 0;

    public HomeEntity(int deadCases, int positiveCases, int recoverCase) {
        this.deadCases = deadCases;
        this.positiveCases = positiveCases;
        this.recoverCase = recoverCase;
    }

    public int getDeadCases() {
        return deadCases;
    }

    public void setDeadCases(int deadCases) {
        this.deadCases = deadCases;
    }

    public int getPositiveCases() {
        return positiveCases;
    }

    public void setPositiveCases(int positiveCases) {
        this.positiveCases = positiveCases;
    }

    public int getRecoverCase() {
        return recoverCase;
    }

    public void setRecoverCase(int recoverCase) {
        this.recoverCase = recoverCase;
    }
}
