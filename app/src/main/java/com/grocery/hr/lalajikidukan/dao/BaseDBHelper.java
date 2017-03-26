package com.grocery.hr.lalajikidukan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vipul on 24/3/17.
 */

public class BaseDBHelper extends SQLiteOpenHelper implements DBTables {

    private static final String TAG = "BaseDbHelper";

    private static BaseDBHelper mInstance;

    private BaseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static BaseDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BaseDBHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int size = DB_SQL_CREATE_TABLE_QUERIES.length;
        for (int creationQuery = 0; creationQuery < size; creationQuery++) {
            db.execSQL(DB_SQL_CREATE_TABLE_QUERIES[creationQuery]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public synchronized boolean insert(ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, values);
        return true;
    }

    public synchronized boolean update(String tableName, ContentValues values, String whereClause, String[] whereAgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName, values, whereClause, whereAgs);
        return true;
    }

    public synchronized void delete(String tableName, String whereClause, String[] whereAgs) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tableName, whereClause, whereAgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteTableItems(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + tableName);
    }


    public synchronized Cursor executeRawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor;
    }

}
