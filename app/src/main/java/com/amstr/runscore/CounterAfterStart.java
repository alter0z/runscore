package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class CounterAfterStart extends AppCompatActivity implements SensorEventListener {

    // variables
    TextView value,stepValue,steps,command,challenge;
    ProgressBar progress,progressHorizontal;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    ImageView back;
    CardView dailyGoalCard;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private SensorManager sensor_manager;
    private Sensor stepc, stepd;
    private boolean isCounterSensorActive, isDetectorSensorActive;
    int stepCount = 0;
    int stepDetect = 0;
//    int scores = 0;
    int valueOfCounter;
    int points = 0;
    float distance = 0f;
    int progressActivity = 0;
    int dailyGoal = 8000;
    float calories = 0f;
    String getCalories = "0",getStepCount = "0",getPoint = "0",getDistance = "0 M", getAchievements = "0";
    Intent intents;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_after_start);

        // to get screen always on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // get id
        value = findViewById(R.id.after_start_value);
        stepValue = findViewById(R.id.detector);
        steps = findViewById(R.id.steps);
        progress = findViewById(R.id.progress_bar_circle);
        back = findViewById(R.id.after_start_back_icon);
        dailyGoalCard = findViewById(R.id.after_start_daily_goal_card);
        featuredRecycler = findViewById(R.id.featured_recycler_after_start);
        progressHorizontal = findViewById(R.id.after_start_progress_bar);
        command = findViewById(R.id.command);
        challenge = findViewById(R.id.challenge_in_after);
        sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // setting start
        stepValue.setText("detecting...");
//        progress.setProgress(0);
//        progressHorizontal.setProgress(0);

        // get value from dab
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        //String user_name = intent.getStringExtra("name");
//        String user_point = intent.getStringExtra("point");
        String user_totalStep = intent.getStringExtra("totalStep");
//        String user_calories = intent.getStringExtra("calories");
//        String user_distance = intent.getStringExtra("distance");
//
//        featuredRecycler(user_point,user_totalStep,user_calories,user_distance);
        //featuredRecycler();

        // animations
        dailyGoalCard.setTranslationX(800f);
        progress.setTranslationX(800f);
        steps.setTranslationX(800f);
        stepValue.setTranslationX(800f);
        featuredRecycler.setTranslationY(300f);

        dailyGoalCard.setAlpha(0f);
        progress.setAlpha(0f);
        stepValue.setAlpha(0f);
        steps.setAlpha(0f);
        featuredRecycler.setAlpha(0f);

        dailyGoalCard.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(200).start();
        progress.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400).start();
        stepValue.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        steps.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();
        featuredRecycler.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(800).start();

//        // get reference DB
//        rootNode = FirebaseDatabase.getInstance();
//        reference = rootNode.getReference("user");

        // for sensor is present
        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepc = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            //step_counter.setText("Sensor active");
            isCounterSensorActive = true;
        } else {
            Toast.makeText(CounterAfterStart.this,"No Sensor are present",Toast.LENGTH_LONG).show();
            isCounterSensorActive = false;
        }

        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            stepd = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            //step_detector.setText("Sensor active");
            isDetectorSensorActive = true;
        } else {
            Toast.makeText(CounterAfterStart.this,"No Sensor are present",Toast.LENGTH_LONG).show();
            isDetectorSensorActive = false;
        }

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                //getValueFromDB(reference,user_username,intents);
                command.setText("Tap this to resume");
                Toast.makeText(CounterAfterStart.this,"Sensor paused",Toast.LENGTH_SHORT);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intents = new Intent(getApplicationContext(),CounterBeforeStart.class);
                        getValueFromDB(reference,user_username,intents);
                    }
                });

                command.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPostResume();
                        command.setText("Tap the counter to stop");
                        Toast.makeText(CounterAfterStart.this,"Sensor resumed",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void featuredRecycler(String point, String totalStep, String calories, String distance, String achievements){
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.steps,totalStep, "Your steps"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.achievements,achievements+"/50","Achievements"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.exclude,calories,"Calories"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.point,point,"Coin spent"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.jarak_icon,distance,"Distance"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        featuredRecycler(getPoint,getStepCount,getCalories,getDistance,getAchievements);

        // get value from dab
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_totalStep = intent.getStringExtra("totalStep");

        valueOfCounter = Integer.parseInt(user_totalStep);

        if(sensorEvent.sensor == stepc){
            stepCount = (int) sensorEvent.values[valueOfCounter];
            getStepCount = String.valueOf(stepCount);
//            getStepCount = stepCount;
            //step_counter.setText(String.valueOf(stepCount));
            //score.setText(String.valueOf(scores));
            progressActivity = (stepCount*100)/dailyGoal;
            progress.setProgress(progressActivity);
            value.setText(String.valueOf(progressActivity)+"%");
            progressHorizontal.setProgress(progressActivity);

            if(progressActivity >= 100){
                progress.setProgress(100);
                value.setText("100%");
                progressHorizontal.setProgress(100);
                challenge.setText("Walk with minimum 8,000 steps Completed");
            }

            if(stepCount >= 25){
                calories = stepCount/25f;
                getCalories = String.valueOf(calories);
            } else if(stepCount >= 25000){
                calories = stepCount/25f;
                getCalories = calories + "K";
            }

            if(stepCount >= 100){
                points = stepCount/100;
                getPoint = String.valueOf(points);
//                if(scores >= 100){
//                    points = scores/100;
////                    getPoint = points;
//                }
                //score.setText(String.valueOf(scores));
            }
            if(stepCount >= 2000){
                distance = (stepCount/2f)/1000f;
                getDistance = distance + " KM";
                //distance_view.setText(distance +" KM");
            } else {
                distance = stepCount/2f;
                getDistance = distance + " M";
                //distance_view.setText(distance+" M");
            }

        } else if(sensorEvent.sensor == stepd){
            stepDetect = (int) (stepDetect+sensorEvent.values[0]);
            stepValue.setText(String.valueOf(stepDetect));
//            progressActivity = (stepDetect*100)/dailyGoal;
//            progress.setProgress(progressActivity);
//            value.setText(String.valueOf(progressActivity)+"%");
//            progressHorizontal.setProgress(progressActivity);
        }

        // get reference DB
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

//        // get value from dab
//        Intent intent = getIntent();
//        String user_username = intent.getStringExtra("username");

//        getValueClass getValueClass = new getValueClass(String.valueOf(points),String.valueOf(stepCount),getCalories);
        updateValue(reference,user_username,getStepCount,getAchievements,getCalories,getPoint,getDistance);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensor_manager.registerListener(this,stepc,SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            sensor_manager.registerListener(this,stepd,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensor_manager.unregisterListener(this, stepc);
        }

        if(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            sensor_manager.unregisterListener(this,stepd);
        }
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

    private void updateValue(DatabaseReference reference, String child, String totalStep, String achievements, String calories, String points, String distance){
        reference.child(child).child("totalStep").setValue(totalStep);
        reference.child(child).child("achievements").setValue(achievements);
        reference.child(child).child("calories").setValue(calories);
        reference.child(child).child("point").setValue(points);
        reference.child(child).child("distance").setValue(distance);
    }
}