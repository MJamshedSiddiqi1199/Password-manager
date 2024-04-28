package com.example.assignmen4;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowPasswordsActivity extends AppCompatActivity {

    private ListView passwordList;
    private MyDatabaseHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_in_list_website_password); // Replace with your layout resource ID

        passwordList = findViewById(R.id.password_list); // Replace with your ListView ID
        dbHelper = new MyDatabaseHelper(this);

        // Retrieve current username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentUsername = sharedPreferences.getString("username", "");

        // Fetch password entries and display them in the list
        fetchPasswordEntries(currentUsername);
    }

    private void fetchPasswordEntries(String username) {
        dbHelper.open(); // Ensure the database is open

        Cursor cursor = dbHelper.getAllPasswordEntries(username);

        if (cursor.getCount() > 0) {
            ArrayList<String> passwordData = new ArrayList<>();
            while (cursor.moveToNext()) {
                String website = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_WEBSITE));
                String password = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_PASSWORD));
                String userName = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_USERNAME));

                // Format the list entry to display username, website, and password (optional)
                String listEntry = String.format("%s (%s) - %s", userName, website, password); // Consider masking password for security

                passwordData.add(listEntry);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, passwordData);
            passwordList.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No passwords found for this user", Toast.LENGTH_SHORT).show();
        }

        cursor.close(); // Close the cursor
        dbHelper.close(); // Close the database connection
    }
}
