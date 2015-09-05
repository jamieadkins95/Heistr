package com.dawgandpony.pd2skills.utils;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 05/09/2015.
 */
public class URLEncoder {

    public static String EncodeBuild(Build build){
        String url = "http://pd2skills.com/#/v3/";

        SkillBuild skillBuild = build.getSkillBuild();

        //region Skills
        int treeNumber = Trees.MASTERMIND;
        for (SkillTree tree : skillBuild.getSkillTrees()){
            switch (treeNumber){
                case Trees.MASTERMIND:
                    url += "m";
                    break;
                case Trees.ENFORCER:
                    url += "e";
                    break;
                case Trees.TECHNICIAN:
                    url += "t";
                    break;
                case Trees.GHOST:
                    url += "g";
                    break;
                case Trees.FUGITIVE:
                    url += "f";
                    break;
            }

            ArrayList<SkillTier> tiers = tree.getTierListInDescendingOrder();
            tiers.add(tree.getTierList().get(0));

            for (SkillTier tier : tiers){
                for (Skill skill : tier.getSkillsInTier()){
                    if (skill.getTaken() == Skill.NORMAL){
                        url += skill.getPd2SkillsSymbol();
                    }
                    else if (skill.getTaken() == Skill.ACE){
                        url += skill.getPd2SkillsSymbol().toUpperCase();
                    }

                }
            }
            url += ":";
            treeNumber++;
        }
        //endregion

        //region Perkdeck
        url+= "p:";
        //endregion

        return url;
    }

    public static Build DecodeURL(String url){
        Build b = new Build();

        return b;
    }


}
