package com.dawgandpony.pd2skills.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    final private static String baseURL = "http://pd2skills.com/#/v3/";

    public static String encodeBuild(Context context, Build build){
        String url = baseURL;

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

    public static Build decodeURL(Context context, String url){
        Build b = new Build();
        String base = url.substring(0, baseURL.length());
        String remaining = url.substring(base.length());

        if (base.equals(baseURL)){
            Log.d("URL DECODER", remaining);

            while (!remaining.equals("")){
                switch (remaining.charAt(0)){
                    case 'm':
                        //mastermind tree
                        break;
                    case 'e':
                        //enforcer
                        break;
                    case 't':
                        //tech
                        break;
                    case 'g':
                        //ghost
                        break;
                    case 'f':
                        //fugitive
                        break;
                    case 'i':
                        //infamy
                        break;
                    case 'p':
                        //perkdeck
                        break;
                    case 'a':
                        //armour
                        break;
                }
            }
        }
        else{
            b = null;
        }

        return b;
    }




}
