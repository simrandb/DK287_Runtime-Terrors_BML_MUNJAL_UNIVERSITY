package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginOtpByPhone extends AppCompatActivity {
    String verificationId,code;
    Button verifycode;
    EditText otp_et1,otp_et2,otp_et3,otp_et4,otp_et5,otp_et6;
    TextView tophonenumber;
    String callnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_otpbyphone);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        tophonenumber=findViewById(R.id.tophonenumber);
        otp_et1=findViewById(R.id.otp_et1);
        otp_et2=findViewById(R.id.otp_et2);
        otp_et3=findViewById(R.id.otp_et3);
        otp_et4=findViewById(R.id.otp_et4);
        otp_et5=findViewById(R.id.otp_et5);
        otp_et6=findViewById(R.id.otp_et6);
        verifycode=findViewById(R.id.verifycode);
        Bundle bundle = getIntent().getExtras();


        callnumber=bundle.getString("tophonenumber").trim();
        tophonenumber.append(""+callnumber);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+callnumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,mCallbacks);


        verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=""+otp_et1+otp_et2+otp_et3+otp_et4+otp_et5+otp_et6;
                verifyCode(code);
            }
        });

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
            Log.d("verificationcode",""+verificationId);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=  phoneAuthCredential.getSmsCode();
            verifyCode(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

    void verifyCode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        sigInWithCredential(credential);
    }
    void sigInWithCredential(PhoneAuthCredential credential)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if     (task.isSuccessful())
                {

                    Intent i=new Intent(getApplicationContext(),HomeScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}