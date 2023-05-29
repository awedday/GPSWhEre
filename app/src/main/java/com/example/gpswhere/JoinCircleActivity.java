package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinCircleActivity extends AppCompatActivity {
    Pinview pinview;
    DatabaseReference reference,currentReference;
    FirebaseUser user;
    FirebaseAuth auth;

    String current_user_id, join_user_id;

    DatabaseReference circleReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        pinview = findViewById(R.id.pinview);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        currentReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        current_user_id = user.getUid();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SideActivity.class);
        startActivity(intent);
        finish();
    }


    public  void submitButtonClick(View v){

        Query query = reference.orderByChild("code").equalTo(pinview.getValue());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User createUser = null;
                    for(DataSnapshot childDss : snapshot.getChildren()){
                        createUser = childDss.getValue(User.class);
                        join_user_id = createUser.getUserId();

                        circleReference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(join_user_id).child("CircleMembers");

                        CircleJoin circleJoin = new CircleJoin(current_user_id);
                        CircleJoin circleJoin1 = new CircleJoin(join_user_id);

                        circleReference.child(user.getUid()).setValue(circleJoin)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "Вы подключились к группе", Toast.LENGTH_SHORT).show();
                                        }
                                        
                                    }
                                });

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Указан неверный код", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}