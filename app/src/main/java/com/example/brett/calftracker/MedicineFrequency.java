package com.example.brett.calftracker;

/**
 * Created by AlexanderGlowacki on 11/9/17.
 */

public class MedicineFrequency {
    private int[] span;

    /**
     * @param span
     */
    public MedicineFrequency(int[] span) {
        super();
        this.span = span;
    }

    /**
     * @return the span
     */
    public int[] getSpan() {
        return span;
    }

    /**
     * @param span the span to set
     */
    public void setSpan(int[] span) {
        this.span = span;
    }
}

