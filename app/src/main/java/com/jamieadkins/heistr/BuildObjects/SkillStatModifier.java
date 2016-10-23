package com.jamieadkins.heistr.BuildObjects;

import java.util.ArrayList;

/**
 * Created by Jamie on 28/11/2015.
 */
public class SkillStatModifier {

    private float damage = 0;
    private float damageMult = 1;
    private int stability = 0;
    private float stabilityMult = 1;
    private int accuracy = 0;
    private float accuracyMult = 1;
    private float ammoMult = 1;
    private float rofMult = 1;
    private int mag = 0;
    private int concealment = 0;


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

    public int getStability() {
        return stability;
    }

    public int getAccuracy() {
        return accuracy;
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

    public float getRofMult() {
        return rofMult;
    }

    public int getMag() {
        return mag;
    }

    public int getConcealment() {
        return concealment;
    }

    public static SkillStatModifier getShotgunImpactStability() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stability = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
    }

    public static SkillStatModifier getShotgunImpact5() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.05f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
    }

    public static SkillStatModifier getShotgunImpact15() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.15f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
    }

    public static SkillStatModifier getClosebyAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.mag = 15;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SHOTGUN);
        return modifier;
    }

    public static SkillStatModifier getPerkDeckBonus() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.05f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getEquilibriumAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.accuracy = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getGunNut() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.mag = 5;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getFullyLoaded() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.ammoMult = 1.25f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getSteadyGrip() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.accuracy = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getSteadyGripAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stability = 16;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }

    public static SkillStatModifier getSureFire() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.mag = 15;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SMG);
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_LMG);
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ASSAULT);
        return modifier;
    }

    public static SkillStatModifier getInnerPockets() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.concealment = 2;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_MELEE);
        return modifier;
    }

    public static SkillStatModifier getInnerPocketsAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.concealment = 4;
        modifier.weaponTypes.add(SkillStatChangeManager.BALLISTIC_VESTS);
        return modifier;
    }

    public static SkillStatModifier getOpticIllusions() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.concealment = 1;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getOpticIllusionsAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.concealment = 2;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getSpecialisedKilling() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.15f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getSpecialisedKillingAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damageMult = 1.15f;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getProfessional() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stability = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getProfessionalAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.accuracy = 12;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SILENCED);
        return modifier;
    }

    public static SkillStatModifier getAkimbo() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stability = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_AKIMBO);
        return modifier;
    }

    public static SkillStatModifier getAkimboAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.ammoMult = 1.5f;
        modifier.stability = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_AKIMBO);
        return modifier;
    }

    public static SkillStatModifier getCustomAmmo() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damage = 5;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }
    public static SkillStatModifier getCustomAmmoAced() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.damage = 10;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_PISTOL);
        return modifier;
    }

    public static SkillStatModifier getMarksman() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.accuracy = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_SINGLE_SHOT);
        return modifier;
    }

    public static SkillStatModifier getStableShot() {
        SkillStatModifier modifier = new SkillStatModifier();
        modifier.stability = 8;
        modifier.weaponTypes.add(SkillStatChangeManager.WEAPON_TYPE_ALL);
        return modifier;
    }
}
