package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
    Button sendemail,gotologin;
    EditText emailforpassword;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        sendemail=findViewById(R.id.senemail);
        emailforpassword=findViewById(R.id.emailforpassword);
        gotologin=findViewById(R.id.gotologin);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(emailforpassword.getText().toString().trim()).matches()==true)
                {
                    auth.sendPasswordResetEmail(emailforpassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(),"Check your email to reset your password!!",Toast.LENGTH_LONG).show();
                                    } else {

                                        Toast.makeText(getApplicationContext(),"Couldnt send password reset email",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
                else
                {
                    emailforpassword.requestFocus();
                    emailforpassword.setError("Please enter valid email id!");
                }
            }
        });


        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });


    }
}