package com.dawgandpony.pd2skills.BuildObjects;

/**
 * Created by Jamie on 14/07/2015.
 */
public class Build {

    long id;
    String name = "";
    long skillBuildID;
    SkillBuild skillBuild;

    int perkDeck;
    int armour;



    public Build() {

    }



    public String getName() {
        return name;
    }

    public SkillBuild getSkillBuild() {
        return skillBuild;
    }

    public void setSkillBuild(SkillBuild skillBuild) {
        this.skillBuild = skillBuild;
    }

    public int getPerkDeck() {
        return perkDeck;
    }

    public void setPerkDeck(int perkDeck) {
        this.perkDeck = perkDeck;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSkillBuildID() {
        return skillBuildID;
    }

    public void setSkillBuildID(long skillBuildID) {
        this.skillBuildID = skillBuildID;
    }
}
