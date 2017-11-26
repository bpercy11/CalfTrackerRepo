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

    // NEED TO BE ABLE TO COPY FOR SEARCHING IN UPDATETASKS
    public VaccineTask(VaccineTask vT) {
        this.vaccine = vT.getVaccine();
        this.calf = vT.getCalf();
        this.start = vT.isStart();
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

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof VaccineTask)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        VaccineTask task = (VaccineTask) o;

        if(task.getCalf().getFarmId().equals(this.getCalf().getFarmId()) && task.getVaccine().getName().equals(this.getVaccine().getName()))
            return true;
        else
            return false;
    }
}
