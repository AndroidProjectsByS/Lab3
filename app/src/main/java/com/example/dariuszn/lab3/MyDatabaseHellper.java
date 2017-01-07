package com.example.dariuszn.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dariuszn.lab3.model.Phone;

/**
 * Created by DariuszN on 28.12.2016.
 */

public class MyDatabaseHellper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "PhoneDB";
    public final static String TABLE_NAME = "phones";

    public MyDatabaseHellper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createEntriesSql = "CREATE TABLE phones ("  + Phone.ID + " INTEGER primary key autoincrement," +
                Phone.MODEL_COLUMMN + " model VARCHAR(50) NOT NULL, " +
                Phone.ANDROID_VERSION_COLUMN + " VARCHAR(5) NOT NULL, " +
                Phone.WWW_COLUMN + " VARCHAR(50) NOT NULL, " +
                Phone.PRODUCENT_COLUMN + " producent VARCHAR(50) NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(createEntriesSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
