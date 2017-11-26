package com.calftracker.project.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AlexanderGlowacki on 11/25/17.
 */
public class Vacc_RangeTest {

    private int[] span;
    private Vacc_Range vacc_range;

    @Before
    public void setUp() throws Exception {
        span = new int[] {2,5};

        vacc_range = new Vacc_Range(span);
    }

    @Test
    public void testAddVaccRange() throws Exception {
        assert(vacc_range.getSpan().equals(span));
    }

    @Test
    public void testSpan() throws Exception {
        int[] new_span = new int[] {4,5};
        vacc_range.setSpan(new_span);
        assert(vacc_range.getSpan().equals(new_span));
    }

    @After
    public void tearDown() throws Exception {

    }

}