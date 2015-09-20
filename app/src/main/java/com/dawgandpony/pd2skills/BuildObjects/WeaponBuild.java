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

/**
 * Created by Jamie on 20/09/2015.
 */
public class WeaponBuild {

    private long id = -1;
    private Weapon primaryWeapon;
    private Weapon secondaryWeapon;
    private Weapon meleeWeapon;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Weapon getPrimaryWeapon() {
        return primaryWeapon;
    }

    public void setPrimaryWeapon(Weapon primaryWeapon) {
        this.primaryWeapon = primaryWeapon;
    }

    public Weapon getSecondaryWeapon() {
        return secondaryWeapon;
    }

    public void setSecondaryWeapon(Weapon secondaryWeapon) {
        this.secondaryWeapon = secondaryWeapon;
    }

    public Weapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(Weapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
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

        //Go get the xml for all the trees
        for (int i = 0; i < 3; i++) {

            //Get the xml for the correct tree
            switch (i) {
                case 0:
                    pd2skillsID = weaponBuildFromDB.getPrimaryWeapon().getPd2skillsID();
                    xmlParser = res.getXml(R.xml.primary_weapons);
                    break;
                case 1:
                    pd2skillsID = weaponBuildFromDB.getSecondaryWeapon().getPd2skillsID();
                    xmlParser = res.getXml(R.xml.secondary_weapons);
                    break;
                case 2:
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

                        switch (xmlParser.getName()){
                            case "weapon":
                                currentWeapon = new Weapon();
                                break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
                        //Log.d("XML", "End tag " + xmlParser.getName());
                        currentTag = xmlParser.getName();
                        switch (xmlParser.getName()){
                            case "weapon":
                                switch (i) {
                                    case 0:
                                        weaponBuildFromXML.setPrimaryWeapon(currentWeapon);
                                        break;
                                    case 1:
                                        weaponBuildFromXML.setSecondaryWeapon(currentWeapon);
                                        break;
                                    case 2:
                                        weaponBuildFromXML.setMeleeWeapon(currentWeapon);
                                        break;

                                }
                                break;
                        }


                    } else if (eventType == XmlPullParser.TEXT) {
                        String text = xmlParser.getText();
                        //Log.d("XML", "Text " + text);
                        //Log.d("Current Tag", currentTag + "");
                        String pd2Skills = "";
                        switch (currentTag){
                            case "pd2skills":
                                pd2Skills = text;
                                break;
                            case "name":
                                if (pd2Skills.equals(pd2skillsID + "")){
                                    currentWeapon.setName(text);
                                }
                                break;
                            default:
                                Log.d("XML", "currentTag didnt match anything!");
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
        weaponBuildFromDB.getPrimaryWeapon().setName(weaponBuildFromXML.getPrimaryWeapon().getName());
        weaponBuildFromDB.getSecondaryWeapon().setName(weaponBuildFromXML.getSecondaryWeapon().getName());
        weaponBuildFromDB.getMeleeWeapon().setName(weaponBuildFromXML.getMeleeWeapon().getName());

        return weaponBuildFromDB;
    }
}
