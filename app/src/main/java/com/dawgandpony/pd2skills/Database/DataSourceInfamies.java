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
 * This class is really used bevasue infamy ids can be calculated. There are only 16 possibilities.
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
