package com.dawgandpony.pd2skills.BuildObjects;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.dawgandpony.pd2skills.Database.DataSourceWeapons;
import com.dawgandpony.pd2skills.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Jamie on 20/09/2015.
 */
public class WeaponBuild {

    public final static int PRIMARY = 0;
    public final static int SECONDARY = 1;
    public final static int MELEE = 2;

    private long id = -1;
    private Weapon[] weapons = new Weapon[3];





    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Weapon getPrimaryWeapon() {
        return weapons[PRIMARY];
    }

    public void setPrimaryWeapon(Weapon primaryWeapon) {
        this.weapons[PRIMARY] = primaryWeapon;
    }

    public Weapon getSecondaryWeapon() {
        return weapons[SECONDARY];
    }

    public void setSecondaryWeapon(Weapon secondaryWeapon) {
        this.weapons[SECONDARY] = secondaryWeapon;
    }

    public Weapon getMeleeWeapon() {
        return weapons[MELEE];
    }

    public void setMeleeWeapon(Weapon meleeWeapon) {
        this.weapons[MELEE] = meleeWeapon;
    }

    public Weapon[] getWeapons(){
        return weapons;
    }


    public static ArrayList<Weapon> getWeaponsFromXML(Resources res, int weaponType) {
        XmlResourceParser xmlParser = null;
        ArrayList<Weapon> weapons = new ArrayList<>();

        //Get the xml for the correct weapon type
        switch (weaponType) {
            case PRIMARY:
                xmlParser = res.getXml(R.xml.primary_weapons);
                break;
            case SECONDARY:
                xmlParser = res.getXml(R.xml.secondary_weapons);
                break;
            case MELEE:
                xmlParser = res.getXml(R.xml.melee_weapons);
                break;
            default:

                Log.e("Error", "Something went wrong while we were retrieving the weapons");
                break;
        }


        try {
            int eventType = xmlParser.getEventType();

            Weapon currentWeapon = null;
            String currentTag = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_DOCUMENT) {
                    //Log.d("XML", "Start Document");
                } else if (eventType == XmlPullParser.START_TAG) {

                    //Log.d("XML", "Start tag " + xmlParser.getName());
                    currentTag = xmlParser.getName();

                    switch (currentTag) {
                        case "weapon":
                            currentWeapon = new Weapon();
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    //Log.d("XML", "End tag " + xmlParser.getName());
                    currentTag = xmlParser.getName();
                    switch (currentTag) {
                        case "weapon":
                            currentWeapon.setWeaponType(weaponType);
                            weapons.add(currentWeapon);
                            break;
                    }


                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xmlParser.getText();
                    //Log.d("XML", "Text " + text);
                    //Log.d("Current Tag", currentTag + "");

                    switch (currentTag) {
                        case "pd2skills":
                            currentWeapon.setPd2skillsID(Long.parseLong(text));
                            break;
                        case "name":
                            currentWeapon.setWeaponName(text);
                            break;
                        case "rof":
                            currentWeapon.setROF(Integer.parseInt(text));
                            break;
                        case "total":
                            currentWeapon.setTotalAmmo(Integer.parseInt(text));
                            break;
                        case "mag":
                            currentWeapon.setMagSize(Integer.parseInt(text));
                            break;
                        case "damage":
                            currentWeapon.setDamage(Float.parseFloat(text));
                            break;
                        case "accuracy":
                            currentWeapon.setAccuracy(Float.parseFloat(text));
                            break;
                        case "stability":
                            currentWeapon.setStability(Float.parseFloat(text));
                            break;
                        case "concealment":
                            currentWeapon.setConcealment(Integer.parseInt(text));
                            break;
                        case "threat":
                            currentWeapon.setThreat(Integer.parseInt(text));
                            break;
                        case "attachments":
                            currentWeapon.setPossibleAttachments(attachmentsFromXML(text));
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

    public static ArrayList<Weapon> getWeaponsFromXML(Resources res) {

        ArrayList<Weapon> weapons = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            weapons.addAll(getWeaponsFromXML(res, i));
        }

        return weapons;
    }

    public static Weapon mergeWeapon(Weapon weaponFromDB, Weapon weaponFromXML){
        Weapon merged = new Weapon();
        merged.setId(weaponFromDB.getId());
        merged.setName(weaponFromDB.getName());
        merged.setWeaponName(weaponFromXML.getWeaponName());
        merged.setWeaponType(weaponFromDB.getWeaponType());
        merged.setPd2skillsID(weaponFromDB.getPd2skillsID());
        merged.setROF(weaponFromXML.getROF());
        merged.setTotalAmmo(weaponFromXML.getTotalAmmo());
        merged.setMagSize(weaponFromXML.getMagSize());
        merged.setDamage(weaponFromXML.getDamage());
        merged.setAccuracy(weaponFromXML.getAccuracy());
        merged.setStability(weaponFromXML.getStability());
        merged.setConcealment(weaponFromXML.getConcealment());
        merged.setThreat(weaponFromXML.getThreat());
        merged.setPossibleAttachments(weaponFromXML.getPossibleAttachments());
        merged.setAttachments(weaponFromDB.getAttachments());

        return merged;
    }

    private static ArrayList<Long> attachmentsFromXML(String xml){
        ArrayList<Long> possibleAttachments = new ArrayList<>();
        return possibleAttachments;
    }
}
