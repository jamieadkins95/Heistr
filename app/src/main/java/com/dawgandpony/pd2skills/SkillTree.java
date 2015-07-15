package com.dawgandpony.pd2skills;

import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 15/07/2015.
 */
public class SkillTree {
    ArrayList<Tier> tiers;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTree(){
        tiers = new ArrayList<Tier>();
    }

    @Override
    public String toString() {
        String text = name + " tree:";
        for (Tier t : tiers){
            text += "\n" + t.toString();
        }
        return text;
    }

    public ArrayList<Tier> getTierList() {
        return tiers;
    }
}
