package com.dawgandpony.pd2skills;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class Tier {
    ArrayList<Skill> skillsInTier;
    int pointRequirement;
    int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String text = "Tier " + number;
        for (Skill s : skillsInTier){
            text += "\n" + s.toString();
        }
        return text;
    }

    public Tier() {
        skillsInTier = new ArrayList<Skill>();
    }

    public void setPointRequirement(int pointRequirement) {
        this.pointRequirement = pointRequirement;
    }

    public ArrayList<Skill> getSkillsInTier() {

        return skillsInTier;
    }

    public int getPointRequirement() {
        return pointRequirement;
    }
}
