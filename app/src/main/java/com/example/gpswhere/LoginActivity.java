package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText e1,e2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        auth = FirebaseAuth.getInstance();
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