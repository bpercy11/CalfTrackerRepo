package com.calftracker.project.models;

import com.calftracker.project.models.Vaccine;

/**
 * Created by JT on 11/1/2017.
 */

public class VaccineSelectionItem {
    private Vaccine vaccine;
    private boolean checked;

    public VaccineSelectionItem(Vaccine vaccine, boolean checked){
        this.vaccine = vaccine;
        this.checked = checked;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
