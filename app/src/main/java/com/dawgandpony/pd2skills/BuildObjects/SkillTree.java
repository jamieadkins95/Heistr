package com.dawgandpony.pd2skills.BuildObjects;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillTree {
    long skillBuildID = -1;


    ArrayList<SkillTier> skillTiers;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTree(){
        skillTiers = new ArrayList<SkillTier>();
    }

    @Override
    public String toString() {
        String text = name + " tree:";
        for (SkillTier t : skillTiers){
            text += "\n" + t.toString();
        }
        return text;
    }

    public ArrayList<SkillTier> getTierList() {
        return skillTiers;
    }

    public long getSkillBuildID() {
        return skillBuildID;
    }

    public void setSkillBuildID(long skillBuildID) {
        this.skillBuildID = skillBuildID;
    }

    public int getSkillCount() {
        int count = 0;
        for (SkillTier tier : skillTiers){
            for (Skill skill : tier.getSkillsInTier())
            {
                count += skill.getTaken();
            }

        }
        return count;
    }
}
