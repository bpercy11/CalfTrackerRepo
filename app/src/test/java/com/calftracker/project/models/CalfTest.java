package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalfTest {
    Calf calf;
    String photo;
    String farmId;
    int internalId;
    String gender;
    Calendar dob;
    ArrayList<Vaccine> vaccines;

    @Before
    public void setUp() throws Exception {
        photo = null;
        farmId = "123";
        internalId = 456;
        gender = "female";
        dob = new GregorianCalendar(2017,11,23);
        vaccines = new ArrayList<Vaccine>();
        ArrayList<Vacc_Range> range = new ArrayList<Vacc_Range>();
        int[] r = {1,3};
        range.add(new Vacc_Range(r));
        vaccines.add(new Vaccine("exampleVaccine", range, 0.5, "mL", "sq"));

        calf = new Calf(photo, farmId, internalId, gender, dob, vaccines);
    }

    @Test
    public void testAddCalf() throws Exception {
        assert(calf.getPhoto().equals(photo));
        assert(calf.getFarmId().equals(farmId));
        assert(calf.getInternalId() == internalId);
        assert(calf.getGender().equals(gender));
        assert(calf.getDateOfBirth().equals(dob));
        assert(calf.getNeededVaccines().equals(vaccines));
        assert(calf.getCalfAllergies() == null);
        assert(calf.getSire()== null);
        assert(calf.getDam() == null);
        assert(calf.getAdministeredVaccines() == null);
        assert(calf.getNeededVaccines() == null);
        assert(calf.getIllnessHistory() == null);
        assert(calf.getPhysicalHistory() == null);
        assert(calf.getFeedingHistory() == null);
        assert(calf.getNotes() == null);
        assert(calf.isActive());
    }

    @Test
    public void testChangeCalf() throws Exception {
        String new_photo = "photo-data";
        String new_farmId = "321";
        int new_internalId = 654;
        String new_gender = "male";
        Calendar new_dob = new GregorianCalendar(2017,11,22);
        ArrayList<String> allergies = new ArrayList<String>();
        allergies.add("Dander");
        allergies.add("Peanuts");
        String sire = "789";
        String dam = "987";

        Calendar date_admin = new GregorianCalendar(2017,11,25);
        Vaccine vacc = vaccines.get(0);
        ArrayList<Vaccine_With_Date> adminVaccines = new ArrayList<Vaccine_With_Date>();
        Vaccine_With_Date adminVacc = new Vaccine_With_Date(vacc, date_admin);
        adminVaccines.add(adminVacc);
        calf.setAdministeredVaccines(adminVaccines);

        calf.setPhoto(new_photo);
        calf.setFarmId(new_farmId);
        calf.setInternalId(new_internalId);
        calf.setGender(new_gender);
        calf.setDateOfBirth(new_dob);
        calf.setCalfAllergies(allergies);
        calf.setSire(sire);
        calf.setDam(dam);
        calf.setActive(false);

        assert(calf.getPhoto().equals(new_photo));
        assert(calf.getFarmId().equals(new_farmId));
        assert(calf.getInternalId() == new_internalId);
        assert(calf.getGender().equals(new_gender));
        assert(calf.getDateOfBirth().equals(new_dob));
        assert(calf.getCalfAllergies().equals(allergies));
        assert(calf.getSire().equals(sire));
        assert(calf.getDam().equals(dam));
        assert(!calf.isActive());
        assert(calf.getAdministeredVaccines().equals(adminVaccines));
        // needed vaccines
        // illness history
        // physical history
        // feeding history
        // notes
        // observe
    }

    @Test
    public void testAddAdministeredVaccine() throws Exception {
        Calendar date_admin = new GregorianCalendar(2017,11,25);
        Vaccine vacc = vaccines.get(0);
        calf.addAdministeredVaccine(vacc, date_admin);

        Vaccine_With_Date adminVacc = new Vaccine_With_Date(vacc, date_admin);

        boolean removeFromNeededVaccines = true;
        boolean addToAdminVaccines = true;

        ArrayList<Vaccine> neededVaccines = calf.getNeededVaccines();
        ArrayList<Vaccine_With_Date> adminVaccines = calf.getAdministeredVaccines();

        for (int i = 0; i < neededVaccines.size(); i++) {
            if (neededVaccines.get(i).equals(vacc)) removeFromNeededVaccines = false;
        }

        for (int i = 0; i < adminVaccines.size(); i++) {
            if (adminVaccines.get(i).equals(adminVacc)) addToAdminVaccines = false;
        }

        assert(removeFromNeededVaccines);
        assert(addToAdminVaccines);
    }

    @After
    public void tearDown() throws Exception {
    }

}