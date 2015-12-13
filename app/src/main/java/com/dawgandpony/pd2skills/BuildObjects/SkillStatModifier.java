package com.dawgandpony.pd2skills.BuildObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jamie on 28/11/2015.
 */
public class SkillStatModifier {

    private float damage = 0;
    private float damageMult = 1;
    private float stabilityMult = 1;


    private ArrayList<Integer> weaponTypes = new ArrayList<>();

    public ArrayList<Integer> getWeaponTypes() {
        return weaponTypes;
    }

    public float getDamage() {
        return damage;
    }

    public float getDamageMult() {
        return damageMult;
    }

    public static SkillStatModifier getGunsligerAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damage = 15;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getShotgunImpact() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stabilityMult = 1.25f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
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
