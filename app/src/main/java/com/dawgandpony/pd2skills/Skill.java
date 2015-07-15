package com.dawgandpony.pd2skills;

import com.dawgandpony.pd2skills.Consts.Taken;

/**
 * Created by Jamie on 15/07/2015.
 */
public class Skill {
    private String name;
    private String normalDescription;
    private String aceDescription;
    private int normalPoints;
    private int acePoints;
    private int taken = Taken.NO;

    public int getNormalPoints() {
        return normalPoints;
    }

    public void setNormalPoints(int normalPoints) {
        this.normalPoints = normalPoints;
    }

    public int getAcePoints() {
        return acePoints;
    }

    public void setAcePoints(int acePoints) {
        this.acePoints = acePoints;
    }



    public String getName() {
        return name;
    }

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }

    public String getNormalDescription() {
        return normalDescription;
    }

    public void setNormalDescription(String normalDescription) {
        this.normalDescription = normalDescription;
    }

    public String getAceDescription() {
        return aceDescription;
    }

    public void setAceDescription(String aceDescription) {
        this.aceDescription = aceDescription;
    }

    public void setName(String name) {

        this.name = name;
    }
}


