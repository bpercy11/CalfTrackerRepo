package com.calftracker.project.interfaces;

import com.calftracker.project.models.Vaccine;
import com.calftracker.project.models.Vaccine_With_Date;

/**
 * Created by JT on 11/14/2017.
 */

public interface MedicalHistoryVaccineMethods {
    void administerVaccine(Vaccine vaccine);
    void returnToNeeded(Vaccine_With_Date vaccine);
}
