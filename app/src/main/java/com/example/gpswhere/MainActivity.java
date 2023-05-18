package com.example.gpswhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    final Random random = new Random();
    int Wallpaper;
    Button signIn,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Wallpaper = random.nextInt(5);
        signUp = (Button)findViewById(R.id.signup);
        signIn = (Button)findViewById(R.id.signin);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);





        switch (Wallpaper){
            case 1:
                relativeLayout.setBackgroundResource(R.drawable.wallspapermainactivityfive);
                signIn.setBackgroundColor(getResources().getColor(R.color.buttonmainfive, null));
                signUp.setBackgroundColor(getResources().getColor(R.color.buttonmainfive, null));
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.drawable.wallspapermainactivitytwo);
                signIn.setBackgroundColor(getResources().getColor(R.color.buttonmaintwo, null));
                signUp.setBackgroundColor(getResources().getColor(R.color.buttonmaintwo, null));
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.drawable.wallspapermainactivitythree);
                signIn.setBackgroundColor(getResources().getColor(R.color.buttonmainthree, null));
                signUp.setBackgroundColor(getResources().getColor(R.color.buttonmainthree, null));
                break;
            case 4:
                relativeLayout.setBackgroundResource(R.drawable.wallspapermainactivityfour);
                signIn.setBackgroundColor(getResources().getColor(R.color.buttonmainfour, null));
                signUp.setBackgroundColor(getResources().getColor(R.color.buttonmainfour, null));
                break;

        }

        System.out.print(Wallpaper);
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
        myIntent.putExtra("walls", Wallpaper);
        startActivity(myIntent);
    }

    public void goToRegister(View v){
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        myIntent.putExtra("walls", Wallpaper);
        startActivity(myIntent);
    }
}