package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amstr.runscore.HelperClass.getValueUserClass;
import com.amstr.runscore.Utils.DeleteUIDClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Identity extends AppCompatActivity {

    // variables
    TextView name,username;
    CardView nameCard,usernameCard;
    ImageView back;
    Intent intents;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        // get id
        name = findViewById(R.id.user_name);
        username = findViewById(R.id.user_username);
        nameCard = findViewById(R.id.name_card);
        usernameCard = findViewById(R.id.username_card);
        back = findViewById(R.id.back_icon_identity);
        dialog = new Dialog(this);

        // get value from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_point = intent.getStringExtra("point");
        String user_totalStep = intent.getStringExtra("totalStep");
        String user_achievements = intent.getStringExtra("achievements");
        String user_calories = intent.getStringExtra("calories");
        String user_distance = intent.getStringExtra("distance");
        String user_password = intent.getStringExtra("password");

        name.setText(user_name);
        username.setText(user_username);

        FirebaseDatabase rootNode;
        DatabaseReference reference;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        back.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),UserSettings.class);
            getValueFromDB(reference,user_username,intents);
        });

        nameCard.setOnClickListener(view -> {
            String child = "name";
            String hint = "Name";
            String title = "Name";
            changeName(reference,user_username,child,hint,title);
        });

        usernameCard.setOnClickListener(view -> {

            dialog.setContentView(R.layout.popup_change_identity);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button save = dialog.findViewById(R.id.save_name);
            TextInputEditText changeValue = dialog.findViewById(R.id.change_name_filed);
            TextView title = dialog.findViewById(R.id.title_alert);
            changeValue.setHint("Username");
            title.setText("Username");
            dialog.show();

            save.setOnClickListener(view1 -> {

                String value = changeValue.getEditableText().toString();

                getValueUserClass getValueUserClass = new getValueUserClass(user_name,value,user_password,user_point,user_achievements,user_totalStep,user_distance,user_calories);

                DeleteUIDClass.removeUserID(user_username);
                reference.child(value).setValue(getValueUserClass);

                dialog.dismiss();

                intents = new Intent(getApplicationContext(),Identity.class);
                getValueFromDB(reference,value,intents);
            });
        });
    }

    public void changeName(DatabaseReference reference,String refGrandChild,String refChild,String hint,String titleAlert){

        dialog.setContentView(R.layout.popup_change_identity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button save = dialog.findViewById(R.id.save_name);
        TextInputEditText changeName = dialog.findViewById(R.id.change_name_filed);
        TextView title = dialog.findViewById(R.id.title_alert);
        changeName.setHint(hint);
        title.setText(titleAlert);
        dialog.show();

        save.setOnClickListener(view1 -> {

            String value = changeName.getEditableText().toString();

            reference.child(refGrandChild).child(refChild).setValue(value);

            dialog.dismiss();

            intents = new Intent(getApplicationContext(),Identity.class);
            getValueFromDB(reference,refGrandChild,intents);
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
                    String pwFromDB = snapshot.child(refChild).child("password").getValue(String.class);
                    //Intent intent = new Intent(getApplicationContext(),CounterAfterStart.class);
                    intent.putExtra("name",nameFromDB);
                    intent.putExtra("username",usernameFromDB);
                    intent.putExtra("point",pointFromDB);
                    intent.putExtra("totalStep",totalStepFromDB);
                    intent.putExtra("achievements",achievementFromDB);
                    intent.putExtra("calories",caloriesFromDB);
                    intent.putExtra("distance",distanceFromDB);
                    intent.putExtra("password",pwFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}