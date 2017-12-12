package com.calftracker.project.models;

/**
 * Created by JT on 11/25/2017.
 */

public class IllnessTask {
    private Illness illness;
    private Medicine prescribedMedicine;
    private Calf calf;

    // empty constructor for firebase
    public IllnessTask(){}


    public IllnessTask(Illness illness, Medicine medicine, Calf calf) {
        this.illness = illness;
        this.prescribedMedicine = medicine;
        this.calf = calf;
    }

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

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof IllnessTask)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        IllnessTask task = (IllnessTask) o;

        if(task.getCalf().getFarmId().equals(this.getCalf().getFarmId()) && task.getMedicine().getName().equals(this.getMedicine().getName())
                && task.getIllness().getName().equals(this.getIllness().getName()))
            return true;
        else
            return false;
    }
}
