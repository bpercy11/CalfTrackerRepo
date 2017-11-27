package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalfTest {
    private Calf calf;
    private String photo;
    private String farmId;
    private int internalId;
    private String gender;
    private java.util.Calendar dob;
    private ArrayList<Vaccine> vaccines;

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
    public void testCalfPhoto() throws Exception {
        String new_photo = "photo-data";
        calf.setPhoto(new_photo);
        assert(calf.getPhoto().equals(new_photo));
    }

    @Test
    public void testCalfFarmId() throws Exception {
        String new_farmId = "321";
        calf.setFarmId(new_farmId);
        assert(calf.getFarmId().equals(new_farmId));
    }

    @Test
    public void testCalfInternalId() throws Exception {
        int new_internalId = 654;
        calf.setInternalId(new_internalId);
        assert(calf.getInternalId() == new_internalId);
    }

    @Test
    public void testCalfGender() throws Exception {
        String new_gender = "male";
        calf.setGender(new_gender);
        assert(calf.getGender().equals(new_gender));
    }

    @Test
    public void testCalfDOB() throws Exception {
        java.util.Calendar new_dob = new GregorianCalendar(2017,11,22);
        calf.setDateOfBirth(new_dob);
        assert(calf.getDateOfBirth().equals(new_dob));
    }

    @Test
    public void testCalfSire() throws Exception {
        Sire sire = new Sire("Rockstar", "789");
        calf.setSire(sire);
        assert(calf.getSire().equals(sire));
    }

    @Test
    public void testCalfDam() throws Exception {
        String dam = "987";
        calf.setDam(dam);
        assert(calf.getDam().equals(dam));
    }

    @Test
    public void testCalfAllergies() throws Exception {
        ArrayList<String> allergies = new ArrayList<String>();
        allergies.add("Dander");
        allergies.add("Peanuts");
        calf.setCalfAllergies(allergies);
        assert(calf.getCalfAllergies().equals(allergies));
    }

    @Test
    public void testCalfActive() throws Exception {
        calf.setActive(false);
        assert(!calf.isActive());
    }

    @Test
    public void testCalfObserve() throws Exception {
        calf.setNeedToObserveForIllness(true);
        assert(calf.isNeedToObserveForIllness());
    }

    @Test
    public void testCalfNeededVaccines() throws Exception {
        ArrayList<Vacc_Range> range = new ArrayList<Vacc_Range>();
        int[] r = {2, 10};
        range.add(new Vacc_Range(r));
        vaccines.add(new Vaccine("exampleVaccine2", range, 0.25, "mL", "IM"));
        calf.setNeededVaccines(vaccines);
        assert(calf.getNeededVaccines().equals(vaccines));
    }

    @Test
    public void testCalfAdministeredVaccines() throws Exception {
        java.util.Calendar date_admin = new GregorianCalendar(2017,11,25);
        Vaccine vacc = vaccines.get(0);
        ArrayList<Vaccine_With_Date> adminVaccines = new ArrayList<Vaccine_With_Date>();
        Vaccine_With_Date adminVacc = new Vaccine_With_Date(vacc, date_admin);
        adminVaccines.add(adminVacc);
        calf.setAdministeredVaccines(adminVaccines);
        assert(calf.getAdministeredVaccines().equals(adminVaccines));
    }

    @Test
    public void testCalfIllnessHistory() throws Exception {
        ArrayList<Calf_Illness> illnessList = new ArrayList<Calf_Illness>();
        ArrayList<Medicine> meds = new ArrayList<Medicine>();
        meds.add(new Medicine("MasterGuard", 1.0, "mL", 5, "notes"));
        Treatment_Protocol protocol = new Treatment_Protocol(meds, "notes");
        illnessList.add(new Calf_Illness(new Illness("Pneumonia", protocol), java.util.Calendar.getInstance(), "outcome notes"));
        calf.setIllnessHistory(illnessList);
        assert(calf.getIllnessHistory().equals(illnessList));
    }

    @Test
    public void testCalfPhysicalHistory() throws Exception {
        ArrayList<Physical_Metrics_And_Date> physical = new ArrayList<Physical_Metrics_And_Date>();
        physical.add(new Physical_Metrics_And_Date(50, 64, new GregorianCalendar(2017, 11, 27)));
        calf.setPhysicalHistory(physical);
        assert(calf.getPhysicalHistory().equals(physical));
    }

    @Test
    public void testCalfFeedingHistory() throws Exception {
        Feeding feeding = new Feeding(new Employee("Dan"), "bottle", 2);
        Feeding[] feedings = new Feeding[1];
        feedings[0] = feeding;
        calf.setFeedingHistory(feedings);
        assert(calf.getFeedingHistory().equals(feedings));
    }

    @Test
    public void testCalfNotes() throws Exception {
        ArrayList<Note> notes = new ArrayList<Note>();
        Note note = new Note("Test note", new GregorianCalendar(2017,11,24));
        notes.add(note);
        calf.setNotes(notes);
        Note note2 = new Note("Test note 2", new GregorianCalendar(2017,11,25));
        calf.addNote(note2);
        notes.add(note2);
        assert(calf.getNotes().equals(notes));
        assert(calf.getNoteNdx(0).equals(note));
        assert(calf.getNotesSize() == 1);
    }

    @Test
    public void testAddAdministeredVaccine() throws Exception {
        java.util.Calendar date_admin = new GregorianCalendar(2017,11,25);
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
    public void tearDown() throws Exception { }
}