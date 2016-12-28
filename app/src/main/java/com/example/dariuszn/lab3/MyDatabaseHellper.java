package com.example.dariuszn.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DariuszN on 28.12.2016.
 */

public class MyDatabaseHellper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "PhoneDB";

    public MyDatabaseHellper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createEntriesSql = "CREATE TABLE phones (id INT primary key autoincrement," +
                    "model VARCHAR(50) NOT NULL, " +
                    "android_version VARCHAR(5) NOT NULL, " +
                    "www VARCHAR(50) NOT NULL, " +
                    "producent VARCHAR(50) NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(createEntriesSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
