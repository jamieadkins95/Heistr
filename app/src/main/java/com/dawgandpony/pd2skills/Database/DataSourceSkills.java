package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dawgandpony.pd2skills.BuildObjects.Skill;
import com.dawgandpony.pd2skills.BuildObjects.SkillTree;
import com.dawgandpony.pd2skills.BuildObjects.SkillTier;
import com.dawgandpony.pd2skills.Consts.SkillTaken;
import com.dawgandpony.pd2skills.Consts.Trees;
import com.dawgandpony.pd2skills.BuildObjects.SkillBuild;

/**
 * Created by Jamie on 16/07/2015.
 */
public class DataSourceSkills {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] skillBuildColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME};
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

        ContentValues skillBuildValues = new ContentValues();
        skillBuildValues.put(MySQLiteHelper.COLUMN_NAME, "none");

        long skillBuildID = database.insert(MySQLiteHelper.TABLE_SKILL_BUILDS, null, skillBuildValues);

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

                long id = database.insert(MySQLiteHelper.TABLE_SKILL_TREES, null, values);
                //Log.d("DB creation", "Tier ID = " + id + " - Added tier " + tier + " to tree " + tree);
            }
        }

        Cursor cursorSkillTreeTiers = database.query(MySQLiteHelper.TABLE_SKILL_TREES,
                skillTreeColumns, MySQLiteHelper.COLUMN_SKILL_BUILD_ID + " = " + skillBuildID, null,
                null, null, null);
        cursorSkillTreeTiers.moveToFirst();


        SkillBuild newSkillBuild = cursorToSkillBuild(cursorBuild, cursorSkillTreeTiers);
        cursorBuild.close();
        cursorSkillTreeTiers.close();

        //Log.d("SkillBuild inserted DB", newSkillBuild.toString());
        return newSkillBuild;



    }

    public SkillBuild getSkillBuild(long id){
        Cursor cursorSkillBuild = database.query(MySQLiteHelper.TABLE_SKILL_BUILDS,
                skillBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        Cursor cursorSkillTiers = database.query(MySQLiteHelper.TABLE_SKILL_TREES,
                skillTreeColumns, MySQLiteHelper.COLUMN_SKILL_BUILD_ID + " = " + id, null,
                null, null, null);
        if (cursorSkillBuild.moveToFirst()){

            SkillBuild skillBuild = cursorToSkillBuild(cursorSkillBuild, cursorSkillTiers);
            cursorSkillBuild.close();
            cursorSkillTiers.close();
            return skillBuild;
        }
        else{
            cursorSkillBuild.close();
            cursorSkillTiers.close();
            return null;
        }


    }

    private SkillBuild cursorToSkillBuild(Cursor cursorSkillBuild, Cursor cursorSkillTreeTiers){

        SkillBuild skillBuild =  new SkillBuild();

        long skillBuildID = cursorSkillBuild.getLong(0);

        skillBuild.setId(skillBuildID);

        while (!cursorSkillTreeTiers.isAfterLast()){

            long skillTierID =  cursorSkillTreeTiers.getLong(0);
            int treeNumber = cursorSkillTreeTiers.getInt(2);
            int tierNumber = cursorSkillTreeTiers.getInt(3);
            int skill1Taken = cursorSkillTreeTiers.getInt(4);
            int skill2Taken = cursorSkillTreeTiers.getInt(5);
            int skill3Taken = cursorSkillTreeTiers.getInt(6);

            //Log.d("DB Retrieval", "Skill Build ID: " + skillBuildID + " Skill Tier ID: " + skillTierID);

            //Add a new tree if we are at a new one
            if (treeNumber > (skillBuild.getSkillTrees().size() - 1)){
                skillBuild.getSkillTrees().add(new SkillTree());
                skillBuild.getSkillTrees().get(treeNumber).setSkillBuildID(skillBuildID);
            }
            //Add a new tier if we are at a new one
            if (tierNumber > (skillBuild.getSkillTrees().get(treeNumber).getTierList().size() - 1)){
                skillBuild.getSkillTrees().get(treeNumber).getTierList().add(new SkillTier());
                skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).setSkillBuildID(skillBuildID);
                skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).setId(skillTierID);
            }

            //Add the correct amount of skills to the tier
            while(skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).getSkillsInTier().size() < 3){
                skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).getSkillsInTier().add(new Skill());
            }

            //Tell the skills if they are taken or not
            skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).getSkillsInTier().get(0).setTaken(skill1Taken);
            skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).getSkillsInTier().get(1).setTaken(skill2Taken);
            skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).getSkillsInTier().get(2).setTaken(skill3Taken);

            cursorSkillTreeTiers.moveToNext();

        }


        //Log.d("SkillBuild retireved DB", skillBuild.toString());
        return skillBuild;
    }


}
