package com.jamieadkins.heistr.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jamieadkins.heistr.BuildObjects.Attachment;
import com.jamieadkins.heistr.BuildObjects.MeleeWeapon;
import com.jamieadkins.heistr.BuildObjects.Weapon;
import com.jamieadkins.heistr.BuildObjects.WeaponBuild;

import java.util.ArrayList;

/**
 * Created by Jamie on 16/07/2015.
 */
public class DataSourceWeapons {

    private Context mContext;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] weaponBuildColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_PRIMARY_WEAPON, MySQLiteHelper.COLUMN_SECONDARY_WEAPON, MySQLiteHelper.COLUMN_MELEE_WEAPON};
    private static String[] weaponColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PD2_ID,
            MySQLiteHelper.COLUMN_WEAPON_TYPE,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_AMMO],
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
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_UPPER_RECEIVER],
            MySQLiteHelper.COLUMNS_ATTACHMENTS[Attachment.MOD_STAT_BOOST]};

    private ArrayList<Weapon> baseWeaponInfo;
    private ArrayList<MeleeWeapon> meleeWeaponInfo;
    private final ArrayList<Attachment> baseAttachmentInfo;
    private final ArrayList<Attachment> overrideAttachmentInfo;

    public DataSourceWeapons(Context context) {
        dbHelper = new MySQLiteHelper(context);
        mContext = context;
        this.baseWeaponInfo = WeaponBuild.getWeaponsFromXML(context.getResources());
        this.baseAttachmentInfo = Attachment.getAttachmentsFromXML(context.getResources());
        this.meleeWeaponInfo = MeleeWeapon.getMeleeWeaponsFromXML(context.getResources());
        this.overrideAttachmentInfo = Attachment.getAttachmentOverrides(context.getResources());
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public WeaponBuild createAndInsertWeaponBuild() {

        ContentValues weaponBuildValues = new ContentValues();
        weaponBuildValues.put(MySQLiteHelper.COLUMN_PRIMARY_WEAPON, -1);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_SECONDARY_WEAPON, -1);
        weaponBuildValues.put(MySQLiteHelper.COLUMN_MELEE_WEAPON, -1);

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

    public Weapon createAndInsertWeapon(String name, String pd2ID, int weaponType) {
        ContentValues weaponValues = new ContentValues();
        weaponValues.put(MySQLiteHelper.COLUMN_NAME, name);
        weaponValues.put(MySQLiteHelper.COLUMN_PD2_ID, pd2ID);
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


    public WeaponBuild getWeaponBuild(long id) {
        WeaponBuild weaponBuild = null;
        Cursor cursorWeaponBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorWeaponBuild.moveToFirst()) {
            weaponBuild = cursorToWeaponBuild(cursorWeaponBuild);
        }


        cursorWeaponBuild.close();

        return weaponBuild;


    }

    public Weapon getWeapon(long id) {
        Weapon weapon = null;
        Cursor cursorWeapon = database.query(MySQLiteHelper.TABLE_WEAPONS,
                weaponColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorWeapon.moveToFirst()) {
            weapon = cursorToWeapon(cursorWeapon);
        }


        cursorWeapon.close();

        return weapon;

    }

    public ArrayList<WeaponBuild> getAllWeaponBuilds() {
        ArrayList<WeaponBuild> weaponBuilds = new ArrayList<>();

        Cursor cursorWeaponBuild = database.query(MySQLiteHelper.TABLE_WEAPON_BUILDS,
                weaponBuildColumns, null, null,
                null, null, null);


        if (cursorWeaponBuild.moveToFirst()) {

            while (!cursorWeaponBuild.isAfterLast()) {
                WeaponBuild weaponBuild = cursorToWeaponBuild(cursorWeaponBuild);
                weaponBuilds.add(weaponBuild);
                cursorWeaponBuild.moveToNext();
            }


        }

        cursorWeaponBuild.close();
        return weaponBuilds;


    }

    public ArrayList<Weapon> getAllWeapons(int weaponType) {
        ArrayList<Weapon> weapons = new ArrayList<>();

        Cursor cursorWeapons = database.query(MySQLiteHelper.TABLE_WEAPONS,
                weaponColumns, null, null,
                null, null, null);


        if (cursorWeapons.moveToFirst()) {

            while (!cursorWeapons.isAfterLast()) {
                Weapon weapon = cursorToWeapon(cursorWeapons);
                if (weapon.getWeaponType() == weaponType) {
                    weapons.add(weapon);
                }
                cursorWeapons.moveToNext();
            }


        }

        cursorWeapons.close();
        return weapons;


    }

    public void deleteWeapon(long id) {
        database.delete(MySQLiteHelper.TABLE_WEAPONS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void updateAttachment(long id, int attachmentType, String attachmentID) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMNS_ATTACHMENTS[attachmentType], attachmentID);
        database.update(MySQLiteHelper.TABLE_WEAPONS, values, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }


    private WeaponBuild cursorToWeaponBuild(Cursor cursorWeaponBuild) {

        WeaponBuild weaponBuild = new WeaponBuild();
        long id = cursorWeaponBuild.getLong(0);
        long primary = cursorWeaponBuild.getLong(1);
        long secondary = cursorWeaponBuild.getLong(2);
        String melee = cursorWeaponBuild.getString(3);

        Weapon primaryWeapon = getWeapon(primary);
        Weapon secondaryWeapon = getWeapon(secondary);
        MeleeWeapon meleeWeapon = null;
        for (MeleeWeapon m : meleeWeaponInfo) {
            if (m.getPd2().equals(melee)) {
                meleeWeapon = m;
            }
        }

        weaponBuild.setId(id);
        weaponBuild.setPrimaryWeapon(primaryWeapon);
        weaponBuild.setSecondaryWeapon(secondaryWeapon);
        weaponBuild.setMeleeWeapon(meleeWeapon);

        return weaponBuild;
    }


    private Weapon cursorToWeapon(Cursor cursorWeapon) {

        Weapon weapon = new Weapon();
        weapon.setId(cursorWeapon.getLong(0));
        weapon.setPd2(cursorWeapon.getString(1));
        weapon.setWeaponType(cursorWeapon.getInt(2));
        weapon.setName(cursorWeapon.getString(3));
        ArrayList<String> attachments = new ArrayList<>();
        for (int i = Attachment.MOD_AMMO; i <= Attachment.MOD_STAT_BOOST; i++) {
            attachments.add(cursorWeapon.getString(4 + i));
        }

        ArrayList<Attachment> equippedAttachments = new ArrayList<>();

        ArrayList<String> attachmentBlackList = new ArrayList<>();
        for (Attachment xml : overrideAttachmentInfo) {
            for (String id : attachments) {
                if (xml.getPd2().equals(id)) {
                    if (weapon.getPd2().equals(xml.getWeaponToOverride())) {
                        equippedAttachments.add(xml);
                        attachmentBlackList.add(xml.getPd2());
                    }
                }
            }

        }

        for (Attachment xml : baseAttachmentInfo) {
            for (String id : attachments) {
                if (xml.getPd2().equals(id)) {
                    if (!attachmentBlackList.contains(xml.getPd2())) {
                        equippedAttachments.add(xml);
                    }
                }
            }

        }
        weapon.setAttachments(equippedAttachments);

        Weapon merged = null;
        for (Weapon w : baseWeaponInfo) {
            if (w.getPd2().equals(weapon.getPd2())) {
                if (w.getWeaponType() == weapon.getWeaponType()) {
                    merged = WeaponBuild.mergeWeapon(weapon, w);
                }

            }
        }

        return merged;
    }


    public static String[] getWeaponColumns() {
        return weaponColumns;
    }
}
