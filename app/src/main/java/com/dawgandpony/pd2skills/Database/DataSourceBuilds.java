package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.Consts.SkillTaken;
import com.dawgandpony.pd2skills.Consts.Trees;

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

    public Build createAndInsertBuild(String name){

        dataSourceSkills = new DataSourceSkills(context);
        dataSourceSkills.open();
        long skillBuildID = dataSourceSkills.createAndInsertSkillBuild().getId();
        dataSourceSkills.close();

        ContentValues buildValues = new ContentValues();
        buildValues.put(MySQLiteHelper.COLUMN_NAME, name);
        buildValues.put(MySQLiteHelper.COLUMN_SKILL_BUILD_ID, skillBuildID);
        buildValues.put(MySQLiteHelper.COLUMN_WEAPON_BUILD_ID, -1);
        buildValues.put(MySQLiteHelper.COLUMN_INFAMY_ID, -1);
        buildValues.put(MySQLiteHelper.COLUMN_PERK_DECK, -1);
        buildValues.put(MySQLiteHelper.COLUMN_ARMOUR, -1);


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

    public void DeleteBuild(long id){

        database.delete(MySQLiteHelper.TABLE_BUILDS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        Toast.makeText(context, "Build deleted", Toast.LENGTH_SHORT).show();

    }

    public void updateInfamy(int infamy, boolean enabled){
        switch(infamy){
            case Trees.MASTERMIND:

                break;
            case Trees.ENFORCER:
                break;
            case Trees.TECHNICIAN:
                break;
            case Trees.GHOST:
                break;

        }
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
        // TODO: Weapon builds and infamy
        build.setPerkDeck(perkDeck);
        build.setArmour(armour);

        return build;
    }


}
