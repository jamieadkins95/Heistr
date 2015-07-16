package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dawgandpony.pd2skills.Consts.SkillTaken;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;

/**
 * Created by Jamie on 16/07/2015.
 */
public class DataSourceSkills {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] skillBuildColumns = { MySQLiteHelper.COLUMN_ID};
    private String[] skillTreeColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SKILL_BUILD_ID,
            MySQLiteHelper.COLUMN_TREE,
            MySQLiteHelper.COLUMN_TIER,
            MySQLiteHelper.COLUMNS_SKILLS[0],
            MySQLiteHelper.COLUMNS_SKILLS[1],
            MySQLiteHelper.COLUMNS_SKILLS[2]};

    public DataSourceSkills(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();

    }

    public SkillBuild createAndInsertSkillBuild(){

        long skillBuildID = database.insert(MySQLiteHelper.TABLE_SKILL_BUILDS, null, null);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_SKILL_BUILDS,
                skillBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + skillBuildID, null,
                null, null, null);
        cursorBuild.moveToFirst();




        for (int tree = Trees.MASTERMIND; tree <= Trees.FUGITIVE; tree++){
            for (int tier = Trees.TIER0; tier <= Trees.TIER6; tier++){

                ContentValues values = new ContentValues();

                values.put(MySQLiteHelper.COLUMN_SKILL_BUILD_ID, skillBuildID);
                values.put(MySQLiteHelper.COLUMN_TREE, tree);
                values.put(MySQLiteHelper.COLUMN_TIER, tier);
                values.put(MySQLiteHelper.COLUMNS_SKILLS[0], SkillTaken.NO);
                values.put(MySQLiteHelper.COLUMNS_SKILLS[1], SkillTaken.NO);
                values.put(MySQLiteHelper.COLUMNS_SKILLS[2], SkillTaken.NO);

                database.insert(MySQLiteHelper.TABLE_SKILL_TREES, null, null);
            }
        }

        Cursor cursorSkillTreeTiers = database.query(MySQLiteHelper.TABLE_SKILL_TREES,
                skillTreeColumns, MySQLiteHelper.COLUMN_ID + " = " + skillBuildID, null,
                null, null, null);
        cursorSkillTreeTiers.moveToFirst();


        SkillBuild newSkillBuild = cursorToSkillBuild(cursorBuild, cursorSkillTreeTiers);
        cursorBuild.close();
        cursorSkillTreeTiers.close();

        return newSkillBuild;



    }

    private SkillBuild cursorToSkillBuild(Cursor cursorBuild, Cursor cursorSkillTreeTiers){

        SkillBuild skillBuild =  new SkillBuild();

        long id = cursorBuild.getLong(0);

        skillBuild.setId(id);

        while (!cursorSkillTreeTiers.isAfterLast()){

        }



        return skillBuild;
    }


}
