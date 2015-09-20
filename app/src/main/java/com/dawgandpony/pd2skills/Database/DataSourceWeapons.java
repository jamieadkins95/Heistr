package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.BuildObjects.Weapon;
import com.dawgandpony.pd2skills.BuildObjects.WeaponBuild;
import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 16/07/2015.
 */
public class DataSourceWeapons {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] weaponBuildColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_PRIMARY_WEAPON, MySQLiteHelper.COLUMN_SECONDARY_WEAPON, MySQLiteHelper.COLUMN_MELEE_WEAPON};
    private String[] weaponColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PD2SKILLS_ID,
            MySQLiteHelper.COLUMN_MOD_BARREL,
            MySQLiteHelper.COLUMN_MOD_BARREL_EXTENSION,
            MySQLiteHelper.COLUMN_MOD_BAYONET,
            MySQLiteHelper.COLUMN_MOD_CUSTOM,
            MySQLiteHelper.COLUMN_MOD_EXTRA,
            MySQLiteHelper.COLUMN_MOD_FOREGRIP,
            MySQLiteHelper.COLUMN_MOD_GADGET,
            MySQLiteHelper.COLUMN_MOD_GRIP,
            MySQLiteHelper.COLUMN_MOD_LOWER_RECEIVER,
            MySQLiteHelper.COLUMN_MOD_MAGAZINE,
            MySQLiteHelper.COLUMN_MOD_SIGHT,
            MySQLiteHelper.COLUMN_MOD_SLIDE,
            MySQLiteHelper.COLUMN_MOD_STOCK,
            MySQLiteHelper.COLUMN_MOD_SUPPRESSOR,
            MySQLiteHelper.COLUMN_MOD_UPPER_RECEIVER};

    public DataSourceWeapons(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();

    }

    public WeaponBuild createAndInsertWeaponBuild(){
        long[] weaponIDs = new long[3];

        for (int weapon = 0; weapon < 3; weapon++){

            ContentValues values = new ContentValues();

            for (String weaponColumn : weaponColumns) {
                values.put(weaponColumn, -1);
            }

            weaponIDs[weapon] = database.insert(MySQLiteHelper.TABLE_WEAPONS, null, values);
            Log.d("Weapon inserted DB", weaponIDs[weapon] + "");
        }

        ContentValues weaponBuildValues = new ContentValues();
        weaponBuildValues.put(MySQLiteHelper.COLUMN_PRIMARY_WEAPON, weaponIDs[0]);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_SECONDARY_WEAPON, weaponIDs[1]);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_MELEE_WEAPON, weaponIDs[2]);

        long weaponBuildID = database.insert(MySQLiteHelper.TABLE_WEAPON_BUILDS, null, weaponBuildValues);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + weaponBuildID, null,
                null, null, null);
        cursorBuild.moveToFirst();


        WeaponBuild newWeaponBuild = cursorToWeaponBuild(cursorBuild);
        cursorBuild.close();

        Log.d("WeaponBuild inserted DB", newWeaponBuild.toString());
        return newWeaponBuild;



    }



    public WeaponBuild getWeaponBuild(long id){
        WeaponBuild weaponBuild = null;
        Cursor cursorWeaponBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorWeaponBuild.moveToFirst()){
            weaponBuild = cursorToWeaponBuild(cursorWeaponBuild);
        }


        cursorWeaponBuild.close();

        return weaponBuild;



    }

    public Weapon getWeapon(long id){
        Weapon weapon = null;
        Cursor cursorWeapon = database.query(MySQLiteHelper.TABLE_WEAPONS,
                weaponColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorWeapon.moveToFirst()){
            weapon = cursorToWeapon(cursorWeapon);
        }


        cursorWeapon.close();

        return weapon;

    }

    public ArrayList<WeaponBuild> getAllWeaponBuilds(){
        ArrayList<WeaponBuild> weaponBuilds = new ArrayList<>();

        Cursor cursorWeaponBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, null, null,
                null, null, null);



        if (cursorWeaponBuild.moveToFirst()){

            while (!cursorWeaponBuild.isAfterLast()){
                WeaponBuild weaponBuild = cursorToWeaponBuild(cursorWeaponBuild);
                weaponBuilds.add(weaponBuild);
                cursorWeaponBuild.moveToNext();
            }



        }

        cursorWeaponBuild.close();
        return weaponBuilds;


    }



    private WeaponBuild cursorToWeaponBuild(Cursor cursorWeaponBuild){

        WeaponBuild weaponBuild =  new WeaponBuild();
        long id = cursorWeaponBuild.getLong(0);
        long primary = cursorWeaponBuild.getLong(1);
        long secondary = cursorWeaponBuild.getLong(2);
        long melee = cursorWeaponBuild.getLong(3);

        Weapon primaryWeapon = getWeapon(primary);
        Weapon secondaryWeapon = getWeapon(secondary);
        Weapon meleeWeapon = getWeapon(melee);

        weaponBuild.setId(id);
        weaponBuild.setPrimaryWeapon(primaryWeapon);
        weaponBuild.setSecondaryWeapon(secondaryWeapon);
        weaponBuild.setMeleeWeapon(meleeWeapon);


        return weaponBuild;
    }

    private Weapon cursorToWeapon(Cursor cursorWeapon){

        Weapon weapon =  new Weapon();
        weapon.setId(cursorWeapon.getLong(0));
        weapon.setPd2skillsID(cursorWeapon.getLong(1));
        weapon.setName("Retr");

        return weapon;
    }


}
