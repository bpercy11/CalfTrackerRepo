package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class Treatment_ProtocolTest {
    private Treatment_Protocol treatmentProtocol;
    private ArrayList<Medicine> medicines;
    private String notes;

    @Before
    public void setUp() throws Exception {
        medicines = new ArrayList<Medicine>();
        medicines.add(new Medicine("Medicine",15.2,"ML",30,"notes"));
        notes = "treatment notes";

        treatmentProtocol = new Treatment_Protocol(medicines,notes);
    }

    @Test
    public void testAddTreatmentProtocol() throws Exception {
        assert(treatmentProtocol.getMedicines().equals(medicines));
        assert(treatmentProtocol.getNotes().equals(notes));
    }

    @Test
    public void testMedicines() throws Exception {
        ArrayList<Medicine> new_medicines = new ArrayList<>();
        new_medicines.add(new Medicine("new Medicine",10.3,"L",45,"new notes"));
        treatmentProtocol.setMedicine(new_medicines);
        assert(treatmentProtocol.getMedicines().equals(new_medicines));
    }

    @Test
    public void testNotes() throws Exception {
        String new_notes = "new Notes";
        treatmentProtocol.setNotes(new_notes);
        assert(treatmentProtocol.getNotes().equals(new_notes));
    }

    @After
    public void tearDown() throws Exception {

    }

}