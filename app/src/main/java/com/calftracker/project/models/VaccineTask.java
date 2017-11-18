package com.calftracker.project.models;

import java.util.Calendar;

/**
 * Created by Jared on 11/16/2017.
 */

public class VaccineTask {
    private Vaccine vaccine;
    private Calf calf;
    private boolean start;

    public VaccineTask(Vaccine vaccine, Calf calf, boolean start) {
        this.vaccine = vaccine;
        this.calf = calf;
        this.start = start;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Calf getCalf() {
        return calf;
    }

    public void setCalf(Calf calf) {
        this.calf = calf;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
