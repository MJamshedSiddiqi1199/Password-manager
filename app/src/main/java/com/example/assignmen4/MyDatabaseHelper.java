package com.example.assignmen4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper {

    private final String DATABASE_NAME = "MyDB";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME = "User_table";
    private final String KEY_USERNAME = "_username";
    private final String KEY_PASSWORD = "_password";

    private CreateDataBase helper;
    private SQLiteDatabase database;
    private Context context;

    public MyDatabaseHelper(Context context) {
        this.context = context;
    }

    public void insertUser(String username, String password) {
        // Check if the user already exists
        if (userExists(username)) {
            Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);

        long records = database.insert(TABLE_NAME, null, cv);
        if (records != -1) {
            Toast.makeText(context, "User signed up successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to sign up user", Toast.LENGTH_SHORT).show();
        }
    }



    public boolean userExists(String username) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean loginUser(String username, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?", new String[]{username, password});
        boolean success = cursor.getCount() > 0;
        cursor.close();
        return success;
    }

    public void open() {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close() {
        database.close();
        helper.close();
    }

    private class CreateDataBase extends SQLiteOpenHelper {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + "(" +
                    KEY_USERNAME + " TEXT NOT NULL PRIMARY KEY," +
                    KEY_PASSWORD + " TEXT NOT NULL" +
                    ");";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
