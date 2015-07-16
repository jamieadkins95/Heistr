package com.dawgandpony.pd2skills.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jamie on 15/07/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pd2skills.db";
    private static final int DATABASE_VERSION = 3;


    //region Skills
    public static final String TABLE_SKILL_BUILDS = "tbSkillBuilds";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private static final String CREATE_SKILL_BUILD_TABLE = "create table if not exists "
            + TABLE_SKILL_BUILDS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text);";

    public static final String TABLE_SKILL_TREES = "tbSkillTrees";
    public static final String COLUMN_SKILL_BUILD_ID = "skillBuildID";
    public static final String COLUMN_TREE = "tree";
    public static final String COLUMN_TIER = "tier";

    public static final String[] COLUMNS_SKILLS = new String[]{"skill1",
            "skill2",
            "skill3"};

    private static final String CREATE_SKILL_TREE_TABLE = "create table if not exists "
            + TABLE_SKILL_TREES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SKILL_BUILD_ID
            + " integer," + COLUMN_TREE
            + " integer," + COLUMN_TIER
            + " integer," + COLUMNS_SKILLS[0]
            + " integer," + COLUMNS_SKILLS[1]
            + " integer," + COLUMNS_SKILLS[2]
            + " integer);";
    //endregion


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SKILL_BUILD_TABLE);
        db.execSQL(CREATE_SKILL_TREE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy some old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILL_BUILDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILL_TREES);
        onCreate(db);
    }

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
