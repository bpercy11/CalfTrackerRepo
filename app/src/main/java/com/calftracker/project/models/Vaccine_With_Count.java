package com.calftracker.project.models;

/**
 * Created by Jared on 11/16/2017.
 */

public class Vaccine_With_Count {
    private Vaccine vaccine;
    private int count;

    public Vaccine_With_Count(Vaccine vaccine, int count) {
        this.vaccine = vaccine;
        this.count = count;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
