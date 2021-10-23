package com.amstr.runscore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1000;

    // variables
    ImageView logo, logoName;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        // get id
        logo = findViewById(R.id.logo);
        logoName = findViewById(R.id.logo_name);

        new Handler().postDelayed((Runnable) () -> {
            // check auth then to dashboard
            if(auth.getCurrentUser() != null){
                toMainApp();
            } else {
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        },SPLASH_SCREEN);
    }

    private void toMainApp() {
        startActivity(new Intent(MainActivity.this,Home.class));
        finish();
    }
}