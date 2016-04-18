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
        for (NewSkillSubTree subtree : subTrees) {
            for (Skill skill : subtree.getSkillsInSubTree()) {
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
}
