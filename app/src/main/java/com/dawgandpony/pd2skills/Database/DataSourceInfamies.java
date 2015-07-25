package com.dawgandpony.pd2skills.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.dawgandpony.pd2skills.BuildObjects.Build;
import com.dawgandpony.pd2skills.Consts.Trees;

import java.util.ArrayList;

/**
 * Created by Jamie on 25/07/2015.
 */
public class DataSourceInfamies {
    private Context context;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,

            MySQLiteHelper.COLUMN_INFAMY_MASTERMIND,
            MySQLiteHelper.COLUMN_INFAMY_ENFORCER,
            MySQLiteHelper.COLUMN_INFAMY_TECHNICIAN,
            MySQLiteHelper.COLUMN_INFAMY_GHOST
            };



    public DataSourceInfamies(Context context){
        dbHelper = new MySQLiteHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();

    }

    public ArrayList<Boolean> createAndInsertInfamy(){
        ContentValues values = new ContentValues();



        long id = database.insert(MySQLiteHelper.TABLE_INFAMY, null, values);

        Cursor cursorBuild = database.query(MySQLiteHelper.TABLE_INFAMY,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        cursorBuild.moveToFirst();





        ArrayList<Boolean> newInfamy = cursorToInfamy(cursorBuild);
        cursorBuild.close();

        return newInfamy;



    }

    public ArrayList<Boolean> getInfamy(long id){
        ArrayList<Boolean> newInfamy;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INFAMY,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);

        if (cursor.moveToFirst()) {

            newInfamy = cursorToInfamy(cursor);
            cursor.close();


        } else {
            cursor.close();
            newInfamy =  null;
        }



        return newInfamy;
    }



    private ArrayList<Boolean> cursorToInfamy(Cursor cursor){
        ArrayList<Boolean> infamy = new ArrayList<>();

        int mastermind = cursor.getInt(1);
        int enforcer = cursor.getInt(2);
        int technician = cursor.getInt(3);
        int ghost = cursor.getInt(4);

        infamy.add(mastermind != 0);
        infamy.add(enforcer != 0);
        infamy.add(technician != 0);
        infamy.add(ghost != 0);



        return infamy;
    }

    public static ArrayList<Boolean> idToInfamy(long id){
        ArrayList<Boolean> infamy = new ArrayList<>();

        for (int i = Trees.MASTERMIND; i <= Trees.GHOST;i++){
            infamy.add(false);
        }

        if ((id - 8) > 0){
            id -= 8;
            infamy.set(Trees.MASTERMIND, true);
        }
        if ((id - 4) > 0){
            id -= 4;
            infamy.set(Trees.ENFORCER, true);
        }
        if ((id - 2) > 0){
            id -= 2;
            infamy.set(Trees.TECHNICIAN, true);
        }
        if ((id - 1) > 0){
            id -= 1;
            infamy.set(Trees.GHOST, true);
        }




        return infamy;
    }
}
