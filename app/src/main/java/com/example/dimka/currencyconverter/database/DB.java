package com.example.dimka.currencyconverter.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dimka.currencyconverter.DateAndTime;

public class DB {

    private static final String DB_NAME = "DataBases";
    private static final int DB_VERSION = 1;

    private static final String TABLE_STYLE_NAME = "style_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "txt";
    private static final String DB_STYLE_CREATE =
            "CREATE TABLE " + TABLE_STYLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TEXT + " TEXT" + ")";

    private static final String TABLE_JSON_NAME = "json_table";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_JSON = "json";
    private static final String DB_JSON_CREATE =
            "CREATE TABLE " + TABLE_JSON_NAME + "(" +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_JSON + " TEXT" + ")";

    private final Context context;

    private DatabaseHandler Database;
    private SQLiteDatabase DB;

    public DB(Context context) {
        this.context = context;
    }

    public void open() {
        Database = new DatabaseHandler(context, DB_NAME, null, DB_VERSION);
        DB = Database.getWritableDatabase();
    }

    public void close() {
        if (Database != null) {
            Database.close();
        }
    }

    public Cursor getAllDate() {
        return DB.query(TABLE_STYLE_NAME, null, null, null, null, null, null);
    }

    public void setJson(String json){
        if (json != null){
            Database = new DatabaseHandler(context, DB_NAME, null, DB_VERSION);
            DB = Database.getWritableDatabase();
            DB.delete(TABLE_JSON_NAME, null, null);
            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, new DateAndTime().getDate());
            values.put(COLUMN_JSON, json);
            DB.replace(TABLE_JSON_NAME, COLUMN_DATE, values);
            DB.close();
        }
    }
    public String getJson() {
        String json = null;
        Database = new DatabaseHandler(context, DB_NAME, null, DB_VERSION);
        DB = Database.getReadableDatabase();
        Cursor cursor = DB.query(TABLE_JSON_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(COLUMN_JSON);
            json = cursor.getString(index);
        }
        return json;
    }

    private class DatabaseHandler extends SQLiteOpenHelper {

        public DatabaseHandler(
                Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_STYLE_CREATE);
            db.execSQL(DB_JSON_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
