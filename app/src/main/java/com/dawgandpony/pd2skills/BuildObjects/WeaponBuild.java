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

    public static WeaponBuild getWeaponBuildFromXML(Resources res, WeaponBuild weaponBuildFromDB) {
        WeaponBuild weaponBuildFromXML = new WeaponBuild();
        XmlResourceParser xmlParser = null;

        long pd2skillsID;
        String pd2SkillsXML = "";

        //Go get the xml for all the trees
        for (int i = PRIMARY; i <= MELEE; i++) {

            //Get the xml for the correct tree
            switch (i) {
                case PRIMARY:
                    pd2skillsID = weaponBuildFromDB.getPrimaryWeapon().getPd2skillsID();
                    xmlParser = res.getXml(R.xml.primary_weapons);
                    break;
                case SECONDARY:
                    pd2skillsID = weaponBuildFromDB.getSecondaryWeapon().getPd2skillsID();
                    xmlParser = res.getXml(R.xml.secondary_weapons);
                    break;
                case MELEE:
                    pd2skillsID = weaponBuildFromDB.getMeleeWeapon().getPd2skillsID();
                    xmlParser = res.getXml(R.xml.melee_weapons);
                    break;
                default:
                    pd2skillsID = weaponBuildFromDB.getPrimaryWeapon().getPd2skillsID();
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

                        switch (currentTag){
                            case "weapon":
                                currentWeapon = new Weapon();
                                break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
                        //Log.d("XML", "End tag " + xmlParser.getName());
                        currentTag = xmlParser.getName();
                        switch (currentTag){
                            case "weapon":
                                switch (i) {
                                    case PRIMARY:
                                        if (pd2SkillsXML.equals(pd2skillsID + "")) {
                                            weaponBuildFromXML.setPrimaryWeapon(currentWeapon);
                                        }
                                        break;
                                    case SECONDARY:
                                        if (pd2SkillsXML.equals(pd2skillsID + "")) {
                                            weaponBuildFromXML.setSecondaryWeapon(currentWeapon);
                                        }
                                        break;
                                    case MELEE:
                                        if (pd2SkillsXML.equals(pd2skillsID + "")) {
                                            weaponBuildFromXML.setMeleeWeapon(currentWeapon);
                                        }
                                        break;

                                }
                                break;
                        }


                    } else if (eventType == XmlPullParser.TEXT) {
                        String text = xmlParser.getText();
                        //Log.d("XML", "Text " + text);
                        //Log.d("Current Tag", currentTag + "");

                        switch (currentTag){
                            case "pd2skills":
                                pd2SkillsXML = text;
                                break;
                            case "name":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setName(text);
                                }
                                break;
                            case "rof":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setROF(Integer.parseInt(text));
                                }
                                break;
                            case "total":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setTotalAmmo(Integer.parseInt(text));
                                }
                                break;
                            case "mag":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setMagSize(Integer.parseInt(text));
                                }
                                break;
                            case "damage":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setDamage(Float.parseFloat(text));
                                }
                                break;
                            case "accuracy":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setAccuracy(Float.parseFloat(text));
                                }
                                break;
                            case "stability":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setStability(Float.parseFloat(text));
                                }
                                break;
                            case "concealment":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setConcealment(Integer.parseInt(text));
                                }
                                break;
                            case "threat":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
                                    currentWeapon.setThreat(Integer.parseInt(text));
                                }
                                break;
                            case "attachments":
                                if (pd2SkillsXML.equals(pd2skillsID + "")){
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
        return weaponBuildFromXML;
    }

    public static WeaponBuild mergeBuilds(WeaponBuild weaponBuildFromDB, WeaponBuild weaponBuildFromXML) {

        WeaponBuild mergedBuild = new WeaponBuild();
        mergedBuild.setId(weaponBuildFromDB.getId());

        for (int weapon = PRIMARY; weapon <= MELEE; weapon ++){
            Weapon merged = new Weapon();
            merged.setId(weaponBuildFromDB.getWeapons()[weapon].getId());
            merged.setName(weaponBuildFromXML.getWeapons()[weapon].getName());
            merged.setPd2skillsID(weaponBuildFromDB.getWeapons()[weapon].getPd2skillsID());
            merged.setROF(weaponBuildFromXML.getWeapons()[weapon].getROF());
            merged.setTotalAmmo(weaponBuildFromXML.getWeapons()[weapon].getTotalAmmo());
            merged.setMagSize(weaponBuildFromXML.getWeapons()[weapon].getMagSize());
            merged.setDamage(weaponBuildFromXML.getWeapons()[weapon].getDamage());
            merged.setAccuracy(weaponBuildFromXML.getWeapons()[weapon].getAccuracy());
            merged.setStability(weaponBuildFromXML.getWeapons()[weapon].getStability());
            merged.setConcealment(weaponBuildFromXML.getWeapons()[weapon].getConcealment());
            merged.setThreat(weaponBuildFromXML.getWeapons()[weapon].getThreat());
            merged.setPossibleAttachments(weaponBuildFromXML.getWeapons()[weapon].getPossibleAttachments());
            merged.setAttachments(weaponBuildFromDB.getWeapons()[weapon].getAttachments());
            mergedBuild.getWeapons()[weapon] = merged;
        }


        return mergedBuild;
    }

    private static ArrayList<Long> attachmentsFromXML(String xml){
        ArrayList<Long> possibleAttachments = new ArrayList<>();
        return possibleAttachments;
    }
}
