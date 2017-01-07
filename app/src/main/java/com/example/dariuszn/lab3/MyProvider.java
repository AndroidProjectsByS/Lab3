package com.example.dariuszn.lab3;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by DariuszN on 06.01.2017.
 */

public class MyProvider extends ContentProvider {
    private MyDatabaseHellper databaseHelper;

    public static final int PHONES = 10;
    public static final int PHONE_ID = 20;

    public static final String AUTHORITY = "com.exmaple.dariuszn.myprovider";

    private static final String BASE_PATH = "phone";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PHONES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PHONE_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new MyDatabaseHellper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int uriType = uriMatcher.match(uri);
        Cursor cursor = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDatabaseHellper.TABLE_NAME);

        switch (uriType) {
            case PHONES:

                break;
            case PHONE_ID:
                    queryBuilder.appendWhere("_id = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
            SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();
            long id = 0;

            switch (uriType) {
                case PHONES:
                    id = sqlDB.insert(MyDatabaseHellper.TABLE_NAME, null, contentValues);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();

        int rowsDeleted = 0;

        switch (uriType) {
            case PHONES:
                rowsDeleted = sqlDB.delete(MyDatabaseHellper.TABLE_NAME, selection, selectionArgs);
                break;
            case PHONE_ID:
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MyDatabaseHellper.TABLE_NAME, "_id = " + id, null);
                }
                else {
                    rowsDeleted = sqlDB.delete(MyDatabaseHellper.TABLE_NAME,
                            "_id = " + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PHONES:
                rowsUpdated = db.update(MyDatabaseHellper.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case PHONE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(MyDatabaseHellper.TABLE_NAME, contentValues, "_id = " + id, null);
                }
                else {
                    rowsUpdated = db.update(MyDatabaseHellper.TABLE_NAME, contentValues,
                            "_id = " + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
