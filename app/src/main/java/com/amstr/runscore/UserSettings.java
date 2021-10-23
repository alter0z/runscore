package com.amstr.runscore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amstr.runscore.BlogAdapter.FeaturedAdapterBlog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSettings extends AppCompatActivity {

    // variables
    CircleImageView profileImage;
    CardView identity,security,about;
    ImageView back,logout;
    DatabaseReference reference;
    TextView name,point;
    Uri imageUri;
    Intent intents;
//    Bitmap bitmap;
    String UserID = "";
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;
//    Boolean hasCropped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        profileImage = findViewById(R.id.profile_image);
        identity = findViewById(R.id.identity_card);
        security = findViewById(R.id.security_card);
        about = findViewById(R.id.about_card);
        back = findViewById(R.id.back_icon_setting);
        logout = findViewById(R.id.logout_icon_setting);
        name = findViewById(R.id.show_name);
        point = findViewById(R.id.show_point);

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_point = intent.getStringExtra("point");

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        UserID = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("users");

        name.setText(user_name);
        point.setText(user_point+" Points");

        UserID = user_username;

//        // change picture
//        profileImage.setOnClickListener(view -> {
////            Dexter.withActivity((Activity) getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
////                @Override
////                public void onPermissionGranted(PermissionGrantedResponse response) {
////                    Intent intent = new Intent();
////                    intent.setType("image/*");
////                    intent.setAction(Intent.ACTION_GET_CONTENT);
////                    startActivityForResult(Intent.createChooser(intent,"please select image"),101);
////                }
////
////                @Override
////                public void onPermissionDenied(PermissionDeniedResponse response) {
////
////                }
////
////                @Override
////                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
////                    token.continuePermissionRequest();
////                }
////            }).check(); //hasCropped = true;
//
//            CropImage.activity().setAspectRatio(1, 1).start(UserSettings.this);
//        });

//        if(!hasCropped){
//            Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_SHORT).show();
//        } else {
//            uploadImage();
//        }

        // sign out
        logout.setOnClickListener(view -> {
            //intents = new Intent(getApplicationContext(),Identity.class);
        });

        back.setOnClickListener(view -> {
            uploadProfileImage(user_username);
            intents = new Intent(getApplicationContext(),Home.class);
            getValueFromDB(reference,user_username,intents);
        });

        identity.setOnClickListener(view -> {
            intents = new Intent(getApplicationContext(),Identity.class);
            getValueFromDB(reference,user_username,intents);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            try {
                profileImage.setImageURI(imageUri);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(imageUri);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                profileImage.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        }
    }

//    public void uploadImage(){
//        final StorageReference uploader = storageReference.child("profileimages/"+"img"+System.currentTimeMillis());
//        uploader.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        final Map<String,Object> map = new HashMap<>();
//                        map.put("profile image",uri.toString());
//
//                        reference.child(UserID).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.exists()){
//                                    reference.child(UserID).updateChildren(map);
//                                } else {
//                                    reference.child(UserID).setValue(map);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//                        Toast.makeText(getApplicationContext(),"Profile image has been changed",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }

    private void uploadProfileImage(String UserID){
        if(imageUri != null){
            StorageReference fileReference = storageReference.child(UserID);
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation) task -> {
                if(!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    myUri = downloadUri.toString();

                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("image",myUri);

                    reference.child(UserID).child("profile image").setValue(userMap);
                }
            });
            Toast.makeText(this,"profile image has been changed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Intent intent = getIntent();
//
//        UserID = intent.getStringExtra("username");
//        reference.child(UserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Glide.with(getApplicationContext()).load(snapshot.child("profile image").getValue().toString()).into(profileImage);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

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