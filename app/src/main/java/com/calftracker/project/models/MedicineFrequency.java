package com.calftracker.project.models;

import java.util.ArrayList;

/**
 * Created by AlexanderGlowacki on 11/9/17.
 */

public class MedicineFrequency {
    private int[] span;
    private ArrayList<Integer> spanArrayList;

    /**
     * @param span
     */
    public MedicineFrequency(int[] span) {
        super();
        this.span = span;
        for(int i = 0; i < span.length; i++){
            spanArrayList.set(i, span[i]);
        }
    }

    /**
     * @return the span
     */
    public int[] getSpan() {

        span = new int[spanArrayList.size()];
        for(int i = 0; i < spanArrayList.size(); i++){
            span[i] = spanArrayList.get(i);
        }

        return span;
    }

    public ArrayList<Integer> getSpanArrayList(){ return spanArrayList; }

    /**
     * @param span the span to set
     */
    public void setSpan(int[] span) {

        this.span = span;
        for(int i = 0; i < span.length; i++){
            spanArrayList.set(i, span[i]);
        }
    }
}

