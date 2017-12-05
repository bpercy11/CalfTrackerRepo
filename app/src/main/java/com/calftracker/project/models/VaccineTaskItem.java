package com.calftracker.project.models;

/**
 * Created by JT on 11/24/2017.
 */



public class VaccineTaskItem {
    boolean overdue;
    VaccineTask vaccineTask;

    public VaccineTaskItem(boolean overdue, VaccineTask vaccineTask) {
        this.overdue = overdue;
        this.vaccineTask = vaccineTask;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public VaccineTask getVaccineTask() {
        return vaccineTask;
    }

    public void setVaccineTask(VaccineTask vaccineTask) {
        this.vaccineTask = vaccineTask;
    }
}
