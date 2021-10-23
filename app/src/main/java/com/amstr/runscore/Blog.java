package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amstr.runscore.BlogAdapter.BlogFeaturedHelperClass;
import com.amstr.runscore.BlogAdapter.FeaturedAdapterBlog;
import com.amstr.runscore.HomeAdapter.FeaturedAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Blog extends AppCompatActivity implements FeaturedAdapterBlog.OnCardListener {

    // variables
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
//    ArrayList<BlogFeaturedHelperClass> featuredLocations = new ArrayList<>();
    FirebaseDatabase rootNode;
    DatabaseReference blogReference,reference;
    Intent intents;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        // get id
        featuredRecycler = findViewById(R.id.content_view);
        back = findViewById(R.id.back_icon_blog);

        featuredRecycler.setTranslationX(800f);
        featuredRecycler.setAlpha(0f);
        featuredRecycler.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(200).start();

        // get content from db
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String category_technology = intent.getStringExtra("category_technology");
        String date_technology = intent.getStringExtra("date_technology");
        String title_technology = intent.getStringExtra("title_technology");
        String content_technology = intent.getStringExtra("text_technology");

        String category_healthy = intent.getStringExtra("category_healthy");
        String date_healthy = intent.getStringExtra("date_healthy");
        String title_healthy = intent.getStringExtra("title_healthy");
        String content_healthy = intent.getStringExtra("text_healthy");

        String category_environment = intent.getStringExtra("category_environment");
        String date_environment = intent.getStringExtra("date_environment");
        String title_environment = intent.getStringExtra("title_environment");
        String content_environment = intent.getStringExtra("text_environment");

        String category_energy = intent.getStringExtra("category_energy");
        String date_energy = intent.getStringExtra("date_energy");
        String title_energy = intent.getStringExtra("title_energy");
        String content_energy = intent.getStringExtra("text_energy");

        // get database
        rootNode = FirebaseDatabase.getInstance();
        blogReference = rootNode.getReference("blog");
        reference = rootNode.getReference("users");

        back.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),Home.class);
            getValueFromDB(reference,user_username,intents);
        });

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<BlogFeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new BlogFeaturedHelperClass(R.drawable.technology,category_technology,date_technology,title_technology,content_technology));
        featuredLocations.add(new BlogFeaturedHelperClass(R.drawable.healthy,category_healthy,date_healthy,title_healthy,content_healthy));
        featuredLocations.add(new BlogFeaturedHelperClass(R.drawable.environment,category_environment,date_environment,title_environment,content_environment));
        featuredLocations.add(new BlogFeaturedHelperClass(R.drawable.energy,category_energy,date_energy,title_energy,content_energy));

        adapter = new FeaturedAdapterBlog(featuredLocations, this);
        featuredRecycler.setAdapter(adapter);
    }

    @Override
    public void cardOnClick(int position) {
        String category,child;
        switch(position){
            case 0: category = "technology"; child = "Teknologi"; break;
            case 1: category = "healthy"; child = "Kesehatan"; break;
            case 2: category = "environment"; child = "Lingkungan"; break;
            case 3: category = "energy"; child = "Energi"; break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        
        intents = new Intent(getApplicationContext(),BlogViewFull.class);
        getContentBlogDB(blogReference,child,intents,category);
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
                    intent.putExtra("category",category);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}