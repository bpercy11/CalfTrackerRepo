package com.example.brett.calftracker;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AlexanderGlowacki on 10/27/17.
 */

public class Medical_Procedures {

    private ArrayList<Vaccine> vaccines = new ArrayList<>();
    private ArrayList<Illness> illnesses = new ArrayList<>();

    public class Vaccine extends Medical_Procedures{
        String name;
        Double dosage;
        String dosageUnits;
        ArrayList<Vacc_Range> toBeAdministeredAt;
        String methodOfAdministration;

        public Vaccine(){}

        public Vaccine(String name, Double dosage, String dosageUnits, ArrayList<Vacc_Range>
                toBeAdministeredAt, String methodOfAdministration){
            this.name = name;
            this.dosage = dosage;
            this.dosageUnits = dosageUnits;
            this.toBeAdministeredAt = new ArrayList<>();
            this.methodOfAdministration = methodOfAdministration;
        }

        public Vaccine createVaccine(String name, Double dosage, String dosageUnits,
                                     ArrayList<Vacc_Range> toBeAdministeredAt, String methodOfAdministration){
            return new Vaccine(name,dosage,dosageUnits,toBeAdministeredAt,methodOfAdministration);
        }

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public Double getDosage(){
            return dosage;
        }
        public void setDosage(Double dosage){
            this.dosage = dosage;
        }
        public String getDosageUnits(){
            return dosageUnits;
        }
        public void setDosageUnits(String dosageUnits){
            this.dosageUnits = dosageUnits;
        }
        public ArrayList<Vacc_Range> getToBeAdministeredAt(){
            return toBeAdministeredAt;
        }
        public void setToBeAdministeredAt(ArrayList<Vacc_Range> toBeAdministeredAt){
            this.toBeAdministeredAt = toBeAdministeredAt;
        }
        public String getMethodOfAdministration(){
            return methodOfAdministration;
        }
        public void setMethodOfAdministration(String methodOfAdministration) {
            this.methodOfAdministration = methodOfAdministration;
        }

    }
    public class Vacc_Range extends Vaccine{
        Integer[] span;

        public Vacc_Range(Integer[] span){
            this.span = new Integer[2];
        }

        public Integer[] getSpan(){
            return span;
        }
        public void setSpan(Integer[] span){
            this.span = span;
        }

        public Vacc_Range createVaccRange(Integer[] span){
            return new Vacc_Range(span);
        }

    }

    public class Illness extends Medical_Procedures{
        String name;
        Treatment_Protocol treatmentProtocol;

        public Illness(String name, Treatment_Protocol treatmentProtocol){
            this.name = name;
            this.treatmentProtocol = treatmentProtocol;
        }

        // default constructor
        public Illness(){}

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public Treatment_Protocol getTreatmentProtocol(){
            return treatmentProtocol;
        }
        public void setTreatmentProtocol(Treatment_Protocol treatmentProtocol){
            this.treatmentProtocol = treatmentProtocol;
        }

        public Illness createIllness(String name, Treatment_Protocol treatmentProtocol){
            return new Illness(name,treatmentProtocol);
        }

    }

    public class Treatment_Protocol extends Illness{
        ArrayList<Medicine> medicines;
        String notes;

        public Treatment_Protocol(ArrayList<Medicine> medicines,String notes){
            this.medicines = new ArrayList<>();
            this.notes = notes;
        }

        // default no-arg constructor
        public Treatment_Protocol() {}

        public ArrayList<Medicine> getMedicines(){
            return medicines;
        }
        public void setMedicines(ArrayList<Medicine> medicines){
            this.medicines = medicines;
        }
        public String getNotes(){
            return notes;
        }
        public void setNotes(String notes){
            this.notes = notes;
        }

        public Treatment_Protocol createTreatmentProtocol(ArrayList<Medicine> medicines, String notes){
            return new Treatment_Protocol(medicines,notes);
        }

        public void addMedicine(Medicine medicine){
            medicines.add(medicine);
        }

    }

    public class Medicine extends Treatment_Protocol{
        String name;
        Double dosage;
        Integer timeInterval;
        Integer[] timesToAdminister; // array of size 2

        public Medicine(String name, Double dosage, Integer timeInterval, Integer[] timesToAdminister){
            this.name = name;
            this.dosage = dosage;
            this.timeInterval = timeInterval;
            // array[0] is minimum number of days to administer medicine
            // array[1] is optional number of days to administer after minimum
            this.timesToAdminister = new Integer[2];
        }

        public Medicine createMedicine(String name, Double dosage, Integer timeInterval, Integer[] timesToAdminister){
            return new Medicine(name,dosage,timeInterval,timesToAdminister);
        }
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
