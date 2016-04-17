package com.jamieadkins.heistr.BuildObjects;

import com.jamieadkins.heistr.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class NewSkillSubTree {
    long id = -1;
    long skillBuildID = -1;
    ArrayList<Skill> skillsInTier;
    int pointRequirement;
    int skillTree;
    int subTree;

    public int getSubTree() {
        return subTree;
    }

    public void setSubTree(int subTree) {
        this.subTree = subTree;
    }

    @Override
    public String toString() {
        String text = "Tier " + subTree;
        for (Skill s : skillsInTier) {
            text += "\n" + s.toString();
        }
        return text;
    }

    public NewSkillSubTree() {
        skillsInTier = new ArrayList<Skill>();
    }

    public static NewSkillSubTree newNonDBInstance(int tierNumber) {
        NewSkillSubTree subTree = new NewSkillSubTree();
        for (int i = 0; i < Trees.SKILLS_PER_SUBTREE; i++) {
            subTree.getSkillsInTier().add(new Skill());
        }

        return subTree;
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
            if (subTree >= 3) {
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

    public int getSkillTree() {
        return skillTree;
    }

    public void setSkillTree(int skillTree) {
        this.skillTree = skillTree;
    }

    public void resetSkills() {
        for (Skill skill : skillsInTier) {
            skill.setTaken(Skill.NO);
        }
    }
}
