package com.jamieadkins.heistr.BuildObjects;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.jamieadkins.heistr.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Jamie on 25/03/2016.
 */
public class MeleeWeapon {
    String pd2 = "wpn";
    String weaponName = "Prim2";
    String damage = "0 (0)";
    String knockdown = "0 (0)";
    String charge = "0";
    String range = "0";
    int concealment = 0;

    public String getPd2() {
        return pd2;
    }

    public void setPd2(String pd2) {
        this.pd2 = pd2;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getKnockdown() {
        return knockdown;
    }

    public void setKnockdown(String knockdown) {
        this.knockdown = knockdown;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getBaseConcealment() {
        return concealment;
    }

    public int getConcealment(SkillStatChangeManager skillStatChangeManager) {
        int adjustedConcealment = concealment;
        if (skillStatChangeManager != null) {
            for (SkillStatModifier modifier : skillStatChangeManager.getModifiers()) {
                for (int weaponType : modifier.getWeaponTypes()) {
                    if (weaponType == SkillStatChangeManager.WEAPON_TYPE_MELEE) {
                        adjustedConcealment += modifier.getConcealment();
                    }
                }
            }
        }
        return adjustedConcealment;
    }

    public void setConcealment(int concealment) {
        this.concealment = concealment;
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(SkillStatChangeManager skillStatChangeManager) {
        boolean thickSkinEquipped = false;

        if (skillStatChangeManager != null) {
            for (SkillStatModifier statModifier : skillStatChangeManager.getModifiers()) {
                for (int weaponType : statModifier.getWeaponTypes()) {
                    if (weaponType == SkillStatChangeManager.WEAPON_TYPE_MELEE) {
                        thickSkinEquipped = true;
                    }
                }
            }
        }

        String thickSkin = "";
        if (thickSkinEquipped) {
            thickSkin = "\n\nYou have the Thick Skin skill equipped, concealment for all melee " +
                    "weapons has been increased by 2.";
        }

        return "Damage: " + damage + "\n" +
                "Knockdown: " + knockdown + "\n" +
                "Charge time: " + charge + "\n" +
                "Range: " + range + "\n" +
                "Concealment: " + getConcealment(skillStatChangeManager) +
                thickSkin;
    }

    public static ArrayList<MeleeWeapon> getMeleeWeaponsFromXML(Resources res) {
        XmlResourceParser xmlParser = res.getXml(R.xml.melee_weapons);
        ArrayList<MeleeWeapon> weapons = new ArrayList<>();

        try {
            int eventType = xmlParser.getEventType();

            MeleeWeapon currentWeapon = null;
            String currentTag = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_DOCUMENT) {
                    //Log.d("XML", "Start Document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    currentTag = xmlParser.getName();

                    switch (currentTag) {
                        case "weapon":
                            currentWeapon = new MeleeWeapon();
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    //Log.d("XML", "End tag " + xmlParser.getName());
                    currentTag = xmlParser.getName();
                    switch (currentTag) {
                        case "weapon":
                            weapons.add(currentWeapon);
                            break;
                    }


                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xmlParser.getText();
                    //Log.d("XML", "Text " + text);
                    //Log.d("Current Tag", currentTag + "");

                    switch (currentTag) {
                        case "pd2":
                            currentWeapon.setPd2(text);
                            break;
                        case "name":
                            currentWeapon.setWeaponName(text);
                            break;
                        case "damage":
                            currentWeapon.setDamage(text);
                            break;
                        case "knockdown":
                            currentWeapon.setKnockdown(text);
                            break;
                        case "charge":
                            currentWeapon.setCharge(text);
                            break;
                        case "range":
                            currentWeapon.setRange(text);
                            break;
                        case "concealment":
                            currentWeapon.setConcealment(Integer.parseInt(text));
                            break;
                        default:
                            //Log.d("XML", "currentTag didnt match anything!");
                            break;
                    }
                }
                eventType = xmlParser.next();
            }

            //Log.d("XML", "End Document");
        } catch (Exception e) {
            Log.e("Error", e.toString());
            xmlParser.close();
            //Toast.makeText(getBaseContext(), "Something went wrong while we were retrieving the skills", Toast.LENGTH_SHORT).show();
        }


        xmlParser.close();
        //Log.d("Result from XML", skillBuildFromXML.toString());
        return weapons;
    }
}
