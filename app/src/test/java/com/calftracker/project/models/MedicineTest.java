package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class MedicineTest {
    private Medicine medicine;
    private String medicineName;
    private Double dosage;
    private String dosageUnits;
    private int timeActive;
    private String notes;

    @Before
    public void setUp() throws Exception {
        medicineName = "medicine1";
        dosage = 15.3;
        dosageUnits = "ML";
        timeActive = 24;
        notes = "Testing Medicine Notes";

        medicine = new Medicine(medicineName,dosage,dosageUnits,timeActive,notes);
    }

    @Test
    public void testAddMedicine() throws Exception {
        assert(medicine.getName().equals(medicineName));
        assert(medicine.getDosage() == dosage);
        assert(medicine.getDosage_units().equals(dosageUnits));
        assert(medicine.getTimeActive() == timeActive);
        assert(medicine.getNotes().equals(notes));

    }

    @Test
    public void testMedicineName() throws Exception {
        String new_medicineName = "new Medicine1";
        medicine.setName(new_medicineName);
        assert(medicine.getName().equals(new_medicineName));
    }

    @Test
    public void testMedicineDosage() throws Exception {
        Double new_dosage = 5.3;
        medicine.setDosage(new_dosage);
        assert(medicine.getDosage() == new_dosage);
    }

    @Test
    public void testMedicineDosageUnits() throws Exception {
        String new_dosageUnits = "L";
        medicine.setDosage_units(new_dosageUnits);
        assert(medicine.getDosage_units().equals(new_dosageUnits));
    }

    @Test
    public void testMedicineTimeActive() throws Exception {
        int new_timeActive = 25;
        medicine.setTimeActive(new_timeActive);
        assert(medicine.getTimeActive() == new_timeActive);
    }

    @Test
    public void testNotes() throws Exception {
        String new_notes = "new notes to test";
        medicine.setNotes(new_notes);
        assert(medicine.getNotes().equals(new_notes));
    }

    @After
    public void tearDown() throws Exception {

    }
}