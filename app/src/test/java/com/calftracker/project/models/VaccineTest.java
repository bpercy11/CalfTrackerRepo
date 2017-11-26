package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class VaccineTest {

    private Vaccine vaccine;
    private String vaccineName;
    private ArrayList<Vacc_Range> range;
    private Double dosage;
    private String dosageUnits;
    private String adminMethod;

    @Before
    public void setUp() throws Exception {
        vaccineName = "Vaccine1";
        range = new ArrayList<Vacc_Range>();
        int[] r = {2,10};
        range.add(new Vacc_Range(r));
        dosage = 14.2;
        dosageUnits = "ML";
        adminMethod = "bottle";

        vaccine = new Vaccine(vaccineName,range,dosage,dosageUnits,adminMethod);
    }

    @Test
    public void testAddVAccine() throws Exception {
        assert(vaccine.getName().equals(vaccineName));
        assert(vaccine.getToBeAdministered().equals(range));
        assert(vaccine.getDosage() == dosage);
        assert(vaccine.getDosageUnits().equals(dosageUnits));
        assert(vaccine.getMethodOfAdministration().equals(adminMethod));
    }

    @Test
    public void testVaccineName() throws Exception {
        String new_VaccineName = "NewVaccine1";
        vaccine.setName(new_VaccineName);
        assert(vaccine.getName().equals(new_VaccineName));
    }

    @Test
    public void testVaccineRange() throws Exception {
        ArrayList<Vacc_Range> new_range = new ArrayList<Vacc_Range>();
        int[] new_r = {5,15};
        new_range.add(new Vacc_Range(new_r));
        vaccine.setToBeAdministered(new_range);
        assert(vaccine.getToBeAdministered().equals(new_range));
    }

    @Test
    public void testVaccineDosage() throws Exception {
        Double new_dosage = 4.2;
        vaccine.setDosage(new_dosage);
        assert(vaccine.getDosage() == new_dosage);
    }

    @Test
    public void testVaccineDosageUnits() throws Exception {
        String new_dosageUnits = "L";
        vaccine.setDosageUnits(new_dosageUnits);
        assert(vaccine.getDosageUnits().equals(new_dosageUnits));
    }

    @Test
    public void testVaccineAdminMethod() throws Exception {
        String new_adminMethod = "Tube";
        vaccine.setMethodOfAdministration(new_adminMethod);
        assert(vaccine.getMethodOfAdministration().equals(new_adminMethod));
    }

    @After
    public void tearDown() throws Exception {

    }

}