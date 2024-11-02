package com.example.lab2redo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;
import java.util.concurrent.Callable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NOTEME";
    private static final String TABLE_NAME = "NOTES";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "SUBTITLE";
    private static final String COL_4 = "CONTENT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, SUBTITLE TEXT, CONTENT TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String noteTitle, String noteSubtitle, String noteBody) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, noteTitle);
        values.put(COL_3, noteSubtitle);
        values.put(COL_4, noteBody);

        long var = db.insert(TABLE_NAME, null, values);
        if (var == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return cursor;
    }

    public Cursor getOneNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }


    public Cursor searchNotes(String filter) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the query with placeholders for parameters
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE TITLE LIKE ? " +
                " OR SUBTITLE LIKE ? " +
                " OR CONTENT LIKE ?";

        // Prepare the filter with wildcards (%)
        String wildcardFilter = "%" + filter + "%";

        // Use selectionArgs to prevent SQL injection and bind the wildcard filter to the placeholders
        Cursor cursor = db.rawQuery(query, new String[] {wildcardFilter, wildcardFilter, wildcardFilter});

        return cursor;
    }


    }
