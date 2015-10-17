package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.Attachment;
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

    private Context mContext;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] weaponBuildColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_PRIMARY_WEAPON, MySQLiteHelper.COLUMN_SECONDARY_WEAPON, MySQLiteHelper.COLUMN_MELEE_WEAPON};
    private static String[] weaponColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PD2SKILLS_ID,
            MySQLiteHelper.COLUMN_WEAPON_TYPE,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_BARREL],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_BARREL_EXTENSION],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_BAYONET],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_CUSTOM],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_EXTRA],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_FOREGRIP],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_GADGET],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_GRIP],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_LOWER_RECEIVER],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_MAGAZINE],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_SIGHT],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_SLIDE],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_STOCK],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_SUPPRESSOR],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_UPPER_RECEIVER]};

    private ArrayList<Weapon> baseWeaponInfo;

    public DataSourceWeapons(Context context, ArrayList<Weapon> baseWeaponInfo){
        dbHelper = new MySQLiteHelper(context);
        mContext = context;
        this.baseWeaponInfo = baseWeaponInfo;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public WeaponBuild createAndInsertWeaponBuild(){

        ContentValues weaponBuildValues = new ContentValues();
        weaponBuildValues.put(MySQLiteHelper.COLUMN_PRIMARY_WEAPON, 1);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_SECONDARY_WEAPON, 2);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_MELEE_WEAPON, 3);

        long weaponBuildID = database.insert(MySQLiteHelper.TABLE_WEAPON_BUILDS, null, weaponBuildValues);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + weaponBuildID, null,
                null, null, null);
        cursorBuild.moveToFirst();


        WeaponBuild newWeaponBuild = cursorToWeaponBuild(cursorBuild);
        cursorBuild.close();

        Log.d("WeaponBuild inserted DB", newWeaponBuild.getId() + "");
        return newWeaponBuild;



    }

    public Weapon createAndInsertWeapon(String name, long pd2skillsID, int weaponType){
        ContentValues weaponValues = new ContentValues();
        weaponValues.put(MySQLiteHelper.COLUMN_NAME, name);
        weaponValues.put(MySQLiteHelper.COLUMN_PD2SKILLS_ID, pd2skillsID);
        weaponValues.put(MySQLiteHelper.COLUMN_WEAPON_TYPE, weaponType);

        long weaponID = database.insert(MySQLiteHelper.TABLE_WEAPONS, null, weaponValues);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_WEAPONS,
                weaponColumns, MySQLiteHelper.COLUMN_ID + " = " + weaponID, null,
                null, null, null);
        cursorBuild.moveToFirst();


        Weapon newWeapon = cursorToWeapon(cursorBuild);
        cursorBuild.close();

        Log.d("WeaponBuild inserted DB", newWeapon.getId() + "");
        return newWeapon;
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

    public ArrayList<Weapon> getAllWeapons(int weaponType){
        ArrayList<Weapon> weapons = new ArrayList<>();

        Cursor cursorWeapons = database.query(MySQLiteHelper.TABLE_WEAPONS,
                weaponColumns, null, null,
                null, null, null);



        if (cursorWeapons.moveToFirst()){

            while (!cursorWeapons.isAfterLast()){
                Weapon weapon = cursorToWeapon(cursorWeapons);
                if (weapon.getWeaponType() == weaponType){
                    weapons.add(weapon);
                }
                cursorWeapons.moveToNext();
            }



        }

        cursorWeapons.close();
        return weapons;


    }

    public void deleteWeapon(long id){
        if (id > 3){
            database.delete(MySQLiteHelper.TABLE_WEAPONS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        } else {
            Toast.makeText(mContext, "Can't delete example weapons", Toast.LENGTH_SHORT).show();
        }
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
        weapon.setWeaponType(cursorWeapon.getInt(2));
        weapon.setName(cursorWeapon.getString(3));
        weapon.setAttachments(attachmentsFromDB(cursorWeapon));

        Weapon merged = null;
        for (Weapon w : baseWeaponInfo){
            if (w.getPd2skillsID() == weapon.getPd2skillsID()){
                if (w.getWeaponType() == weapon.getWeaponType()){
                    merged = WeaponBuild.mergeWeapon(weapon, w);
                }

            }
        }

        return merged;
    }



    private ArrayList<Attachment> attachmentsFromDB(Cursor dbAttachments){
        ArrayList<Attachment> attachments = new ArrayList<>();
        return attachments;
    }


    public static String[] getWeaponColumns() {
        return weaponColumns;
    }
}
