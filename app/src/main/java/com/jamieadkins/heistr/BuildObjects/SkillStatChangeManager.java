package com.jamieadkins.heistr.BuildObjects;

import com.jamieadkins.heistr.Consts.Trees;

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
    protected static int WEAPON_TYPE_SINGLE_SHOT = 14;

    protected static int WEAPON_TYPE_ALL = 99;


    private ArrayList<SkillStatModifier> modifiers;

    public ArrayList<SkillStatModifier> getModifiers() {
        return modifiers;
    }

    public static SkillStatChangeManager getInstance(SkillBuild skillBuild) {
        return new SkillStatChangeManager(skillBuild);
    }

    private SkillStatChangeManager(SkillBuild skillBuild) {
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
                            if (tier.getSkillTree() == Trees.TECHNICIAN) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getSharpshooter());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getSharpshooterAced());
                                }
                            }
                            break;
                        case "f":
                            break;
                        case "g":
                            if (tier.getSkillTree() == Trees.MASTERMIND) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getLeadership());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getLeadershipAced());
                                }
                            }
                            break;
                        case "h":
                            if (tier.getSkillTree() == Trees.ENFORCER) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getShotgunImpact());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getShotgunImpactAced());
                                }
                            }
                            break;
                        case "i":
                            if (tier.getSkillTree() == Trees.MASTERMIND) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getEquilibrium());
                                }
                            }
                            break;
                        case "j":
                            if (tier.getSkillTree() == Trees.GHOST) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getSMGSpecialistAced());
                                }
                            }
                            break;
                        case "k":
                            break;
                        case "l":
                            break;
                        case "m":
                            if (tier.getSkillTree() == Trees.GHOST) {
                                if (skill.getTaken() == Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getSilentKiller());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getSilentKillerAced());
                                }
                            }
                            break;
                        case "n":
                            break;
                        case "o":
                            if (tier.getSkillTree() == Trees.MASTERMIND) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getGunsligerAced());
                                }
                            }
                            if (tier.getSkillTree() == Trees.ENFORCER) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getFullyLoaded());
                                }
                            }
                            break;
                        case "p":
                            if (tier.getSkillTree() == Trees.GHOST) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getProfessional());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getProfessionalAced());
                                }
                            }
                            break;
                        case "q":
                            break;
                        case "r":
                            if (tier.getSkillTree() == Trees.TECHNICIAN) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getMagPlus());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getMagPlusAced());
                                }
                            }
                            break;
                        case "s":
                            if (tier.getSkillTree() == Trees.FUGITIVE) {
                                if (skill.getTaken() == Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getAkimbo());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getAkimboAced());
                                }
                            }
                            break;
                    }
                }
            }
        }*/

        modifiers.add(SkillStatModifier.getPerkDeckBonus());
    }
}
