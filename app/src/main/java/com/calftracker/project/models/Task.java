package com.calftracker.project.models;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jared on 11/16/2017.
 */

public class Task {

    private Calendar dateLastUpdated;
    private ArrayList<Calf> calvesToObserve;
    private ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister;
    private ArrayList<VaccineTask> overdueVaccinations;

    public Task(Calendar dateLastUpdated, ArrayList<Calf> calvesToObserve,
                ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister, ArrayList<VaccineTask> overdueVaccinations) {
        super();
        this.dateLastUpdated = dateLastUpdated;
        this.calvesToObserve = calvesToObserve;
        this.vaccinesToAdminister = vaccinesToAdminister;
        this.overdueVaccinations = overdueVaccinations;
    }

    public Calendar getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Calendar dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public ArrayList<Calf> getCalvesToObserve() {
        return calvesToObserve;
    }

    public void setCalvesToObserve(ArrayList<Calf> calvesToObserve) {
        this.calvesToObserve = calvesToObserve;
    }

    public ArrayList<ArrayList<VaccineTask>> getVaccinesToAdminister() {
        return vaccinesToAdminister;
    }

    public void setVaccinesToAdminister(ArrayList<ArrayList<VaccineTask>> vaccinesToAdminister) {
        this.vaccinesToAdminister = vaccinesToAdminister;
    }

    public ArrayList<VaccineTask> getOverdueVaccinations() {
        return overdueVaccinations;
    }

    public void setOverdueVaccinations(ArrayList<VaccineTask> overdueVaccinations) {
        this.overdueVaccinations = overdueVaccinations;
    }
}
