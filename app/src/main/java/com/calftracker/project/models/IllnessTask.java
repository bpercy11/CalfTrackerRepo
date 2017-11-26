package com.calftracker.project.models;

/**
 * Created by JT on 11/25/2017.
 */

public class IllnessTask {
    private Illness illness;
    private Medicine medicine;
    private Calf calf;

    public IllnessTask(Illness illness, Medicine medicine, Calf calf) {
        this.illness = illness;
        this.medicine = medicine;
        this.calf = calf;
    }

    public Illness getIllness() {
        return illness;
    }

    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Calf getCalf() {
        return calf;
    }

    public void setCalf(Calf calf) {
        this.calf = calf;
    }
}
