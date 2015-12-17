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
    private float accuracyMult = 1;
    private float ammoMult = 1;
    private int mag = 0;


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

    public float getStabilityMult() {
        return stabilityMult;
    }

    public float getAccuracyMult() {
        return accuracyMult;
    }

    public float getAmmoMult() {
        return ammoMult;
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
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.35f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
    }

    public static SkillStatModifier getPerkDeckBonus() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.05f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getSilentKiller() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.15f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getSilentKillerAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.30f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getLeadership() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stabilityMult = 1.25f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getLeadershipAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stabilityMult = 1.50f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getEquilibrium() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.accuracyMult = 1.10f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getEquilibriumAced() {
        return null;
    }

    public static SkillStatModifier getFullyLoaded() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.ammoMult = 1.25f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getSharpshooter() {
        return null;
    }

    public static SkillStatModifier getSharpshooterAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stabilityMult = 1.25f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ASSAULT);
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SNIPER);
        return modifier;
    }

    public static SkillStatModifier getMagPlus() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.mag = 5;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getMagPlusAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.mag = 10;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
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
