package com.calftracker.project.models;

import java.util.ArrayList;

/**
 * Created by Lisa on 10/31/2017.
 */

public class Medical_Procedures {

    private ArrayList<Vaccine> vaccines;
    private ArrayList<Illness> illnesses;

    public Medical_Procedures(){
       this.vaccines = new ArrayList<>();
       this.illnesses = new ArrayList<>();
    }

    public Medical_Procedures(ArrayList<Vaccine> vaccines, ArrayList<Illness> illnesses){
        this.vaccines = vaccines;
        this.illnesses = illnesses;
    }

    public ArrayList<Vaccine> getVaccines(){
        return vaccines;
    }
    public void setVaccines(ArrayList<Vaccine> vaccines){
        this.vaccines = vaccines;
    }
    public ArrayList<Illness> getIllnesses(){
        return illnesses;
    }
    public void setIllnesses(ArrayList<Illness> illnesses){
        this.illnesses = illnesses;
    }

    public void addVaccine(Vaccine vaccine){
        vaccines.add(vaccine);
    }

    public void addIllness(Illness illness){
        illnesses.add(illness);
    }

    public void removeVaccine(Vaccine vaccine){ vaccines.remove(vaccine); }

    public void removeIllness(Illness illness){
        illnesses.remove(illness);
    }
}
