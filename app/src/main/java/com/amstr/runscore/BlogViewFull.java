package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BlogViewFull extends AppCompatActivity {

    // variables
    TextView  judul, tanggal, text;
    ImageView image_content, back_icon;
    Intent intents;
    FirebaseDatabase node;
    DatabaseReference blogReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_view_full);

        // get id
        judul = findViewById(R.id.content_title);
        tanggal = findViewById(R.id.content_date);
        text = findViewById(R.id.content);
        image_content = findViewById(R.id.content_image);
        back_icon = findViewById(R.id.back_icon_blog_view);

        //get content from DB
        Intent intent = getIntent();
        String getCategory = intent.getStringExtra("category");
        //String category = intent.getStringExtra("category_" + getCategory);
        String title = intent.getStringExtra("title_" + getCategory);
        String date = intent.getStringExtra("date_" + getCategory);
        String content = intent.getStringExtra("text_" + getCategory);

        //set value
        judul.setText(title);
        tanggal.setText("Publish on "+date);
        text.setText(content);

        if(getCategory.equals("technology")){
            image_content.setImageResource(R.drawable.technology);
        } else if (getCategory.equals("healthy")){
            image_content.setImageResource(R.drawable.healthy);
        } else if (getCategory.equals("environment")){
            image_content.setImageResource(R.drawable.environment);
        } else if (getCategory.equals("energy")){
            image_content.setImageResource(R.drawable.energy);
        }

        //image_content.setImageResource(R.drawable.achievement_gold);

        //get reference
        node = FirebaseDatabase.getInstance();
        blogReference = node.getReference("blog");

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    private void getContentBlogDB(DatabaseReference reference, String refChild, Intent intent, String category){
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