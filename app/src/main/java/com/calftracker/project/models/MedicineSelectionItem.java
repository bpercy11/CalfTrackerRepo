package com.calftracker.project.models;

/**
 * Created by Lisa on 11/17/2017.
 */

public class MedicineSelectionItem {

    private Medicine medicine;
    private boolean checked;

    // empty constructor for firebase
    public MedicineSelectionItem(){}

    public MedicineSelectionItem(Medicine medicine, boolean checked){
        this.medicine = medicine;
        this.checked = checked;
    }

    public Medicine getMedicine(){
        return medicine;
    }
    public void setMedicine(Medicine medicine){
        this.medicine = medicine;
    }
    public boolean getChecked(){
        return checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
