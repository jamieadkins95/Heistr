package com.jamieadkins.heistr.BuildObjects;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillTier {
    long id = -1;
    long skillBuildID = -1;
    ArrayList<Skill> skillsInTier;
    int pointRequirement;
    int normalCost;
    int aceCost;
    int skillTree;
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
        for (Skill s : skillsInTier) {
            text += "\n" + s.toString();
        }
        return text;
    }

    public SkillTier() {
        skillsInTier = new ArrayList<Skill>();
    }

    public static SkillTier newNonDBInstance(int tierNumber) {
        SkillTier skillTier = new SkillTier();
        if (tierNumber == 0) {
            skillTier.getSkillsInTier().add(new Skill());
        } else {
            for (int i = 0; i < 3; i++) {
                skillTier.getSkillsInTier().add(new Skill());
            }
        }


        return skillTier;
    }

    public void setPointRequirement(int pointRequirement) {
        this.pointRequirement = pointRequirement;
    }

    public ArrayList<Skill> getSkillsInTier() {

        return skillsInTier;
    }

    public int getPointRequirement(boolean infamyInThisTree) {
        int points = pointRequirement;
        if (infamyInThisTree) {
            if (number >= 3) {
                int deduction = points / 10;
                points -= deduction;
            }
        }

        return points;
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

    public int getSkillTree() {
        return skillTree;
    }

    public void setSkillTree(int skillTree) {
        this.skillTree = skillTree;
    }

    public void ResetSkills() {
        for (Skill skill : skillsInTier) {
            skill.setTaken(Skill.NO);
        }
    }
}
