package com.example.dimka.currencyconverter.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "style_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "style_table";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "txt";

    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TEXT + " TEXT" + ")";

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
        return DB.query(TABLE_NAME, null, null, null, null, null, null);
    }

    private class DatabaseHandler extends SQLiteOpenHelper {

        public DatabaseHandler(
                Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
