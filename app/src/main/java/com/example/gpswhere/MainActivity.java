package com.example.gpswhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    final Random random = new Random();
    int Wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Wallpaper = random.nextInt(5);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);



        switch (Wallpaper){
            case 1:
                relativeLayout.setBackgroundResource(R.drawable.japan);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.drawable.usa);
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.drawable.russia);
                break;
            case 4:
                relativeLayout.setBackgroundResource(R.drawable.paris);
                break;
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            setContentView(R.layout.activity_main);
        }
        else{
//            Intent myIntent = new Intent(MainActivity.this,SideActivity.class);
//            startActivity(myIntent);
//            finish();
        }


    }

    public void goToLogin(View v){
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
    }

    public void goToRegister(View v){
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(myIntent);
    }
}