package com.example.assignmen4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper {

    private final String DATABASE_NAME = "MyDB";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME_USER = "User_table";
    private final String TABLE_NAME_PASSWORDS = "Password_table";
    private final String KEY_ID = "_id";
    private final String KEY_USERNAME = "_username";
    private final String KEY_PASSWORD = "_password";
    private final String KEY_WEBSITE = "_website";


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

        long records = database.insert(TABLE_NAME_USER, null, cv);
        if (records != -1) {
            Toast.makeText(context, "User signed up successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to sign up user", Toast.LENGTH_SHORT).show();
        }
    }



    public boolean userExists(String username) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean loginUser(String username, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?", new String[]{username, password});
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
    // Add methods for managing password entries here

    private class CreateDataBase extends SQLiteOpenHelper {
        public CreateDataBase(@Nullable Context context, String DATABASE_NAME, Object o, int DATABASE_VERSION) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String queryUser = "CREATE TABLE " + TABLE_NAME_USER + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT NOT NULL UNIQUE," +
                    KEY_PASSWORD + " TEXT NOT NULL" +
                    ");";
            db.execSQL(queryUser);

            String queryPassword = "CREATE TABLE " + TABLE_NAME_PASSWORDS + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT NOT NULL," +
                    KEY_PASSWORD + " TEXT NOT NULL," +
                    KEY_WEBSITE + " TEXT NOT NULL," +
                    "FOREIGN KEY(" + KEY_USERNAME + ") REFERENCES " + TABLE_NAME_USER + "(" + KEY_USERNAME + ")" +
                    ");";
            db.execSQL(queryPassword);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PASSWORDS);
            onCreate(db);
        }
    }




    // new day
    public void insertPassword(String username, String password, String website) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);
        cv.put(KEY_WEBSITE, website);

        long records = database.insert(TABLE_NAME_PASSWORDS, null, cv);
        if (records != -1) {
            Toast.makeText(context, "Password added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("DB_ERROR", "Failed to add password: " + database.getVersion()); // Log the error message
            Toast.makeText(context, "Failed to add password", Toast.LENGTH_SHORT).show();
        }
    }




}

