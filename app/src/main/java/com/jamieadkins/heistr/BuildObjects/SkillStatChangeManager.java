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

    protected static int WEAPON_TYPE_LMG = 15;
    protected static int WEAPON_TYPE_MELEE = 16;
    protected static int BALLISTIC_VESTS = 17;

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
        for (NewSkillTree tree : skillBuild.getNewSkillTrees()) {
            for (NewSkillSubTree subTree : tree.getSubTrees()) {
                for (Skill skill : subTree.getSkillsInSubTree()) {
                    switch (skill.getPd2SkillsSymbol()) {
                        case "a":
                            if (subTree.getSkillTree() == Trees.FUGITIVE
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getEquilibriumAced());
                                }
                            }
                            break;
                        case "b":
                            if (subTree.getSkillTree() == Trees.FUGITIVE
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getGunNut());
                                }
                            }

                            if (subTree.getSkillTree() == Trees.ENFORCER
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getClosebyAced());
                                }
                            }
                            break;
                        case "c":
                            if (subTree.getSkillTree() == Trees.FUGITIVE
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getCustomAmmo());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getCustomAmmoAced());
                                }
                            }
                            break;
                        case "d":
                            if (subTree.getSkillTree() == Trees.ENFORCER
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() == Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getShotgunImpactStability());
                                    modifiers.add(SkillStatModifier.getShotgunImpact5());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getShotgunImpactStability());
                                    modifiers.add(SkillStatModifier.getShotgunImpact15());
                                }
                            }


                            if (subTree.getSkillTree() == Trees.FUGITIVE
                                    && subTree.getSubTree() == 0) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getAkimbo());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getAkimboAced());
                                }
                            }
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
                            if (subTree.getSkillTree() == Trees.GHOST
                                    && subTree.getSubTree() == 1) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getInnerPockets());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getInnerPocketsAced());
                                }
                            }
                            break;
                        case "k":
                            break;
                        case "l":
                            break;
                        case "m":
                            if (subTree.getSkillTree() == Trees.ENFORCER
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getFullyLoaded());
                                }
                            }
                            break;
                        case "n":
                            if (subTree.getSkillTree() == Trees.TECHNICIAN
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getSureFire());
                                }
                            }
                            if (subTree.getSkillTree() == Trees.GHOST
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getSpecialisedKilling());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getSpecialisedKillingAced());
                                }
                            }
                            break;
                        case "o":
                            break;
                        case "p":
                            if (subTree.getSkillTree() == Trees.MASTERMIND
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getMarksman());
                                }
                            }

                            if (subTree.getSkillTree() == Trees.GHOST
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getProfessional());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getProfessionalAced());
                                }
                            }
                            break;
                        case "q":
                            if (subTree.getSkillTree() == Trees.GHOST
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getOpticIllusionsAced());
                                }
                            }
                            break;
                        case "r":
                            if (subTree.getSkillTree() == Trees.MASTERMIND
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getStableShot());
                                }
                            }

                            if (subTree.getSkillTree() == Trees.TECHNICIAN
                                    && subTree.getSubTree() == 2) {
                                if (skill.getTaken() >= Skill.NORMAL) {
                                    modifiers.add(SkillStatModifier.getSteadyGrip());
                                }
                                if (skill.getTaken() == Skill.ACE) {
                                    modifiers.add(SkillStatModifier.getSteadyGripAced());
                                }
                            }
                            break;
                    }
                }
            }
        }

        modifiers.add(SkillStatModifier.getPerkDeckBonus());
    }
}
