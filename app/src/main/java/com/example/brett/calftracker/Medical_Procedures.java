package com.example.brett.calftracker;

import java.util.ArrayList;

/**
 * Created by AlexanderGlowacki on 10/27/17.
 */

public class Medical_Procedures {

    ArrayList<Vaccine> Vaccines = new ArrayList<Vaccine>();
    ArrayList<Illness> Illnesses = new ArrayList<Illness>();

    public class Vaccine extends Medical_Procedures{
        String Name;
        Double Dosage;
        String Dosage_Units;
        String Method_of_Administration;

        /*public Vaccine createVaccine(String Name, ArrayList<Vacc_Range> Vacc_Range, Double Dosage,
                                     String Dosage_Units, String Method_of_Administration){
            return Vaccine;
        }*/

    }
    public class Vacc_Range extends Vaccine{
        Integer Span;

        /*public Vacc_Range createVaccRange(Integer Span){
            return Vacc_Range;
        }*/
    }

    public class Illness extends Medical_Procedures{
        String Name;
        Treatment_Protocol Treatment_Protocol;

    }

    public class Treatment_Protocol extends Illness{
        ArrayList<Medicine> Medicines = new ArrayList<Medicine>();
    }

    public class Medicine extends Treatment_Protocol{
        String Name;
        Double Dosage;
        Integer Time_Interval_Administered_Days;
        Integer Number_of_Times_To_Adminster;

        /*public Medicine createMedicine(String Name, Double Dosage, Integer
                Time_Interval_Administered_Days, Integer Number_of_Times_To_Adminster){
            return Medicine;
        }*/
    }

    public void addVaccine(Vaccine vaccine){
        Vaccines.add(vaccine);
    }

    public void addIllness(Illness illness){
        Illnesses.add(illness);
    }

    public void removeVaccine(Vaccine vaccine){
        Vaccines.remove(vaccine);
    }

    public void removeIllness(Illness illness){
        Illnesses.remove(illness);
    }

}
