package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class IllnessTest {

    private Illness illness;
    private String illnessName;
    private Treatment_Protocol treatmentProtocol;

    @Before
    public void setUp() throws Exception {
        illnessName = "Illness1";
        treatmentProtocol = new Treatment_Protocol(new ArrayList<Medicine>(),"notes");

        illness = new Illness(illnessName, treatmentProtocol);
    }

    @Test
    public void testAddIllness() throws Exception {
        assert(illness.getName().equals(illnessName));
        assert(illness.getTreatmentProtocol().equals(treatmentProtocol));
    }

    @Test
    public void testIllnessName() throws Exception {
        String new_illnessName = "new Illness1";
        illness.setName(new_illnessName);
        assert(illness.getName().equals(new_illnessName));
    }

    @Test
    public void testTreatmentProtocol() throws Exception {
        Treatment_Protocol new_treatmentProtocol = new Treatment_Protocol(new ArrayList<Medicine>(), "new Notes");
        illness.setTreatmentProtocol(new_treatmentProtocol);
        assert(illness.getTreatmentProtocol().equals(new_treatmentProtocol));
    }

    @After
    public void tearDown() throws Exception {

    }


}