package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amstr.runscore.HomeAdapter.FeaturedAdapter;
import com.amstr.runscore.HomeAdapter.FeaturedHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CounterBeforeStart extends AppCompatActivity {

    // variables
    TextView value,buttonText,challenge;
    ProgressBar progress;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    ImageView buttonBelow, back;
    Button buttonAbove;
    CardView dailyGoalCard;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Intent intents;
    int progressActivity;
    int dailyGoal = 8000;
    int valueOfProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_before_start);

        // get id
        value = findViewById(R.id.value);
        progress = findViewById(R.id.progress_bar);
        featuredRecycler = findViewById(R.id.featured_recycler_before_start);
        buttonAbove = findViewById(R.id.button_above);
        buttonText = findViewById(R.id.button_above_text);
        buttonBelow = findViewById(R.id.button_below);
        dailyGoalCard = findViewById(R.id.daily_goal_card);
        back = findViewById(R.id.back_icon);
        challenge = findViewById(R.id.challenge_in_before);

//        progress.setProgress(50);
//        value.setText("50%");

        // get value from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        //String user_name = intent.getStringExtra("name");
        String user_point = intent.getStringExtra("point");
        String user_totalStep = intent.getStringExtra("totalStep");
        String user_achievements = intent.getStringExtra("achievements");
        String user_calories = intent.getStringExtra("calories");
        String user_distance = intent.getStringExtra("distance");

        featuredRecycler(user_point,user_totalStep,user_calories,user_distance, user_achievements);

        // set progress
        valueOfProgress = Integer.parseInt(user_totalStep);
        progressActivity = (valueOfProgress*100)/dailyGoal;
        progress.setProgress(progressActivity);
        value.setText(progressActivity +"%");

        if(progressActivity >= 100){
            progress.setProgress(100);
            value.setText("100%");
            challenge.setText("Walk with minimum 8,000 steps Completed");
        }

        // animations
        dailyGoalCard.setTranslationX(800f);
        buttonBelow.setTranslationX(800f);
        buttonAbove.setTranslationX(800f);
        buttonText.setTranslationX(800f);
        featuredRecycler.setTranslationY(300f);

        dailyGoalCard.setAlpha(0f);
        buttonText.setAlpha(0f);
        buttonAbove.setAlpha(0f);
        buttonBelow.setAlpha(0f);
        featuredRecycler.setAlpha(0f);

        dailyGoalCard.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(200).start();
        buttonBelow.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400).start();
        buttonAbove.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        buttonText.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        featuredRecycler.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(800).start();

        // get database
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents = new Intent(getApplicationContext(),Home.class);
                getValueFromDB(reference,user_username,intents);
            }
        });

        buttonAbove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents = new Intent(getApplicationContext(),CounterAfterStart.class);
                getValueFromDB(reference,user_username,intents);
            }
        });
    }

    private void featuredRecycler(String point, String totalStep, String calories, String distance, String achievements){
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.steps,totalStep,"Your steps"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.achievements,achievements+"/50","Achievements"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.exclude,calories,"Calories"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.point,point,"Coin spent"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.jarak_icon,distance,"Distance"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }

    private void getValueFromDB(DatabaseReference reference,String refChild,Intent intent){
        Query checkUser = reference.orderByChild("username").equalTo(refChild);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String nameFromDB = snapshot.child(refChild).child("name").getValue(String.class);
                    String usernameFromDB = snapshot.child(refChild).child("username").getValue(String.class);
                    String pointFromDB = snapshot.child(refChild).child("point").getValue(String.class);
                    String totalStepFromDB = snapshot.child(refChild).child("totalStep").getValue(String.class);
                    String achievementFromDB = snapshot.child(refChild).child("achievements").getValue(String.class);
                    String caloriesFromDB = snapshot.child(refChild).child("calories").getValue(String.class);
                    String distanceFromDB = snapshot.child(refChild).child("distance").getValue(String.class);
                    //Intent intent = new Intent(getApplicationContext(),CounterAfterStart.class);
                    intent.putExtra("name",nameFromDB);
                    intent.putExtra("username",usernameFromDB);
                    intent.putExtra("point",pointFromDB);
                    intent.putExtra("totalStep",totalStepFromDB);
                    intent.putExtra("achievements",achievementFromDB);
                    intent.putExtra("calories",caloriesFromDB);
                    intent.putExtra("distance",distanceFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}