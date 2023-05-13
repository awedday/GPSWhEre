package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeAvatarActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    Uri resultUri;
    Button changeAvatar;
    StorageReference storageReference, newPhoto;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        circleImageView = (CircleImageView)findViewById(R.id.circleImageView);
        changeAvatar =(Button)findViewById(R.id.changeAvatar);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");
    }

    public void ChangeAvatar(View v) {
     if(resultUri!=null) {
                    StorageReference storRef = FirebaseStorage.getInstance().getReference("profile")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("profile.jpg");

                    storRef.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    storeData(task.getResult());
                                                    
                                                        Intent myIntent = new Intent(ChangeAvatarActivity.this, SideActivity.class);
                                                        startActivity(myIntent);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("err2", e.getMessage());
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("err1", "" + e.getMessage());
                                }
                            });

                    }

    }

    public void selectImage(View v){
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            resultUri = data.getData();
            circleImageView.setImageURI(resultUri);
        }
    }

    private void storeData(Uri uri){


        newPhoto = FirebaseStorage.getInstance().getReference("profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("profile.jpg").getStorage().getReferenceFromUrl(uri.toString());

    }
}

