package com.jamieadkins.heistr.BuildObjects;

import com.jamieadkins.heistr.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class NewSkillSubTree {
    long id = -1;
    long skillBuildID = -1;
    ArrayList<Skill> skillsInSubTree;
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
        String text = "Subtree " + subTree;
        for (Skill s : skillsInSubTree) {
            text += "\n" + s.toString();
        }
        return text;
    }

    public NewSkillSubTree() {
        skillsInSubTree = new ArrayList<Skill>();
    }

    public static NewSkillSubTree newNonDBInstance(int tierNumber) {
        NewSkillSubTree subTree = new NewSkillSubTree();
        for (int i = 0; i < Trees.SKILLS_PER_SUBTREE; i++) {
            subTree.getSkillsInSubTree().add(new Skill());
        }

        return subTree;
    }

    public ArrayList<Skill> getSkillsInSubTree() {

        return skillsInSubTree;
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
        for (Skill skill : skillsInSubTree) {
            skill.setTaken(Skill.NO);
        }
    }

    public int getPointsSpentInThisTree() {
        return getPointsSpentInThisTree(Trees.TIERS_PER_SUBTREE + 1);
    }

    public int getPointsSpentInThisTree(int upToTier) {
        int total = 0;
        for (Skill skill : skillsInSubTree) {
            if (skill.getTier() < upToTier) {
                switch (skill.getTaken()) {
                    case Skill.NORMAL:
                        total += skill.getNormalCost();
                        break;
                    case Skill.ACE:
                        total += skill.getNormalCost() + skill.getAceCost();
                        break;
                }
            }
        }

        return total;
    }
}
