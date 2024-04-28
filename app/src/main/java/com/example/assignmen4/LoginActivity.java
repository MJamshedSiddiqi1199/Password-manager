package com.example.assignmen4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etloginpassword,etloginusername;
    Button btnloginAddUser;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etloginusername = findViewById(R.id.etloginusername);
        etloginpassword = findViewById(R.id.etloginpassword);
        btnloginAddUser = findViewById(R.id.btnloginAddUser);

        // Initialize database helper
        myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        // Set click listener for login button
        btnloginAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password from EditText fields
                String username = etloginusername.getText().toString().trim();
                String password = etloginpassword.getText().toString();

                // Validate if username and password are not empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user exists in the database
                if (myDatabaseHelper.loginUser(username, password)) {
                    // User exists, navigate to MainActivity

                    Toast.makeText(LoginActivity.this, "User Login successfully!!!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish(); // Finish this activity to prevent going back to it with the back button
                } else {
                    // User does not exist or incorrect credentials
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        myDatabaseHelper.close();
    }
}
