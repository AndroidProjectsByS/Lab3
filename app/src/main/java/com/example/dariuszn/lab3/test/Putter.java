package com.example.dariuszn.lab3.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dariuszn.lab3.MyDatabaseHellper;

/**
 * Created by DariuszN on 12.01.2017.
 */

public class Putter {
    public static void putToDabatabse(Context con) {

        MyDatabaseHellper dbhelper = new MyDatabaseHellper(con);
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();

        String sqlQuery = "INSERT INTO phones ('model', 'android_version', 'www', 'producent'" +
                ") VALUES ('Galaxy s2', '2.3', 'http://www.mgsm.pl/pl/katalog/samsung/galaxysii/', 'Samsung')," +
                "('Xperia e5', '2.3', 'http://www.mgsm.pl/pl/katalog/sony/xperiae5/Sony-Xperia-E5.html', 'Sony')";

        sqLiteDatabase.execSQL(sqlQuery);
    }
}
