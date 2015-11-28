package com.dawgandpony.pd2skills.BuildObjects;

/**
 * Created by Jamie on 28/11/2015.
 */
public class SkillStatModifier {

    private float damage;
    private float damageMult;

    private int weaponType;


    public static SkillStatModifier getGunsligerAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damage = 15;
        modifier.weaponType = SkillStatChangeManager.WEAPON_TYPE_PISTOL;
        return modifier;
    }

    public static SkillStatModifier getShotgunImpact() {
        return null;
    }

    public static SkillStatModifier getShotgunImpactAced() {
        return null;
    }

    public static SkillStatModifier getPerkDeckBonus() {
        return null;
    }

    public static SkillStatModifier getSilentKiller() {
        return null;
    }

    public static SkillStatModifier getSilentKillerAced() {
        return null;
    }

    public static SkillStatModifier getLeadership() {
        return null;
    }

    public static SkillStatModifier getLeadershipAced() {
        return null;
    }

    public static SkillStatModifier getEquilibrium() {
        return null;
    }

    public static SkillStatModifier getEquilibriumAced() {
        return null;
    }

    public static SkillStatModifier getFullyLoaded() {
        return null;
    }

    public static SkillStatModifier getFullyLoadedAced() {
        return null;
    }

    public static SkillStatModifier getSharpshooter() {
        return null;
    }

    public static SkillStatModifier getSharpshooterAced() {
        return null;
    }

    public static SkillStatModifier getMagPlus() {
        return null;
    }

    public static SkillStatModifier getMagPlusAced() {
        return null;
    }

    public static SkillStatModifier getSMGSpecialist() {
        return null;
    }

    public static SkillStatModifier getSMGSpecialistAced() {
        return null;
    }

    public static SkillStatModifier getProfessional() {
        return null;
    }

    public static SkillStatModifier getProfessionalAced() {
        return null;
    }

    public static SkillStatModifier getAkimbo() {
        return null;
    }

    public static SkillStatModifier getAkimboAced() {
        return null;
    }
}
