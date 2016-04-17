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
    private int normalCost = 0;
    private int aceCost = 0;
    private int taken = 0;

    private int unlockRequirement = 0;
    private int tier = 0;
    private boolean locked = true;

    public final static int NO = 0;
    public final static int NORMAL = 1;
    public final static int ACE = 2;

    public final static boolean UNLOCKED = true;
    public final static boolean LOCKED = false;

    public Skill() {
        name = "";
        normalDescription = "";
        aceDescription = "";
        normalCost = 0;
        aceCost = 0;
        taken = NO;
        pd2SkillsSymbol = "";
        tier = 0;
        unlockRequirement = 0;
        locked = LOCKED;
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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getUnlockRequirement() {
        return unlockRequirement;
    }

    public void setUnlockRequirement(int unlockRequirement) {
        this.unlockRequirement = unlockRequirement;
    }

    public int getNormalCost() {
        return normalCost;
    }

    public void setNormalCost(int normalCost) {
        this.normalCost = normalCost;
    }

    public int getAceCost() {
        return aceCost;
    }

    public void setAceCost(int aceCost) {
        this.aceCost = aceCost;
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


