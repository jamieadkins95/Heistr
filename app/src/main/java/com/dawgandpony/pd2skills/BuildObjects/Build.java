package com.dawgandpony.pd2skills.BuildObjects;

/**
 * Created by Jamie on 14/07/2015.
 */
public class Build {

    String name = "";



    SkillBuild skillBuild;

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
}
