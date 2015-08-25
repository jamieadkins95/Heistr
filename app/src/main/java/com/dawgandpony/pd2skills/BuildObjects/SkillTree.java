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

    public ArrayList<SkillTier> getTierListInDescendingOrder(){
        ArrayList<SkillTier> descending = new ArrayList<>();
        for (int tier = skillTiers.size() - 1; tier > 0; tier--){
            descending.add(skillTiers.get(tier));

        }

        return descending;
    }

    public int getPointsSpentInThisTree(){
        int total = 1;
        for (SkillTier tier : skillTiers){
            for (Skill skill : tier.getSkillsInTier()){
                switch (skill.getTaken()){
                    case Skill.NORMAL:
                        total += tier.getNormalCost();
                        break;
                    case Skill.ACE:
                        total += tier.getAceCost();
                        break;
                }
            }
        }

        return total;
    }
    public int getPointsSpentInThisTree(int upToTier){
        int total = 1;
        for (int i = 0; i < upToTier; i++){
            SkillTier tier =skillTiers.get(i);
            for (Skill skill : tier.getSkillsInTier()){
                switch (skill.getTaken()){
                    case Skill.NORMAL:
                        total += tier.getNormalCost();
                        break;
                    case Skill.ACE:
                        total += tier.getAceCost();
                        break;
                }
            }
        }

        return total;
    }
}
