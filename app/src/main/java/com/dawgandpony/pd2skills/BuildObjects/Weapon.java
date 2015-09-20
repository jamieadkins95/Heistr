package com.dawgandpony.pd2skills.BuildObjects;

/**
 * Created by Jamie on 20/09/2015.
 */
public class Weapon {

    long id = -1;
    long pd2skillsID = -1;
    String name = "Prim";


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPd2skillsID() {
        return pd2skillsID;
    }

    public void setPd2skillsID(long pd2skillsID) {
        this.pd2skillsID = pd2skillsID;
    }
}
