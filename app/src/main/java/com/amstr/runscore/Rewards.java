package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Rewards extends AppCompatActivity {

    // variables
    TextView pointShow,expires1,dateExpired1,expires2,dateExpired2,expires3,dateExpired3;
    CardView hyundaiCard,kfcCard,bukalapakCard;
    Button redeem1,redeem2,redeem3,readMore1,readMore2,readMore3;
    ImageView back;
    Dialog popup,dialog;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Intent intents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // get id
        pointShow = findViewById(R.id.show_point_reward);
        expires1 = findViewById(R.id.expires1);
        expires2 = findViewById(R.id.expires2);
        expires3 = findViewById(R.id.expires3);
        dateExpired1 = findViewById(R.id.date_x1);
        dateExpired2 = findViewById(R.id.date_x2);
        dateExpired3 = findViewById(R.id.date_x3);
        hyundaiCard = findViewById(R.id.hyundai_card);
        kfcCard = findViewById(R.id.kfc_card);
        bukalapakCard = findViewById(R.id.bukalapak_card);
        redeem1 = findViewById(R.id.redeem);
        redeem2 = findViewById(R.id.redeem2);
        redeem3 = findViewById(R.id.redeem3);
        readMore1 = findViewById(R.id.read_more);
        readMore2 = findViewById(R.id.read_more2);
        readMore3 = findViewById(R.id.read_more3);
        back = findViewById(R.id.back_icon_rewards);
        popup = new Dialog(this);
        dialog = new Dialog(this);

        // get value from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_point = intent.getStringExtra("point");
        String user_hyundai = intent.getStringExtra("hyundai");
        String user_kfc = intent.getStringExtra("kfc");
        String user_bukalapak = intent.getStringExtra("bukalapak");

        hyundaiCard.setTranslationX(800f);
        kfcCard.setTranslationX(800f);
        bukalapakCard.setTranslationX(800f);

        hyundaiCard.setAlpha(0f);
        kfcCard.setAlpha(0f);
        bukalapakCard.setAlpha(0f);

        hyundaiCard.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(200).start();
        kfcCard.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400).start();
        bukalapakCard.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(600).start();

        pointShow.setText(user_point);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        if("Claimed".equals(user_hyundai)){
            expires1.setText("");
            dateExpired1.setText("Claimed");
            dateExpired1.setTextColor(Color.parseColor("#3BC224"));
        }

        if("Claimed".equals(user_kfc)) {
            expires2.setText("");
            dateExpired2.setText("Claimed");
            dateExpired2.setTextColor(Color.parseColor("#3BC224"));
        }

        if("Claimed".equals(user_bukalapak)){
            expires3.setText("");
            dateExpired3.setText("Claimed");
            dateExpired3.setTextColor(Color.parseColor("#3BC224"));
        }

        // on click hyundai
        redeem1.setOnClickListener(view -> {
            int getPoint = Integer.parseInt(user_point);
            String redeemCode = "AX153FF4597R";
            // check available point
            if(getPoint >= 5000){
                showRedeemCode(redeemCode);
                // check status
                if(!isClaimed(user_hyundai,"Claimed")){
                    int getUserPoint = Integer.parseInt(user_point) - 5000;
                    String newValueForPoint = String.valueOf(getUserPoint);
                    reference.child(user_username).child("point").setValue(newValueForPoint);
                    reference.child(user_username).child("rewards").child("Hyundai Santa FE").child("status").setValue("Claimed");
                    reference.child(user_username).child("rewards").child("Hyundai Santa FE").child("redeem code").setValue(redeemCode);
                }
            } else {
                Toast.makeText(Rewards.this,"You don't have enough point",Toast.LENGTH_SHORT).show();
            }
        });

        readMore1.setOnClickListener(view -> {
            String title = "Hyundai Santa FE";
            String desc = "Get 1 Hyundai Santa FE.";
            String terms = "Available in every Hyundai Distro." +
                    "Limited to reward(s) per customer.";
            String telephone = "+6217463845977";
            String mail = "hyundai@upi.edu";
            String redeemCode = "AX153FF4597R";
            String redeemValue = "Redeem for 5000 points";
            int getPoint = Integer.parseInt(user_point);
            int limitPoint = 5000;
            int brand = R.drawable.hyundai;

            readMore(brand,title,desc,terms,telephone,mail,redeemValue,redeemCode,getPoint,limitPoint);
        });

        redeem2.setOnClickListener(view -> {
            int getPoint = Integer.parseInt(user_point);
            String redeemCode = "AB98JK008933";
            // check available point
            if(getPoint >= 75){
                showRedeemCode(redeemCode);
                if(!isClaimed(user_kfc,"Claimed")){
                    int getUserPoint = Integer.parseInt(user_point) - 75;
                    String newValueForPoint = String.valueOf(getUserPoint);
                    reference.child(user_username).child("point").setValue(newValueForPoint);
                    reference.child(user_username).child("rewards").child("KFC 1 Snack Bucket").child("status").setValue("Claimed");
                    reference.child(user_username).child("rewards").child("KFC 1 Snack Bucket").child("redeem code").setValue(redeemCode);
                }
            } else {
                Toast.makeText(Rewards.this,"You don't have enough point",Toast.LENGTH_SHORT).show();
            }
        });

        readMore2.setOnClickListener(view -> {
            String title = "KFC 1 Snack Bucket";
            String desc = "Get 1 KFC Snack Bucket with 4 wingers, 4 chicken strips, and 1 large french fries in it.";
            String terms = "Available in every Hyundai Distro." +
                    "Limited to reward(s) per customer";
            String telephone = "+6287456243888";
            String mail = "kfc@upi.edu";
            String redeemCode = "AB98JK008933";
            String redeemValue = "Redeem for 75 points";
            int getPoint = Integer.parseInt(user_point);
            int limitPoint = 75;
            int brand = R.drawable.kfc;

            readMore(brand,title,desc,terms,telephone,mail,redeemValue,redeemCode,getPoint,limitPoint);
        });

        redeem3.setOnClickListener(view -> {
            int getPoint = Integer.parseInt(user_point);
            String redeemCode = "78JJ558FFHZ3";
            // check available point
            if(getPoint >= 25){
                showRedeemCode(redeemCode);
                // check status
                if(!isClaimed(user_bukalapak,"Claimed")){
                    int getUserPoint = Integer.parseInt(user_point) - 25;
                    String newValueForPoint = String.valueOf(getUserPoint);
                    reference.child(user_username).child("point").setValue(newValueForPoint);
                    reference.child(user_username).child("rewards").child("Bukalapak 10% Diskon Voucher").child("status").setValue("Claimed");
                    reference.child(user_username).child("rewards").child("Bukalapak 10% Diskon Voucher").child("redeem code").setValue(redeemCode);
                }
            } else {
                Toast.makeText(Rewards.this,"You don't have enough point",Toast.LENGTH_SHORT).show();
            }
        });

        readMore3.setOnClickListener(view -> {
            String title = "Bukalapak 10% Diskon Voucher";
            String desc = "Get 1 Bukalapak 10% Diskon Voucher.";
            String terms = "Available only in Official Store." +
                    "Limited to reward(s) per customer";
            String telephone = "+6285876089567";
            String mail = "bukalapak@upi.edu";
            String redeemCode = "AB98JK008933";
            String redeemValue = "Redeem for 25 points";
            int getPoint = Integer.parseInt(user_point);
            int limitPoint = 25;
            int brand = R.drawable.bukalapak;

            readMore(brand,title,desc,terms,telephone,mail,redeemValue,redeemCode,getPoint,limitPoint);
        });

        back.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),Home.class);
            getValueFromDB(reference,user_username,intents);
        });
    }

    public boolean isClaimed(String status,String thenStatus){
        return thenStatus.equals(status);
    }

    public void showRedeemCode(String redeemCodes){

        popup.setContentView(R.layout.popup_redeem_code);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button copy = popup.findViewById(R.id.button_copy);
        TextView redeemCode = popup.findViewById(R.id.random_code);
//        TextView title = dialog.findViewById(R.id.title_redeem);
        redeemCode.setText(redeemCodes);
        popup.show();

        copy.setOnClickListener(view1 -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Redeem Code",redeemCode.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            clipData.getDescription();
            Toast.makeText(Rewards.this,"Copied",Toast.LENGTH_SHORT).show();
            popup.dismiss();
        });
    }

    public void readMore(int brand,String title,String desc,String terms,String telephone,String mail,String redeemValue,String redeemCode,int getPoint, int limitPoint){
        dialog.setContentView(R.layout.popup_reward_read_more);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView brandIcon = dialog.findViewById(R.id.brand_icon);
        TextView titleReward = dialog.findViewById(R.id.title_reward);
        TextView description = dialog.findViewById(R.id.content_desc_reward);
        TextView termsCondition = dialog.findViewById(R.id.content_tnr_reward);
        TextView NoTelephone = dialog.findViewById(R.id.telephone);
        TextView mailAddress = dialog.findViewById(R.id.mail);
        Button redeem = dialog.findViewById(R.id.redeem_more);

        brandIcon.setImageResource(brand);
        titleReward.setText(title);
        description.setText(desc);
        termsCondition.setText(terms);
        NoTelephone.setText(telephone);
        mailAddress.setText(mail);
        redeem.setText(redeemValue);
        dialog.show();

        redeem.setOnClickListener(view -> {
            if(getPoint >= limitPoint){
                showRedeemCode(redeemCode);
            } else {
                Toast.makeText(Rewards.this,"You don't have enough point",Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
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
}