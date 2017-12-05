package com.calftracker.project.models;

/**
 * Created by JT on 11/26/2017.
 */

public class Sire {
    private String name;
    private String id;

    public Sire(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Sire(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
