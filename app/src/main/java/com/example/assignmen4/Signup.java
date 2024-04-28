package com.example.assignmen4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText etUsername, btnsignupAddUser;
    Button btnAddUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etusername);
        btnsignupAddUser = findViewById(R.id.etpassword);
        btnAddUser = findViewById(R.id.btnsignupAddUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();

                startActivity(new Intent(Signup.this, LoginActivity.class));
                finish();
            }
        });


    }

    private void addUser() {
        String username = etUsername.getText().toString().trim();
        String password = btnsignupAddUser.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        myDatabaseHelper.insertUser(username, password);

        myDatabaseHelper.close();

        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();

    }
}
