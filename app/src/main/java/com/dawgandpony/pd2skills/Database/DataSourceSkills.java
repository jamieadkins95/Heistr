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
import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;

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
                values.put(MySQLiteHelper.COLUMNS_SKILLS[0], Skill.NO);
                values.put(MySQLiteHelper.COLUMNS_SKILLS[1], Skill.NO);
                values.put(MySQLiteHelper.COLUMNS_SKILLS[2], Skill.NO);

                long id = database.insert(MySQLiteHelper.TABLE_SKILL_TIERS, null, values);
                //Log.d("DB creation", "Tier ID = " + id + " - Added tier " + tier + " to tree " + tree);
            }
        }



        SkillBuild newSkillBuild = cursorToSkillBuild(cursorBuild);
        cursorBuild.close();

        //Log.d("SkillBuild inserted DB", newSkillBuild.toString());
        return newSkillBuild;



    }

    public SkillBuild createAndInsertSkillBuild(long templateID){

        ContentValues skillBuildValues = new ContentValues();
        skillBuildValues.put(MySQLiteHelper.COLUMN_NAME, "none");

        long skillBuildID = database.insert(MySQLiteHelper.TABLE_SKILL_BUILDS, null, skillBuildValues);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_SKILL_BUILDS,
                skillBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + skillBuildID, null,
                null, null, null);
        cursorBuild.moveToFirst();

        SkillBuild template = getSkillBuild(templateID);


        for (int tree = Trees.MASTERMIND; tree <= Trees.FUGITIVE; tree++){
            for (int tier = Trees.TIER0; tier <= Trees.TIER6; tier++){
                ContentValues values = new ContentValues();

                values.put(MySQLiteHelper.COLUMN_SKILL_BUILD_ID, skillBuildID);
                values.put(MySQLiteHelper.COLUMN_TREE, tree);
                values.put(MySQLiteHelper.COLUMN_TIER, tier);

                values.put(MySQLiteHelper.COLUMNS_SKILLS[0], template.getSkillTrees().get(tree).getTierList().get(tier).getSkillsInTier().get(0).getTaken());
                if (template.getSkillTrees().get(tree).getTierList().get(tier).getSkillsInTier().size() > 1) {
                    values.put(MySQLiteHelper.COLUMNS_SKILLS[1], template.getSkillTrees().get(tree).getTierList().get(tier).getSkillsInTier().get(1).getTaken());
                    values.put(MySQLiteHelper.COLUMNS_SKILLS[2], template.getSkillTrees().get(tree).getTierList().get(tier).getSkillsInTier().get(2).getTaken());
                }
                else {
                    values.put(MySQLiteHelper.COLUMNS_SKILLS[1], Skill.NO);
                    values.put(MySQLiteHelper.COLUMNS_SKILLS[2], Skill.NO);
                }

                long id = database.insert(MySQLiteHelper.TABLE_SKILL_TIERS, null, values);
                //Log.d("DB creation", "Tier ID = " + id + " - Added tier " + tier + " to tree " + tree);
            }
        }



        SkillBuild newSkillBuild = cursorToSkillBuild(cursorBuild);
        cursorBuild.close();

        //Log.d("SkillBuild inserted DB", newSkillBuild.toString());
        return newSkillBuild;



    }

    public SkillBuild getSkillBuild(long id){
        SkillBuild skillBuild = null;
        Cursor cursorSkillBuild = database.query(MySQLiteHelper.TABLE_SKILL_BUILDS,
                skillBuildColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursorSkillBuild.moveToFirst()){
            skillBuild = cursorToSkillBuild(cursorSkillBuild);
        }


        cursorSkillBuild.close();

        return skillBuild;



    }

    public ArrayList<SkillBuild> getAllSkillBuilds(){
        ArrayList<SkillBuild> skillBuilds = new ArrayList<>();

        Cursor cursorSkillBuild = database.query(MySQLiteHelper.TABLE_SKILL_BUILDS,
                skillBuildColumns, null, null,
                null, null, null);



        if (cursorSkillBuild.moveToFirst()){

            while (!cursorSkillBuild.isAfterLast()){
                SkillBuild skillBuild = cursorToSkillBuild(cursorSkillBuild);
                skillBuilds.add(skillBuild);
                cursorSkillBuild.moveToNext();
            }



        }

        cursorSkillBuild.close();
        return skillBuilds;


    }

    public void updateSkillTier(long buildID, int tree, SkillTier tier){

        ContentValues values = new ContentValues();
        for (int skill = 0; skill < tier.getSkillsInTier().size(); skill++){
            values.put(MySQLiteHelper.COLUMNS_SKILLS[skill], tier.getSkillsInTier().get(skill).getTaken());
        }


        database.update(MySQLiteHelper.TABLE_SKILL_TIERS, values, MySQLiteHelper.COLUMN_SKILL_BUILD_ID + " = " + buildID +
                " AND " + MySQLiteHelper.COLUMN_TREE + " = " + tree +
                " AND " + MySQLiteHelper.COLUMN_TIER + " = " + tier.getNumber(), null);
        Log.d("DB", "Tier updated for build " + buildID + ", tree " + tree + ", tier " + tier.getNumber());
    }

    private SkillBuild cursorToSkillBuild(Cursor cursorSkillBuild){

        SkillBuild skillBuild =  new SkillBuild();
        long skillBuildID = cursorSkillBuild.getLong(0);
        skillBuild.setId(skillBuildID);

        Cursor cursorSkillTreeTiers = database.query(MySQLiteHelper.TABLE_SKILL_TIERS,
                skillTreeColumns, MySQLiteHelper.COLUMN_SKILL_BUILD_ID + " = " + skillBuildID, null,
                null, null, null);

        cursorSkillTreeTiers.moveToFirst();

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
                skillBuild.getSkillTrees().get(treeNumber).getTierList().get(tierNumber).setSkillTree(treeNumber);
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

        cursorSkillTreeTiers.close();

        //Log.d("SkillBuild retireved DB", skillBuild.toString());
        return skillBuild;
    }


}
