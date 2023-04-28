package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText e1,e2;
    FirebaseAuth auth;

    Button login;
    int log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        auth = FirebaseAuth.getInstance();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        login = (Button)findViewById(R.id.login);
        log = (int)getIntent().getExtras().get("walls");

        switch (log){
            case 1:
                relativeLayout.setBackgroundResource(R.drawable.wallspaperlogregfive);
                login.setBackgroundColor(getResources().getColor(R.color.buttonmainfive, null));
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.drawable.wallspaperlogregtwo);
                login.setBackgroundColor(getResources().getColor(R.color.buttonmaintwo, null));
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.drawable.wallspaperlogregthree);
                login.setBackgroundColor(getResources().getColor(R.color.buttonmainthree, null));
                break;
            case 4:
                relativeLayout.setBackgroundResource(R.drawable.wallspaperlogregfour);
                login.setBackgroundColor(getResources().getColor(R.color.buttonmainfour, null));
                break;

        }
    }
    public void login(View v){
        auth.signInWithEmailAndPassword(e1.getText().toString(),e2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //Toast.makeText(getApplicationContext(),"Пользователь успешно авторизовался", Toast.LENGTH_LONG).show();
                    FirebaseUser user = auth.getCurrentUser();
                    if(user.isEmailVerified()){
                        Intent intent = new Intent(LoginActivity.this, SideActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Вы не подтвердили почту", Toast.LENGTH_LONG).show();

                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Неверный логин или пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}