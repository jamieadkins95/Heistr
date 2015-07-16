package com.dawgandpony.pd2skills.BuildObjects;

import com.dawgandpony.pd2skills.BuildObjects.Skill;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class Tier {
    long id = -1;
    long skillBuildID = -1;
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

    public long getSkillBuildID() {
        return skillBuildID;
    }

    public void setSkillBuildID(long skillBuildID) {
        this.skillBuildID = skillBuildID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
