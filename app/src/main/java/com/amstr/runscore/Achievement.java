package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Achievement extends AppCompatActivity {

    // variables
    TextView pointValue,achievementText,contentText,achievement1,challenge1,valueAchievement1,pointAchievement1,achievement2,challenge2,valueAchievement2,pointAchievement2,achievement3,challenge3,valueAchievement3,pointAchievement3,achievement4,challenge4,valueAchievement4,pointAchievement4,achievement5,challenge5,valueAchievement5,pointAchievement5,achievement6,challenge6,valueAchievement6,pointAchievement6,achievement7,challenge7,valueAchievement7,pointAchievement7,achievement8,challenge8,valueAchievement8,pointAchievement8,achievement9,challenge9,valueAchievement9,pointAchievement9;
    ImageView point,profile/*,history*/,blog,achievementIcon,homeIcon,imgAchievement1,imgAchievement2,imgAchievement3,imgAchievement4,imgAchievement5,imgAchievement6,imgAchievement7,imgAchievement8,imgAchievement9,pointIconAchievement1,pointIconAchievement2,pointIconAchievement3,pointIconAchievement4,pointIconAchievement5,pointIconAchievement6,pointIconAchievement7,pointIconAchievement8,pointIconAchievement9;
    CardView cardAchievement1,cardAchievement2,cardAchievement3,cardAchievement4,cardAchievement5,cardAchievement6,cardAchievement7,cardAchievement8,cardAchievement9;
    ProgressBar progressBarAchievement1,progressBarAchievement2,progressBarAchievement3,progressBarAchievement4,progressBarAchievement5,progressBarAchievement6,progressBarAchievement7,progressBarAchievement8,progressBarAchievement9;
    Button buttonStart;
    int progressActivityAchievement1,valueOfProgressAchievement1,progressActivityAchievement3,valueOfProgressAchievement3,progressActivityAchievement4,valueOfProgressAchievement4,progressActivityAchievement6,valueOfProgressAchievement6,progressActivityAchievement7,valueOfProgressAchievement7,progressActivityAchievement9,valueOfProgressAchievement9;
    float progressActivityAchievement2,valueOfProgressAchievement2,progressActivityAchievement5,valueOfProgressAchievement5,progressActivityAchievement8,valueOfProgressAchievement8;
    int achievement_1 = 5000;
//    int achievement_2 = 50;
    int achievement_3 = 45;
    int achievement_4 = 40000;
//    int achievement_5 = 350;
    int achievement_6 = 500;
    int achievement_7 = 100000;
//    int achievement_8 = 750;
    int achievement_9 = 1200;
    float achievement_2 = 50f;
    float achievement_5 = 350f;
    float achievement_8 = 750f;
    FirebaseDatabase rootNode;
    DatabaseReference reference,blogReference;
    Intent intents;
    boolean ach1Condition,ach2Condition,ach3Condition,ach4Condition,ach5Condition,ach6Condition,ach7Condition,ach8Condition,ach9Condition;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        // get id
            // top bar
            point = findViewById(R.id.point_icon_achievement);
            pointValue = findViewById(R.id.point_achievement);
            profile = findViewById(R.id.profile_icon_achievement);
            // history = findViewById(R.id.history_icon_achievement);
            blog = findViewById(R.id.blog_icon_achievement);
            // button interaction
            achievementIcon = findViewById(R.id.achievement_interaction_achievement);
            homeIcon = findViewById(R.id.home_interaction_achievement);
            buttonStart = findViewById(R.id.button_start_achievement);
            // text heading
            achievementText = findViewById(R.id.achievement_text);
            contentText = findViewById(R.id.content_text);
            // achievement text
            achievement1 = findViewById(R.id.achievement1); challenge1 = findViewById(R.id.challenge1); valueAchievement1 = findViewById(R.id.value_getting_started); pointAchievement1 = findViewById(R.id.point_achievement_getting_started);
            achievement2 = findViewById(R.id.achievement2); challenge2 = findViewById(R.id.challenge2); valueAchievement2 = findViewById(R.id.value_heating); pointAchievement2 = findViewById(R.id.point_achievement_heating);
            achievement3 = findViewById(R.id.achievement3); challenge3 = findViewById(R.id.challenge3); valueAchievement3 = findViewById(R.id.value_harvest_season); pointAchievement3 = findViewById(R.id.point_achievement_harvest_season);
            achievement4 = findViewById(R.id.achievement4); challenge4 = findViewById(R.id.challenge4); valueAchievement4 = findViewById(R.id.value_leap_forward); pointAchievement4 = findViewById(R.id.point_achievement_leap_forward);
            achievement5 = findViewById(R.id.achievement5); challenge5 = findViewById(R.id.challenge5); valueAchievement5 = findViewById(R.id.value_medium_rare); pointAchievement5 = findViewById(R.id.point_achievement_medium_rare);
            achievement6 = findViewById(R.id.achievement6); challenge6 = findViewById(R.id.challenge6); valueAchievement6 = findViewById(R.id.value_mildly_rich); pointAchievement6 = findViewById(R.id.point_achievement_mildly_rich);
            achievement7 = findViewById(R.id.achievement7); challenge7 = findViewById(R.id.challenge7); valueAchievement7 = findViewById(R.id.value_walls); pointAchievement7 = findViewById(R.id.point_achievement_walls);
            achievement8 = findViewById(R.id.achievement8); challenge8 = findViewById(R.id.challenge8); valueAchievement8 = findViewById(R.id.value_meat); pointAchievement8 = findViewById(R.id.point_achievement_meat);
            achievement9 = findViewById(R.id.achievement9); challenge9 = findViewById(R.id.challenge9); valueAchievement9 = findViewById(R.id.value_millionaire); pointAchievement9 = findViewById(R.id.point_achievement_millionaire);
            // image achievement
            imgAchievement1 = findViewById(R.id.img_achievement1); pointIconAchievement1 = findViewById(R.id.point_icon_achievement_getting_started);
            imgAchievement2 = findViewById(R.id.img_achievement2); pointIconAchievement2 = findViewById(R.id.point_icon_achievement_heating);
            imgAchievement3 = findViewById(R.id.img_achievement3); pointIconAchievement3 = findViewById(R.id.point_icon_achievement_harvest_season);
            imgAchievement4 = findViewById(R.id.img_achievement4); pointIconAchievement4 = findViewById(R.id.point_icon_achievement_leap_forward);
            imgAchievement5 = findViewById(R.id.img_achievement5); pointIconAchievement5 = findViewById(R.id.point_icon_achievement_medium_rare);
            imgAchievement6 = findViewById(R.id.img_achievement6); pointIconAchievement6 = findViewById(R.id.point_icon_achievement_mildly_rich);
            imgAchievement7 = findViewById(R.id.img_achievement7); pointIconAchievement7 = findViewById(R.id.point_icon_achievement_walls);
            imgAchievement8 = findViewById(R.id.img_achievement8); pointIconAchievement8 = findViewById(R.id.point_icon_achievement_meat);
            imgAchievement9 = findViewById(R.id.img_achievement9); pointIconAchievement9 = findViewById(R.id.point_icon_achievement_millionaire);
            // card view n progress bar
            cardAchievement1 = findViewById(R.id.card_achievement1); progressBarAchievement1 = findViewById(R.id.progress_bar_getting_started);
            cardAchievement2 = findViewById(R.id.card_achievement2); progressBarAchievement2 = findViewById(R.id.progress_bar_heating);
            cardAchievement3 = findViewById(R.id.card_achievement3); progressBarAchievement3 = findViewById(R.id.progress_bar_Harvest_season);
            cardAchievement4 = findViewById(R.id.card_achievement4); progressBarAchievement4 = findViewById(R.id.progress_bar_leap_forward);
            cardAchievement5 = findViewById(R.id.card_achievement5); progressBarAchievement5 = findViewById(R.id.progress_bar_medium_rare);
            cardAchievement6 = findViewById(R.id.card_achievement6); progressBarAchievement6 = findViewById(R.id.progress_bar_mildly_rich);
            cardAchievement7 = findViewById(R.id.card_achievement7); progressBarAchievement7 = findViewById(R.id.progress_bar_walls);
            cardAchievement8 = findViewById(R.id.card_achievement8); progressBarAchievement8 = findViewById(R.id.progress_meat);
            cardAchievement9 = findViewById(R.id.card_achievement9); progressBarAchievement9 = findViewById(R.id.progress_millionaire);

        // get value from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_point = intent.getStringExtra("point");
        String user_calories = intent.getStringExtra("calories");
        String user_totalStep = intent.getStringExtra("totalStep");
        String user_achievement = intent.getStringExtra("achievements");

        // get database
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        blogReference = rootNode.getReference("blog");

        // set value
        pointValue.setText(user_point+" Points");

        // step --> (total step * 100%) / challenge
        // point --> (total point * 100%) / challenge
        // calories --> (total calories + 100%) / challenge

        // set achievement 1
        valueOfProgressAchievement1 = Integer.parseInt(user_totalStep);
        progressActivityAchievement1 = (valueOfProgressAchievement1*100)/achievement_1;
        progressBarAchievement1.setProgress(progressActivityAchievement1);
        valueAchievement1.setText(progressActivityAchievement1 +"%");

        if(progressActivityAchievement1 >= 100) {
            progressActivityAchievement1 = 100;
            progressBarAchievement1.setProgress(progressActivityAchievement1);
            valueAchievement1.setText("100%");
            achievement1.setText("Completed");
            achievement1.setTextColor(Color.parseColor("#3BC224"));

            // create the value variables
            int getPoint = Integer.parseInt(user_point) + 10;
            String point = String.valueOf(getPoint);
            String achievement = achievementComplete(user_achievement);

            // up to db
            reference.child(user_username).child("point").setValue(point);
            reference.child(user_username).child("achievements").setValue(achievement);
        }

        // set achievement 2
        valueOfProgressAchievement2 = Float.parseFloat(user_calories);
        progressActivityAchievement2 = (valueOfProgressAchievement2*100f)/achievement_2;
        progressBarAchievement2.setProgress((int) progressActivityAchievement2);
        valueAchievement2.setText((int) progressActivityAchievement2 +"%");
        // set achievement 3
        valueOfProgressAchievement3 = Integer.parseInt(user_point);
        progressActivityAchievement3 = (valueOfProgressAchievement3*100)/achievement_3;
        progressBarAchievement3.setProgress(progressActivityAchievement3);
        valueAchievement3.setText(progressActivityAchievement3 +"%");
        // set achievement 4
        valueOfProgressAchievement4 = Integer.parseInt(user_totalStep);
        progressActivityAchievement4 = (valueOfProgressAchievement4*100)/achievement_4;
        progressBarAchievement4.setProgress(progressActivityAchievement4);
        valueAchievement4.setText(progressActivityAchievement4 +"%");
        // set achievement 5
        valueOfProgressAchievement5 = Float.parseFloat(user_calories);
        progressActivityAchievement5 = (valueOfProgressAchievement5*100)/achievement_5;
        progressBarAchievement5.setProgress((int) progressActivityAchievement5);
        valueAchievement5.setText((int) progressActivityAchievement5 +"%");
        // set achievement 6
        valueOfProgressAchievement6 = Integer.parseInt(user_point);
        progressActivityAchievement6 = (valueOfProgressAchievement6*100)/achievement_6;
        progressBarAchievement6.setProgress(progressActivityAchievement6);
        valueAchievement6.setText(progressActivityAchievement6 +"%");
        // set achievement 7
        valueOfProgressAchievement7 = Integer.parseInt(user_totalStep);
        progressActivityAchievement7 = (valueOfProgressAchievement7*100)/achievement_7;
        progressBarAchievement7.setProgress(progressActivityAchievement7);
        valueAchievement7.setText(progressActivityAchievement7 +"%");
        // set achievement 8
        valueOfProgressAchievement8 = Float.parseFloat(user_calories);
        progressActivityAchievement8 = (valueOfProgressAchievement8*100)/achievement_8;
        progressBarAchievement8.setProgress((int) progressActivityAchievement8);
        valueAchievement8.setText((int) progressActivityAchievement8 +"%");
        // set achievement 9
        valueOfProgressAchievement9 = Integer.parseInt(user_point);
        progressActivityAchievement9 = (valueOfProgressAchievement9*100)/achievement_9;
        progressBarAchievement9.setProgress(progressActivityAchievement9);
        valueAchievement9.setText(progressActivityAchievement9 +"%");

        // animation
        achievementText.setTranslationX(800f);
        contentText.setTranslationX(800f);
        buttonStart.setTranslationY(300f);
        achievement1.setTranslationX(800f); challenge1.setTranslationX(800f); valueAchievement1.setTranslationX(800f); pointAchievement1.setTranslationX(800f); imgAchievement1.setTranslationX(800f); pointIconAchievement1.setTranslationX(800f); cardAchievement1.setTranslationX(800f); progressBarAchievement1.setTranslationX(800f);
        achievement2.setTranslationX(800f); challenge2.setTranslationX(800f); valueAchievement2.setTranslationX(800f); pointAchievement2.setTranslationX(800f); imgAchievement2.setTranslationX(800f); pointIconAchievement2.setTranslationX(800f); cardAchievement2.setTranslationX(800f); progressBarAchievement2.setTranslationX(800f);
        achievement3.setTranslationX(800f); challenge3.setTranslationX(800f); valueAchievement3.setTranslationX(800f); pointAchievement3.setTranslationX(800f); imgAchievement3.setTranslationX(800f); pointIconAchievement3.setTranslationX(800f); cardAchievement3.setTranslationX(800f); progressBarAchievement3.setTranslationX(800f);
        achievement4.setTranslationX(800f); challenge4.setTranslationX(800f); valueAchievement4.setTranslationX(800f); pointAchievement4.setTranslationX(800f); imgAchievement4.setTranslationX(800f); pointIconAchievement4.setTranslationX(800f); cardAchievement4.setTranslationX(800f); progressBarAchievement4.setTranslationX(800f);
        achievement5.setTranslationX(800f); challenge5.setTranslationX(800f); valueAchievement5.setTranslationX(800f); pointAchievement5.setTranslationX(800f); imgAchievement5.setTranslationX(800f); pointIconAchievement5.setTranslationX(800f); cardAchievement5.setTranslationX(800f); progressBarAchievement5.setTranslationX(800f);
        achievement6.setTranslationX(800f); challenge6.setTranslationX(800f); valueAchievement6.setTranslationX(800f); pointAchievement6.setTranslationX(800f); imgAchievement6.setTranslationX(800f); pointIconAchievement6.setTranslationX(800f); cardAchievement6.setTranslationX(800f); progressBarAchievement6.setTranslationX(800f);
        achievement7.setTranslationX(800f); challenge7.setTranslationX(800f); valueAchievement7.setTranslationX(800f); pointAchievement7.setTranslationX(800f); imgAchievement7.setTranslationX(800f); pointIconAchievement7.setTranslationX(800f); cardAchievement7.setTranslationX(800f); progressBarAchievement7.setTranslationX(800f);
        achievement8.setTranslationX(800f); challenge8.setTranslationX(800f); valueAchievement8.setTranslationX(800f); pointAchievement8.setTranslationX(800f); imgAchievement8.setTranslationX(800f); pointIconAchievement8.setTranslationX(800f); cardAchievement8.setTranslationX(800f); progressBarAchievement8.setTranslationX(800f);
        achievement9.setTranslationX(800f); challenge9.setTranslationX(800f); valueAchievement9.setTranslationX(800f); pointAchievement9.setTranslationX(800f); imgAchievement9.setTranslationX(800f); pointIconAchievement9.setTranslationX(800f); cardAchievement9.setTranslationX(800f); progressBarAchievement9.setTranslationX(800f);

        achievementText.setAlpha(0f);
        contentText.setAlpha(0f);
        buttonStart.setAlpha(0f);
        achievement1.setAlpha(0f); challenge1.setAlpha(0f); valueAchievement1.setAlpha(0f); pointAchievement1.setAlpha(0f); imgAchievement1.setAlpha(0f); pointIconAchievement1.setAlpha(0f); cardAchievement1.setAlpha(0f); progressBarAchievement1.setAlpha(0f);
        achievement2.setAlpha(0f); challenge2.setAlpha(0f); valueAchievement2.setAlpha(0f); pointAchievement2.setAlpha(0f); imgAchievement2.setAlpha(0f); pointIconAchievement2.setAlpha(0f); cardAchievement2.setAlpha(0f); progressBarAchievement2.setAlpha(0f);
        achievement3.setAlpha(0f); challenge3.setAlpha(0f); valueAchievement3.setAlpha(0f); pointAchievement3.setAlpha(0f); imgAchievement3.setAlpha(0f); pointIconAchievement3.setAlpha(0f); cardAchievement3.setAlpha(0f); progressBarAchievement3.setAlpha(0f);
        achievement4.setAlpha(0f); challenge4.setAlpha(0f); valueAchievement4.setAlpha(0f); pointAchievement4.setAlpha(0f); imgAchievement4.setAlpha(0f); pointIconAchievement4.setAlpha(0f); cardAchievement4.setAlpha(0f); progressBarAchievement4.setAlpha(0f);
        achievement5.setAlpha(0f); challenge5.setAlpha(0f); valueAchievement5.setAlpha(0f); pointAchievement5.setAlpha(0f); imgAchievement5.setAlpha(0f); pointIconAchievement5.setAlpha(0f); cardAchievement5.setAlpha(0f); progressBarAchievement5.setAlpha(0f);
        achievement6.setAlpha(0f); challenge6.setAlpha(0f); valueAchievement6.setAlpha(0f); pointAchievement6.setAlpha(0f); imgAchievement6.setAlpha(0f); pointIconAchievement6.setAlpha(0f); cardAchievement6.setAlpha(0f); progressBarAchievement6.setAlpha(0f);
        achievement7.setAlpha(0f); challenge7.setAlpha(0f); valueAchievement7.setAlpha(0f); pointAchievement7.setAlpha(0f); imgAchievement7.setAlpha(0f); pointIconAchievement7.setAlpha(0f); cardAchievement7.setAlpha(0f); progressBarAchievement7.setAlpha(0f);
        achievement8.setAlpha(0f); challenge8.setAlpha(0f); valueAchievement8.setAlpha(0f); pointAchievement8.setAlpha(0f); imgAchievement8.setAlpha(0f); pointIconAchievement8.setAlpha(0f); cardAchievement8.setAlpha(0f); progressBarAchievement8.setAlpha(0f);
        achievement9.setAlpha(0f); challenge9.setAlpha(0f); valueAchievement9.setAlpha(0f); pointAchievement9.setAlpha(0f); imgAchievement9.setAlpha(0f); pointIconAchievement9.setAlpha(0f); cardAchievement9.setAlpha(0f); progressBarAchievement9.setAlpha(0f);

        achievementText.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(200).start();
        contentText.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(200).start();
        buttonStart.animate().translationY(0f).alpha(1f).setDuration(800).setStartDelay(300).start();

        cardAchievement1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(250).start();
        imgAchievement1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(250).start();
        achievement1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(265).start();
        challenge1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(280).start();
        pointAchievement1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(300).start();
        pointIconAchievement1.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(300).start();
        progressBarAchievement1.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(350).start();
        valueAchievement1.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(350).start();

        cardAchievement2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(400).start();
        imgAchievement2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(400).start();
        achievement2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(415).start();
        challenge2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(430).start();
        pointAchievement2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(450).start();
        pointIconAchievement2.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(450).start();
        progressBarAchievement2.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(500).start();
        valueAchievement2.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(500).start();

        cardAchievement3.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(550).start();
        imgAchievement3.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(550).start();
        achievement3.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(565).start();
        challenge3.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(580).start();
        pointAchievement3.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(600).start();
        pointIconAchievement3.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        progressBarAchievement3.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(650).start();
        valueAchievement3.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(650).start();

        cardAchievement4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(700).start();
        imgAchievement4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(700).start();
        achievement4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(715).start();
        challenge4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(730).start();
        pointAchievement4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(750).start();
        pointIconAchievement4.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(750).start();
        progressBarAchievement4.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(800).start();
        valueAchievement4.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(800).start();

        cardAchievement5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(850).start();
        imgAchievement5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(850).start();
        achievement5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(865).start();
        challenge5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(880).start();
        pointAchievement5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(900).start();
        pointIconAchievement5.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(900).start();
        progressBarAchievement5.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(950).start();
        valueAchievement5.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(950).start();

        cardAchievement6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1000).start();
        imgAchievement6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1000).start();
        achievement6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1015).start();
        challenge6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1030).start();
        pointAchievement6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1050).start();
        pointIconAchievement6.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1050).start();
        progressBarAchievement6.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1100).start();
        valueAchievement6.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1100).start();

        cardAchievement7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1000).start();
        imgAchievement7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1000).start();
        achievement7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1015).start();
        challenge7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1030).start();
        pointAchievement7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1050).start();
        pointIconAchievement7.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1050).start();
        progressBarAchievement7.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1100).start();
        valueAchievement7.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1100).start();

        cardAchievement8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1150).start();
        imgAchievement8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1150).start();
        achievement8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1165).start();
        challenge8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1180).start();
        pointAchievement8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1200).start();
        pointIconAchievement8.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1200).start();
        progressBarAchievement8.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1250).start();
        valueAchievement8.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1250).start();

        cardAchievement9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1300).start();
        imgAchievement9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1300).start();
        achievement9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1315).start();
        challenge9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1330).start();
        pointAchievement9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1350).start();
        pointIconAchievement9.animate().translationX(0f).alpha(1f).setDuration(200).setStartDelay(1350).start();
        progressBarAchievement9.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1400).start();
        valueAchievement9.animate().translationX(0f).alpha(1f).setDuration(400).setStartDelay(1400).start();

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String technology = "technology";
                String healthy = "healthy";
                String environment = "environment";
                String energy = "energy";
                intents = new Intent(getApplicationContext(),Blog.class);
                getContentBlogDB(blogReference,"Teknologi",intents,technology);
                getContentBlogDB(blogReference,"Kesehatan",intents,healthy);
                getContentBlogDB(blogReference,"Lingkungan",intents,environment);
                getContentBlogDB(blogReference,"Energi",intents,energy);
            }
        });

        profile.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),UserSettings.class);
            getValueFromDB(reference,user_username,intents);
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intents = new Intent(getApplicationContext(),CounterBeforeStart.class);
                getValueFromDB(reference,user_username,intents);
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query checkUser = reference.orderByChild("username").equalTo(user_username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            String nameFromDB = snapshot.child(user_username).child("name").getValue(String.class);
                            String usernameFromDB = snapshot.child(user_username).child("username").getValue(String.class);
                            String pointFromDB = snapshot.child(user_username).child("point").getValue(String.class);
                            String totalStepFromDB = snapshot.child(user_username).child("totalStep").getValue(String.class);
                            String achievementFromDB = snapshot.child(user_username).child("achievements").getValue(String.class);
                            String caloriesFromDB = snapshot.child(user_username).child("calories").getValue(String.class);
                            String distanceFromDB = snapshot.child(user_username).child("distance").getValue(String.class);
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("username",usernameFromDB);
                            intent.putExtra("point",pointFromDB);
                            intent.putExtra("totalStep",totalStepFromDB);
                            intent.putExtra("achievements",achievementFromDB);
                            intent.putExtra("calories",caloriesFromDB);
                            intent.putExtra("distance",distanceFromDB);

                            Pair[] pairs = new Pair[1];
                            pairs[0] = new Pair<View,String>(achievementIcon,"onchanged");

                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Achievement.this,pairs);
                                startActivity(intent,options.toBundle());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
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

    private void getContentBlogDB(DatabaseReference reference,String refChild,Intent intent, String category){
        Query checkUser = reference.orderByChild("username");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String categoryFromDB = snapshot.child(refChild).child("category").getValue(String.class);
                    String titleFromDB = snapshot.child(refChild).child("title").getValue(String.class);
                    String dateFromDB = snapshot.child(refChild).child("date").getValue(String.class);
                    String contentFromDB = snapshot.child(refChild).child("text").getValue(String.class);

                    intent.putExtra("category_"+category,categoryFromDB);
                    intent.putExtra("title_"+category,titleFromDB);
                    intent.putExtra("date_"+category,dateFromDB);
                    intent.putExtra("text_"+category,contentFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String achievementComplete(String achievement){
        int getAchievement = Integer.parseInt(achievement)+1;
        return String.valueOf(getAchievement);
    }
}