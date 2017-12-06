package com.calftracker.project.interfaces;

import com.calftracker.project.models.Calf;
import com.calftracker.project.models.IllnessTask;

/**
 * Created by JT on 11/25/2017.
 */

public interface TasksMethods {
    void showObservationDialog(Calf calf);
    void gotoIllnessDetails(IllnessTask illnessTask);
}
