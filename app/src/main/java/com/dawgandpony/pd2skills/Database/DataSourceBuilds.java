package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.utils.URLEncoder;

import java.util.ArrayList;

/**
 * Created by Jamie on 16/07/2015.
 */
public class DataSourceBuilds {


    private Context context;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] buildColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_SKILL_BUILD_ID,
            MySQLiteHelper.COLUMN_WEAPON_BUILD_ID,
            MySQLiteHelper.COLUMN_INFAMY_ID,
            MySQLiteHelper.COLUMN_PERK_DECK,
            MySQLiteHelper.COLUMN_ARMOUR};

    private DataSourceSkills dataSourceSkills;

    public DataSourceBuilds(Context context){
        dbHelper = new MySQLiteHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();

    }

    public Build createAndInsertBuild(String name, int infamies, String url, long templateID){

        int perkDeck = 0;
        int armour = 0;

        Build template = null;
        template = URLEncoder.decodeURL(context, url);
        if (template == null){
            if (templateID > 0){
                template = getBuild(templateID);
            }
        }

        dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();
        long skillBuildID;
        if (template != null){
            long templateSkillBuildID = template.getSkillBuildID();
            skillBuildID = dataSourceSkills.createAndInsertSkillBuild(templateSkillBuildID).getId();
            if (infamies < template.getInfamyID()){
                infamies = (int) template.getInfamyID();
            }
            perkDeck = template.getPerkDeck();
            armour = template.getArmour();
        }
        else {
            skillBuildID = dataSourceSkills.createAndInsertSkillBuild().getId();
        }
        dataSourceSkills.close();

        ContentValues buildValues = new ContentValues();
        buildValues.put(MySQLiteHelper.COLUMN_NAME, name);
        buildValues.put(MySQLiteHelper.COLUMN_SKILL_BUILD_ID, skillBuildID);
        buildValues.put(MySQLiteHelper.COLUMN_WEAPON_BUILD_ID, -1);
        buildValues.put(MySQLiteHelper.COLUMN_INFAMY_ID, infamies);
        buildValues.put(MySQLiteHelper.COLUMN_PERK_DECK, perkDeck);
        buildValues.put(MySQLiteHelper.COLUMN_ARMOUR, armour);


        long buildID = database.insert(MySQLiteHelper.TABLE_BUILDS, null, buildValues);



        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_BUILDS,
                buildColumns, MySQLiteHelper.COLUMN_ID + " = " + buildID, null,
                null, null, null);
        cursorBuild.moveToFirst();

        Build newBuild = cursorToBuild(cursorBuild);
        cursorBuild.close();





        Log.d("Build inserted DB", newBuild.toString());
        return newBuild;



    }



    public Build getBuild(long id){
        Build build;

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_BUILDS,
                buildColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorBuild.moveToFirst()) {

            build = cursorToBuild(cursorBuild);
            cursorBuild.close();


        } else {
            cursorBuild.close();
            build =  null;
        }



        return build;
    }

    public ArrayList<Build> getAllBuilds(){
        ArrayList<Build> builds = new ArrayList<>();

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_BUILDS,
                buildColumns, null, null,
                null, null, null);



        if (cursorBuild.moveToFirst()){

            while (!cursorBuild.isAfterLast()){
                Build build = cursorToBuild(cursorBuild);
                builds.add(build);
                cursorBuild.moveToNext();
            }



        }

        cursorBuild.close();

        Log.d("DB", "Got all builds for db, there were " + builds.size());
        return builds;


    }

    private void copyBuild(Build template, Build newBuild) {
    }

    public void DeleteBuild(long id){

        database.delete(MySQLiteHelper.TABLE_BUILDS, MySQLiteHelper.COLUMN_ID + " = " + id, null);


    }

    public void updateInfamy(long buildID, long infamyID){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_INFAMY_ID, infamyID);

        database.update(MySQLiteHelper.TABLE_BUILDS, values, MySQLiteHelper.COLUMN_ID + " = " + buildID, null);
        Log.d("DB", "Infamy updated for build " + buildID + " to " + infamyID);
    }

    public void updatePerkDeck(long buildID, long selected){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PERK_DECK, selected);

        database.update(MySQLiteHelper.TABLE_BUILDS, values, MySQLiteHelper.COLUMN_ID + " = " + buildID, null);
        Log.d("DB", "Perk Deck updated for build " + buildID + " to " + selected);
    }

    public void updateArmour(long buildID, long selected){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ARMOUR, selected);

        database.update(MySQLiteHelper.TABLE_BUILDS, values, MySQLiteHelper.COLUMN_ID + " = " + buildID, null);
        Log.d("DB", "Armour updated for build " + buildID + " to " + selected);
    }

    private Build cursorToBuild(Cursor cursorBuild){

        Build build = new Build();
        long buildID = cursorBuild.getLong(0);
        String name = cursorBuild.getString(1);
        long skillBuildID = cursorBuild.getLong(2);
        long weaponBuildID = cursorBuild.getLong(3);
        long infamyID = cursorBuild.getLong(4);
        int perkDeck = cursorBuild.getInt(5);
        int armour = cursorBuild.getInt(6);




        build.setId(buildID);
        build.setName(name);
        build.setSkillBuildID(skillBuildID);
        build.setInfamies(DataSourceInfamies.idToInfamy(infamyID));
        // TODO: Weapon builds
        build.setPerkDeck(perkDeck);
        build.setArmour(armour);


        SkillBuild skillBuildFromXML = SkillBuild.getSkillBuildFromXML(context.getResources());
        SkillBuild skillBuildFromDB = SkillBuild.getSkillBuildFromDB(skillBuildID, context);
        SkillBuild mergedSkillBuild = SkillBuild.mergeBuilds(skillBuildFromXML, skillBuildFromDB);


        build.setSkillBuild(mergedSkillBuild);


        return build;
    }


}
