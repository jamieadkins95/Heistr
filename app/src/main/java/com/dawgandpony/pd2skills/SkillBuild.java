package com.dawgandpony.pd2skills;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillBuild {
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
}
