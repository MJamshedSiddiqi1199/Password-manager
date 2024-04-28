package com.example.assignmen4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class get_password_website extends AppCompatActivity {

    EditText etWebsite, etPassword;
    Button btnAddWebsite;
    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password_website);

        // Initialize views
        etWebsite = findViewById(R.id.etpwwebsite);
        etPassword = findViewById(R.id.etpwpassword);
        btnAddWebsite = findViewById(R.id.btnpwAddwebsite);

        // Initialize database helper
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.open();

        // Set click listener for add button
        btnAddWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get website and password from EditText fields
                String website = etWebsite.getText().toString().trim();
                String password = etPassword.getText().toString();

                // Validate if website and password are not empty
                if (website.isEmpty() || password.isEmpty()) {
                    Toast.makeText(get_password_website.this, "Please enter both website and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve username from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");

                // Insert password entry into the database
                dbHelper.insertPassword(username, password, website);

                // Clear EditText fields
                etWebsite.setText("");
                etPassword.setText("");

                Toast.makeText(get_password_website.this, "Password added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        dbHelper.close();
    }
}
