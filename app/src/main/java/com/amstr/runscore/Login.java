package com.amstr.runscore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    // variables
    ImageView imageView;
    TextInputEditText usernames,passwords;
    Button loginButton,registerButton;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    RelativeLayout field;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        imageView = findViewById(R.id.login_logo);
        usernames = findViewById(R.id.email);
        passwords = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.to_register);
        field = findViewById(R.id.space);

        // set position for animation
        field.setTranslationY(500f);
        field.setAlpha(0f);

        // set animation
        field.animate().translationY(0f).alpha(1f).setDuration(800).setStartDelay(300).start();

        registerButton.setOnClickListener(view -> {

        });

        loginButton.setOnClickListener(view -> {

        });
    }
}