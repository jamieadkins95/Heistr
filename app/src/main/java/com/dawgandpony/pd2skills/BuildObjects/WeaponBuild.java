package com.dawgandpony.pd2skills.BuildObjects;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.Database.DataSourceSkills;
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

    public static WeaponBuild getWeaponBuildFromDB(Context context, long weaponBuildID) {
        WeaponBuild weaponBuildFromDB = null;
        DataSourceWeapons dataSourceWeapons = new DataSourceWeapons(context);
        dataSourceWeapons.open();

        try {

            weaponBuildFromDB = dataSourceWeapons.getWeaponBuild(weaponBuildID);

        }
        catch(Exception e){
            Log.e("Error", e.toString());
        }
        finally {
            dataSourceWeapons.close();
            return weaponBuildFromDB;
        }
    }

    public static ArrayList<Weapon> getWeaponsFromXML(Resources res, int weaponType, ArrayList<Long> pd2SkillsIDs) {
        XmlResourceParser xmlParser = null;
        ArrayList<Weapon> weapons = new ArrayList<>();

        String pd2SkillsXML = "";

        //Go get the xml for all the trees
        for (long pd2SkillsID : pd2SkillsIDs) {

            //Get the xml for the correct tree
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
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    weapons.add(currentWeapon);
                                }

                            break;
                        }


                    } else if (eventType == XmlPullParser.TEXT) {
                        String text = xmlParser.getText();
                        //Log.d("XML", "Text " + text);
                        //Log.d("Current Tag", currentTag + "");

                        switch (currentTag) {
                            case "pd2skills":
                                pd2SkillsXML = text;
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setPd2skillsID(Long.parseLong(text));
                                }
                                break;
                            case "name":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setName(text);
                                }
                                break;
                            case "rof":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setROF(Integer.parseInt(text));
                                }
                                break;
                            case "total":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setTotalAmmo(Integer.parseInt(text));
                                }
                                break;
                            case "mag":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setMagSize(Integer.parseInt(text));
                                }
                                break;
                            case "damage":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setDamage(Float.parseFloat(text));
                                }
                                break;
                            case "accuracy":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setAccuracy(Float.parseFloat(text));
                                }
                                break;
                            case "stability":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setStability(Float.parseFloat(text));
                                }
                                break;
                            case "concealment":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setConcealment(Integer.parseInt(text));
                                }
                                break;
                            case "threat":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setThreat(Integer.parseInt(text));
                                }
                                break;
                            case "attachments":
                                if (pd2SkillsXML.equals(pd2SkillsID + "")) {
                                    currentWeapon.setPossibleAttachments(attachmentsFromXML(text));
                                }
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

        }
        xmlParser.close();
        //Log.d("Result from XML", skillBuildFromXML.toString());
        return weapons;
    }

    public static WeaponBuild getWeaponBuildFromXML(Resources res, WeaponBuild weaponBuildFromDB) {
        WeaponBuild weaponBuildFromXML = new WeaponBuild();
        ArrayList<Long> primary = new ArrayList<>();
        primary.add(weaponBuildFromDB.getPrimaryWeapon().getPd2skillsID());
        weaponBuildFromXML.setPrimaryWeapon(getWeaponsFromXML(res, PRIMARY, primary).get(0));

        ArrayList<Long> secondary = new ArrayList<>();
        secondary.add(weaponBuildFromDB.getSecondaryWeapon().getPd2skillsID());
        weaponBuildFromXML.setSecondaryWeapon(getWeaponsFromXML(res, SECONDARY, secondary).get(0));

        ArrayList<Long> melee = new ArrayList<>();
        melee.add(weaponBuildFromDB.getMeleeWeapon().getPd2skillsID());
        weaponBuildFromXML.setMeleeWeapon(getWeaponsFromXML(res, MELEE, melee).get(0));

        return weaponBuildFromXML;
    }

    public static WeaponBuild mergeBuilds(WeaponBuild weaponBuildFromDB, WeaponBuild weaponBuildFromXML) {

        WeaponBuild mergedBuild = new WeaponBuild();
        mergedBuild.setId(weaponBuildFromDB.getId());

        Weapon[] weapons = mergeWeapons(weaponBuildFromDB, weaponBuildFromXML);
        mergedBuild.setPrimaryWeapon(weapons[PRIMARY]);
        mergedBuild.setSecondaryWeapon(weapons[SECONDARY]);
        mergedBuild.setMeleeWeapon(weapons[MELEE]);


        return mergedBuild;
    }

    public static Weapon[] mergeWeapons(WeaponBuild weaponBuildFromDB, WeaponBuild weaponBuildFromXML) {
        Weapon[] weapons = new Weapon[3];
        for (int weapon = PRIMARY; weapon <= MELEE; weapon ++){
            Weapon merged = mergeWeapon(weaponBuildFromDB.getWeapons()[weapon], weaponBuildFromXML.getWeapons()[weapon]);
            weapons[weapon] = merged;
        }
        return weapons;
    }

    public static Weapon mergeWeapon(Weapon weaponFromDB, Weapon weaponFromXML){
        Weapon merged = new Weapon();
        merged.setId(weaponFromDB.getId());
        merged.setName(weaponFromXML.getName());
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
