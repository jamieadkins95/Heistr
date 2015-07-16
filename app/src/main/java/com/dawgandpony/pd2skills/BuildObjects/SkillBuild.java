package com.dawgandpony.pd2skills.BuildObjects;

import java.util.ArrayList;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillBuild {
    long id = -1;

    ArrayList<SkillTree> skillTrees;
    int pointsUsed;
    int pointsAvailable;

    @Override
    public String toString() {
        String text = "";
        for (SkillTree st : skillTrees){
            text += "\n" + st.toString();
        }
        return text;
    }

    public ArrayList<SkillTree> getSkillTrees() {
        return skillTrees;
    }

    public SkillBuild(){
        skillTrees = new ArrayList<SkillTree>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
