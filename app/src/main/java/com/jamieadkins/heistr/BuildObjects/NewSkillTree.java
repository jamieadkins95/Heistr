package com.jamieadkins.heistr.BuildObjects;

import com.jamieadkins.heistr.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class NewSkillTree {
    long skillBuildID = -1;
    ArrayList<NewSkillSubTree> subTrees;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewSkillTree() {
        subTrees = new ArrayList<NewSkillSubTree>();
    }

    public static NewSkillTree newNonDBInstance() {
        NewSkillTree skillTree = new NewSkillTree();
        for (int i = 0; i < Trees.SUBTREES_PER_TREE; i++) {
            skillTree.getSubTrees().add(NewSkillSubTree.newNonDBInstance(i));
        }

        return skillTree;
    }

    @Override
    public String toString() {
        String text = name + " tree:";
        for (NewSkillSubTree t : subTrees) {
            text += "\n" + t.toString();
        }
        return text;
    }

    public ArrayList<NewSkillSubTree> getSubTrees() {
        return subTrees;
    }

    public long getSkillBuildID() {
        return skillBuildID;
    }

    public void setSkillBuildID(long skillBuildID) {
        this.skillBuildID = skillBuildID;
    }

    public int getSkillCount() {
        int count = 0;
        for (NewSkillSubTree tier : subTrees) {
            for (Skill skill : tier.getSkillsInTier()) {
                count += skill.getTaken();
            }

        }
        return count;
    }

    public ArrayList<NewSkillSubTree> getTierListInDescendingOrder() {
        ArrayList<NewSkillSubTree> descending = new ArrayList<>();
        for (int tier = subTrees.size() - 1; tier > 0; tier--) {
            descending.add(subTrees.get(tier));

        }

        return descending;
    }

    public int getPointsSpentInThisTree() {
        return getPointsSpentInThisTree(7);
    }

    public int getPointsSpentInThisTree(int upToTier) {
        int total = 0;
        for (int i = 0; i < upToTier; i++) {
            NewSkillSubTree tier = subTrees.get(i);
            for (Skill skill : tier.getSkillsInTier()) {
                switch (skill.getTaken()) {
                    case Skill.NORMAL:
                        total += skill.getNormalPoints();
                        break;
                    case Skill.ACE:
                        total += skill.getNormalPoints() + skill.getAcePoints();
                        break;
                }
            }
        }

        return total;
    }
}
