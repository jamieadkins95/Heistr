package com.jamieadkins.heistr.BuildObjects;


/**
 * Created by Jamie on 15/07/2015.
 */
public class Skill {
    private String name = "";
    private String normalDescription = "";
    private String aceDescription = "";
    private String abbreviation = "";
    private String pd2SkillsSymbol = "";
    private int normalPoints = 0;
    private int acePoints = 0;
    private int taken = 0;

    public final static int NO = 0;
    public final static int NORMAL = 1;
    public final static int ACE = 2;

    public Skill() {
        name = "";
        normalDescription = "";
        aceDescription = "";
        normalPoints = 0;
        acePoints = 0;
        taken = NO;
        pd2SkillsSymbol = "";
    }

    @Override
    public String toString() {
        String text = "Name: " + name + "\nNormal: " + normalDescription + "\nAce: " + aceDescription + "\nTaken: ";


        switch (taken) {
            case NO:
                text += "no";
                break;
            case NORMAL:
                text += "normal";
                break;
            case ACE:
                text += "ace";
                break;

        }

        return text;
    }

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

    public String getPd2SkillsSymbol() {
        return pd2SkillsSymbol;
    }

    public void setPd2SkillsSymbol(String pd2SkillsSymbol) {
        this.pd2SkillsSymbol = pd2SkillsSymbol;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}


