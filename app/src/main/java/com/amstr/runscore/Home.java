package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amstr.runscore.HomeAdapter.FeaturedAdapter;
import com.amstr.runscore.HomeAdapter.FeaturedHelperClass;
import com.amstr.runscore.HomeAdapter.getValueClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // variables
    ImageView pointIcon,profileIcon,historyIcon,blogIcon,homeInteraction,achievementInteraction;
    TextView points,name,contentIntro,rewards,rewardDesc,yourStats,statsDesc;
    CardView banner,foodCardView,fashionCardView,sportCardView,moreCardView,buttonCardView;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    Button buttonStart;
    FirebaseDatabase rootNode;
    DatabaseReference reference,blogReference;
    Intent intents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get id
        pointIcon = findViewById(R.id.point_icon);
        profileIcon = findViewById(R.id.profile_icon);
        historyIcon = findViewById(R.id.history_icon);
        blogIcon = findViewById(R.id.blog_icon);
        points = findViewById(R.id.point);
        name = findViewById(R.id.name);
        homeInteraction = findViewById(R.id.home_interaction);
        achievementInteraction = findViewById(R.id.achievement_interaction);
//        ellipse = findViewById(R.id.ellipse);
        contentIntro = findViewById(R.id.content_intro);
        rewards = findViewById(R.id.rewards);
        rewardDesc = findViewById(R.id.reward_desc);
        yourStats = findViewById(R.id.your_stats);
        statsDesc = findViewById(R.id.stats_desc);
        banner = findViewById(R.id.banner);
        foodCardView = findViewById(R.id.food_card_view);
        fashionCardView = findViewById(R.id.fashion_card_view);
        sportCardView = findViewById(R.id.sport_card_view);
        moreCardView = findViewById(R.id.more_card_view);
        buttonCardView = findViewById(R.id.button_card_view);
        buttonStart = findViewById(R.id.button_start);
        featuredRecycler = findViewById(R.id.featured_recycler);

        // get value from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_point = intent.getStringExtra("point");
        String user_totalStep = intent.getStringExtra("totalStep");
        String user_achievements = intent.getStringExtra("achievements");
        String user_calories = intent.getStringExtra("calories");
        String user_distance = intent.getStringExtra("distance");
        //String user_password = intent.getStringExtra("password");

        // set the value
        name.setText("Welcome back, "+user_name);
        points.setText(user_point+" Points");

        featuredRecycler(user_point,user_totalStep,user_calories,user_distance,user_achievements);

        // animations
        name.setTranslationX(800f);
//        homeInteraction.setTranslationY(300f);
//        achievementInteraction.setTranslationY(300f);
//        ellipse.setTranslationY(300f);
        contentIntro.setTranslationX(800f);
        rewards.setTranslationX(800f);
        rewardDesc.setTranslationX(800f);
        yourStats.setTranslationY(300f);
        statsDesc.setTranslationY(300f);
        banner.setTranslationX(800f);
        foodCardView.setTranslationX(800f);
        fashionCardView.setTranslationX(800f);
        sportCardView.setTranslationX(800f);
        moreCardView.setTranslationX(800f);
//        buttonCardView.setTranslationY(300f);
        buttonStart.setTranslationY(300f);
        featuredRecycler.setTranslationY(300f);

        name.setAlpha(0f);
//        homeInteraction.setAlpha(0f);
//        achievementInteraction.setAlpha(0f);
//        ellipse.setAlpha(0f);
        contentIntro.setAlpha(0f);
        rewardDesc.setAlpha(0f);
        rewards.setAlpha(0f);
        yourStats.setAlpha(0f);
        statsDesc.setAlpha(0f);
        banner.setAlpha(0f);
        foodCardView.setAlpha(0f);
        fashionCardView.setAlpha(0f);
        sportCardView.setAlpha(0f);
        moreCardView.setAlpha(0f);
        buttonStart.setAlpha(0f);
//        buttonCardView.setAlpha(0f);
        featuredRecycler.setAlpha(0f);

        name.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(200).start();
        contentIntro.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(300).start();
        banner.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400).start();
        rewards.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(500).start();
        rewardDesc.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        foodCardView.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(700).start();
        sportCardView.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(800).start();
        fashionCardView.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(900).start();
        moreCardView.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(1000).start();
        yourStats.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(200).start();
        statsDesc.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(300).start();
        featuredRecycler.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(400).start();
//        buttonCardView.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(500).start();
//        ellipse.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(500).start();
//        homeInteraction.animate().translationY(0f).alpha(1f).setDuration(800).setStartDelay(600).start();
//        achievementInteraction.animate().translationY(0f).alpha(1f).setDuration(800).setStartDelay(700).start();
        buttonStart.animate().translationY(0f).alpha(1f).setDuration(800).setStartDelay(800).start();

        // get database
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        blogReference = rootNode.getReference("blog");

        achievementInteraction.setOnClickListener(view -> {
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
                        Intent intent1 = new Intent(getApplicationContext(),Achievement.class);
                        intent1.putExtra("name",nameFromDB);
                        intent1.putExtra("username",usernameFromDB);
                        intent1.putExtra("point",pointFromDB);
                        intent1.putExtra("totalStep",totalStepFromDB);
                        intent1.putExtra("achievements",achievementFromDB);
                        intent1.putExtra("calories",caloriesFromDB);
                        intent1.putExtra("distance",distanceFromDB);

                        Pair[] pairs = new Pair[1];
                        pairs[0] = new Pair<View,String>(homeInteraction,"onchanged");

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this,pairs);
                            startActivity(intent1,options.toBundle());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        buttonStart.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),CounterBeforeStart.class);
            getValueFromDB(reference,user_username,intents);
        });

        banner.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),Rewards.class);
            getValueFromDB(reference,user_username,intents);
        });

        moreCardView.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),Rewards.class);
            getValueFromDB(reference,user_username,intents);
        });

        blogIcon.setOnClickListener(view -> {
            String technology = "technology";
            String healthy = "healthy";
            String environment = "environment";
            String energy = "energy";
            intents = new Intent(getApplicationContext(),Blog.class);
            getContentBlogDB(blogReference,"Teknologi",intents,technology);
            getContentBlogDB(blogReference,"Kesehatan",intents,healthy);
            getContentBlogDB(blogReference,"Lingkungan",intents,environment);
            getContentBlogDB(blogReference,"Energi",intents,energy);
            getValueFromDB(reference,user_username,intents);
        });

        profileIcon.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),UserSettings.class);
            getValueFromDB(reference,user_username,intents);
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
                    String rewardStatuesForHyundai = snapshot.child(refChild).child("rewards").child("Hyundai Santa FE").child("status").getValue(String.class);
                    String rewardStatuesForKfc = snapshot.child(refChild).child("rewards").child("KFC 1 Snack Bucket").child("status").getValue(String.class);
                    String rewardStatuesForBukalapak = snapshot.child(refChild).child("rewards").child("Bukalapak 10% Diskon Voucher").child("status").getValue(String.class);
                    //Intent intent = new Intent(getApplicationContext(),CounterAfterStart.class);
                    intent.putExtra("name",nameFromDB);
                    intent.putExtra("username",usernameFromDB);
                    intent.putExtra("point",pointFromDB);
                    intent.putExtra("totalStep",totalStepFromDB);
                    intent.putExtra("achievements",achievementFromDB);
                    intent.putExtra("calories",caloriesFromDB);
                    intent.putExtra("distance",distanceFromDB);
                    intent.putExtra("hyundai",rewardStatuesForHyundai);
                    intent.putExtra("kfc",rewardStatuesForKfc);
                    intent.putExtra("bukalapak",rewardStatuesForBukalapak);

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
}