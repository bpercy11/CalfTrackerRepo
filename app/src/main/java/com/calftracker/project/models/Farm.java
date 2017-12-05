package com.calftracker.project.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BrendonLapp on 10/30/17.
 */


public class Farm {
    private String name;
    private String owner;
    private String location;
    private int passcode;
    private HashMap<Integer, Calf> calfProfileMap;
    //change to arrayList possibly
    private Integer currInternalID;
    //TODO Medical_Procedures
    private ArrayList<Employee> employees;


    /***
     *
     * @param name
     * @param owner
     * @param location
     */
    public Farm(String name, String owner, String location){
        this.name = name;
        this.owner = owner;
        this.location = location;

        this.currInternalID = 0;
        passcode = -1;
        //employees = new ArrayList<Employee>();
        calfProfileMap = new HashMap<Integer, Calf>();

        //TODO set up anything else we need
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getLocation() {
        return this.location;
    }

    /***
     *
     * @param passcode
     */
    public void setPasscode(int passcode) { this.passcode = passcode; }

    /**
     *
     * @param passcode
     * @return
     */
    public boolean checkPasscode(int passcode) { return this.passcode == passcode; }

    /**
     *
     * @return
     */
    public HashMap getCalfProfileMap() { return this.calfProfileMap; }

    /**
     *
     * @param calf
     */
    public void addCalf(Calf calf) { calfProfileMap.put(currInternalID++ , calf); }

    //public void removeCalf()

    /**
     *
     * @param
     */
    public void setEmployeeList(ArrayList<Employee> list) { this.employees = list; }

    /**
     *
     * @param employee
     */
    public void removeEmployee(Employee employee) { this.employees.remove(employee); }

    /**
     *
     * @return
     */
    public ArrayList getEmployees() { return this.employees; }


}
