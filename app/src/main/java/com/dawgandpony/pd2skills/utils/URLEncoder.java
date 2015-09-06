package com.dawgandpony.pd2skills.utils;

import android.content.Context;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.R;

import java.util.ArrayList;

/**
 * Created by Jamie on 05/09/2015.
 */
public class URLEncoder {

    public static String EncodeBuild(Context context, Build build){
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

        //region Infamy
        url += "i";
        if (build.getInfamies().get(Trees.MASTERMIND)){
            url += "b";
        }
        if (build.getInfamies().get(Trees.ENFORCER)){
            url += "c";
        }
        if (build.getInfamies().get(Trees.TECHNICIAN)){
            url += "d";
        }
        if (build.getInfamies().get(Trees.GHOST)){
            url += "e";
        }
        url += "a:";
        //endregion

        //region Perkdeck
        String perkDeckShort = context.getResources().getStringArray(R.array.perkDecksURL)[build.getPerkDeck()];
        url+= "p" + perkDeckShort.toUpperCase()+"8::";
        //endregion


        //region Primary Weapon
        //url+="w2:";
        //endregion

        //region Secondary Weapon
        //url+="s7:";
        //endregion

        //region Armour
        url+="a" + context.getResources().getStringArray(R.array.armourURLNumbers)[build.getArmour()];
        //end region



        return url;
    }

    public static Build DecodeURL(String url){
        Build b = new Build();

        return b;
    }




}
