package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.ref.Reference;

public class Login extends AppCompatActivity {

    // variables
    ImageView imageView;
    TextInputEditText usernames,passwords;
    Button loginButton,registerButton;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    // Animation rightAnim;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // animation
        // rightAnim = AnimationUtils.loadAnimation(this,R.anim.right_anim);

        // get id
        imageView = findViewById(R.id.login_logo);
        usernames = findViewById(R.id.login_username_field);
        passwords = findViewById(R.id.login_password_field);
        loginButton = findViewById(R.id.login_button_login);
        registerButton = findViewById(R.id.login_button_register);

        usernames.setTranslationX(800f);
        passwords.setTranslationX(800f);
        loginButton.setTranslationX(800f);
        registerButton.setTranslationX(800f);


        usernames.setAlpha(v);
        passwords.setAlpha(v);
        loginButton.setAlpha(v);
        registerButton.setAlpha(v);

        usernames.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(300).start();
        passwords.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500).start();
        loginButton.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(700).start();
        registerButton.animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(900).start();

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this,Register.class);
            Pair[] pairs = new Pair[4];
            pairs[0] = new Pair<View,String>(imageView,"logo_transit");
            pairs[1] = new Pair<View,String>(usernames,"username_transit");
            pairs[2] = new Pair<View,String>(passwords,"password_transit");
            pairs[3] = new Pair<View,String>(loginButton,"login_transit");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        loginButton.setOnClickListener(view -> {

            // get database
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            // get all value
            String username = usernames.getEditableText().toString();
            String password = passwords.getEditableText().toString();

            if(username.length() > 0 && password.length() > 0){
                // do login
                Query checkUser = reference.orderByChild("username").equalTo(username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            usernames.setError(null);
                            String pwFromDB = snapshot.child(username).child("password").getValue(String.class);

                            if(pwFromDB.equals(password)){
                                usernames.setError(null);
                                String nameFromDB = snapshot.child(username).child("name").getValue(String.class);
                                String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                                String pointFromDB = snapshot.child(username).child("point").getValue(String.class);
                                String totalStepFromDB = snapshot.child(username).child("totalStep").getValue(String.class);
                                String achievementFromDB = snapshot.child(username).child("achievements").getValue(String.class);
                                String caloriesFromDB = snapshot.child(username).child("calories").getValue(String.class);
                                String distanceFromDB = snapshot.child(username).child("distance").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(),Home.class);
                                intent.putExtra("name",nameFromDB);
                                intent.putExtra("username",usernameFromDB);
                                intent.putExtra("point",pointFromDB);
                                intent.putExtra("totalStep",totalStepFromDB);
                                intent.putExtra("achievements",achievementFromDB);
                                intent.putExtra("calories",caloriesFromDB);
                                intent.putExtra("distance",distanceFromDB);
                                //intent.putExtra("password",pwFromDB);

                                startActivity(intent);
                            } else {
                                passwords.setError("Wrong Password");
                                passwords.requestFocus();
                            }
                        } else {
                            usernames.setError("No Such User Exist");
                            usernames.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                if(username.length() == 0){
                    usernames.setError("Username is still empty");
                } else if(password.length() == 0){
                    passwords.setError("Password is still empty");
                }
            }
        });
    }
}