package com.example.gpswhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    EditText subject;
    EditText body;
    TextView email;
    Button buttonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        subject = findViewById(R.id.etSubject);
        body = findViewById(R.id.etBody);
        email = findViewById(R.id.tvSendTo);
        buttonSend = findViewById(R.id.sendEmail);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {"isip_a.p.benzenko@mpt.ru"};

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }else{
                    Toast.makeText(HelpActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SideActivity.class);
        startActivity(intent);
        finish();
    }

}