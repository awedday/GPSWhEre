package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
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

public class RegisterActivity extends AppCompatActivity {

    EditText e4_email;
    ProgressDialog dialog;
    FirebaseAuth auth;
    EditText e3_password;


    Button buttonReg;

    EditText e5_name;
    CircleImageView circleImageView;

    DatabaseReference reference;
    FirebaseUser user;
    StorageReference storageReference;
    Uri resultUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e4_email = (EditText)findViewById(R.id.editText4);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dialog = new ProgressDialog(this);
        e3_password =(EditText)findViewById(R.id.editText3);
        buttonReg =(Button)findViewById(R.id.buttonRegister);
        e5_name = (EditText)findViewById(R.id.editText5);
        circleImageView = (CircleImageView)findViewById(R.id.circleImageView);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");
    }



    public void Registration(View v) {

        dialog.setMessage("Почта проверяется");
        dialog.show();
        auth.fetchSignInMethodsForEmail(e4_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                    if (!check) {
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Данная почта уже была зарегестрирована", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if(e3_password.getText().toString().length()>6) {


        if(resultUri!=null) {

            dialog.setMessage("Пожалуйста, подождите пока создается аккаунт");
            dialog.show();

            auth.createUserWithEmailAndPassword(e4_email.getText().toString(), e3_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //берем данные из реалтайм датабейз
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
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Пожалуйста, выберите фотографию", Toast.LENGTH_SHORT).show();
        }
        }
        else{
            Toast.makeText(getApplicationContext(), "Длина пароля должна быть больше 6 символов", Toast.LENGTH_SHORT).show();
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

//    public void generateCode(View v){
//        Date myDate = new Date();
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
//        String date = format1.format(myDate);
//        Random r = new Random();
//
//        int n = 100000 + r.nextInt(900000);
//        code = String.valueOf(n);
//
//    }
    private void storeData(Uri uri){
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();

        int n = 100000 + r.nextInt(900000);
         String code = String.valueOf(n);

        User newUser = new User();

        newUser.setCode(code);
        newUser.setEmail(e4_email.getText().toString());
        newUser.setImageUrl(uri.toString());
        newUser.setIssharding(false);
        newUser.setLat("");
        newUser.setLng("");
        newUser.setName(e5_name.getText().toString());
        newUser.setPassword(e3_password.getText().toString());
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(RegisterActivity.this, "На почту было отправлено письмо для подтверждения", Toast.LENGTH_SHORT).show();
                        sendVerificationEmail();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                });

    }

    public void sendVerificationEmail() {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"На почту было отправлено подтверждение", Toast.LENGTH_SHORT).show();
                            finish();
                            auth.signOut();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Не можем отправить письмо для подтверждения", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}