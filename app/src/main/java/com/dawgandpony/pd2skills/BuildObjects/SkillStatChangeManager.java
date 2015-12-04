package com.dawgandpony.pd2skills.BuildObjects;

import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 28/11/2015.
 */
public class SkillStatChangeManager {

    protected static int WEAPON_TYPE_ASSAULT = 0;
    protected static int WEAPON_TYPE_AKIMBO = 1;
    protected static int WEAPON_TYPE_PISTOL = 8;
    protected static int WEAPON_TYPE_SAW = 9;
    protected static int WEAPON_TYPE_SHOTGUN = 10;
    protected static int WEAPON_TYPE_SMG = 11;
    protected static int WEAPON_TYPE_SNIPER = 12;

    protected static int WEAPON_TYPE_SILENCED = 13;

    protected static int WEAPON_TYPE_ALL = 99;


    private ArrayList<SkillStatModifier> modifiers;

    public ArrayList<SkillStatModifier> getModifiers() {
        return modifiers;
    }

    public SkillStatChangeManager(SkillBuild skillBuild) {
        modifiers = new ArrayList<>();
        /*for (SkillTree tree : skillBuild.getSkillTrees()) {
            for (SkillTier tier : tree.getTierList()) {
                for (Skill skill : tier.getSkillsInTier()) {
                    switch (skill.getPd2SkillsSymbol()) {
                        case "a":
                            break;
                        case "b":
                            break;
                        case "c":
                            break;
                        case "d":
                            break;
                        case "e":
                            break;
                        case "f":
                            break;
                        case "g":
                            break;
                        case "h":
                            break;
                        case "i":
                            break;
                        case "j":
                            break;
                        case "k":
                            break;
                        case "l":
                            break;
                        case "m":
                            break;
                        case "n":
                            break;
                        case "o":
                            if (tier.getSkillTree() == Trees.MASTERMIND) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getGunsligerAced());
                                }
                            }
                            break;
                        case "p":
                            break;
                        case "q":
                            break;
                        case "r":
                            break;
                        case "s":
                            break;
                    }
                }
            }
        }*/
    }
}
