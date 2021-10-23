package com.amstr.runscore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    // variables
    ImageView imageView;
    TextInputEditText names,usernames,passwords,confirmPasswords;
    Button loginButton,registerButton;
    float v = 0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get id
        imageView = findViewById(R.id.register_logo);
        usernames = findViewById(R.id.register_username_field);
        names = findViewById(R.id.name_field);
        passwords = findViewById(R.id.register_password_field);
        confirmPasswords = findViewById(R.id.confirm_password_field);
        loginButton = findViewById(R.id.register_button_login);
        registerButton = findViewById(R.id.register_button_register);

        names.setTranslationX(800f);
        confirmPasswords.setTranslationX(800f);
        registerButton.setTranslationX(800f);

        names.setAlpha(v);
        confirmPasswords.setAlpha(v);
        registerButton.setAlpha(v);

        names.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(300).start();
        confirmPasswords.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500);
        registerButton.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(700).start();

        registerButton.setOnClickListener(view -> {

            // get all value
            String username = usernames.getEditableText().toString();
            String name = names.getEditableText().toString();
            String password = passwords.getEditableText().toString();
            String confirmPassword = confirmPasswords.getEditableText().toString();
            String point = "0";
            String totalStep = "0";
            String achievements = "0";
            String calories = "0";
            String distance = "0 M";

            UserHelperClass helperClass = new UserHelperClass(name,username,password,point,totalStep,achievements,calories,distance);

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            if(name.length() > 0 && username.length() > 0 && password.length() > 0 && confirmPassword.length() > 0){
                if(password.equals(confirmPassword)){
                    reference.child(username).setValue(helperClass);
                    Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                } else {
                    confirmPasswords.setError("Password not Match");
                }
            } else if(name.length() == 0){
                names.setError("Name cannot be empty");
            } else if(username.length() == 0){
                usernames.setError("Username cannot be empty");
            } else if(password.length() == 0){
                passwords.setError("Set your password");
            } else if(confirmPassword.length() == 0){
                confirmPasswords.setError("Confirm your password");
            }
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this,Login.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View,String>(imageView,"logo_transit");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }
}