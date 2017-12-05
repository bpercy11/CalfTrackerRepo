package com.calftracker.project.models;

/**
 * Created by JT on 11/25/2017.
 */

public class IllnessTask {
    private Illness illness;
    private Medicine prescribedMedicine;
    private Calf calf;

    public IllnessTask(Illness illness, Medicine medicine, Calf calf) {
        this.illness = illness;
        this.prescribedMedicine = medicine;
        this.calf = calf;
    }

    public IllnessTask(){}

    public Illness getIllness() {
        return illness;
    }

    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    public Medicine getMedicine() {
        return prescribedMedicine;
    }

    public void setMedicine(Medicine medicine) {
        this.prescribedMedicine = medicine;
    }

    public Calf getCalf() {
        return calf;
    }

    public void setCalf(Calf calf) {
        this.calf = calf;
    }
}
