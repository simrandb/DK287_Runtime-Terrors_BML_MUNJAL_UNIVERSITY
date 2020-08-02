package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignupPhone extends AppCompatActivity {
    int otpflag=0;
    String flag="phone",callnumber;
    User user1;
    TextView signupwithemail,plus91;
    RadioGroup radiogroup;
    TextView verificationcodeheading,tophonenumber,verificationcodetitle,gendertitle,birthdatetitle;
    ImageView verificationcodeimage,phonenumberimage,genderimage,birthdateimage,nameimage,nextarrow;
    Button verifycode;
    EditText otp_et1,otp_et2,otp_et3,otp_et4,otp_et5,otp_et6;
    DatabaseReference dbUsers;
    String username,firstname,lastname,gender,verificationId,code,bdate;
    RadioButton female,male,other;
    Button gotonext;
    EditText usernameT,firstnameT,lastnameT,confirmpasswordT;
    public FirebaseAuth mAuth;
    DatePicker birthdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_phone);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        signupwithemail=findViewById(R.id.signupwithemail);
        mAuth=FirebaseAuth.getInstance();
        plus91=findViewById(R.id.plus91);
        radiogroup=findViewById(R.id.radiogroup);
        gendertitle=findViewById(R.id.gendertitle);
        birthdatetitle=findViewById(R.id.birthdatetitle);
        other=findViewById(R.id.other);
        female=findViewById(R.id.female);
        male=findViewById(R.id.male);
        usernameT=findViewById(R.id.usernameT);
        firstnameT=findViewById(R.id.firstnameT);
        lastnameT=findViewById(R.id.lastnameT);
        gotonext=(Button)findViewById(R.id.next);
        birthdate=findViewById(R.id.birthdate);
        phonenumberimage=findViewById(R.id.phonenumberimage);
        genderimage=findViewById(R.id.genderimage);
        nameimage=findViewById(R.id.nameimage);
        birthdateimage=findViewById(R.id.birthdateimage);
        nextarrow=findViewById(R.id.nextarrow);


        gotonext=findViewById(R.id.gotonext);

        gotonext.setVisibility(View.INVISIBLE);
        verificationcodeheading=findViewById(R.id.verificationcodeheading);
        verificationcodeimage=findViewById(R.id.verificationcodeimage);
        verificationcodetitle=findViewById(R.id.verificationcodetitle);
        verifycode=findViewById(R.id.verifycode);
        tophonenumber=findViewById(R.id.tophonenumber);

        otp_et1=findViewById(R.id.otp_et1);
        otp_et2=findViewById(R.id.otp_et2);
        otp_et3=findViewById(R.id.otp_et3);
        otp_et4=findViewById(R.id.otp_et4);
        otp_et5=findViewById(R.id.otp_et5);
        otp_et6=findViewById(R.id.otp_et6);


        verificationcodeheading.setVisibility(View.INVISIBLE);
        verificationcodetitle.setVisibility(View.INVISIBLE);
        verifycode.setVisibility(View.INVISIBLE);
        verificationcodeimage.setVisibility(View.INVISIBLE);
        tophonenumber.setVisibility(View.INVISIBLE);
        otp_et6.setVisibility(View.INVISIBLE);
        otp_et5.setVisibility(View.INVISIBLE);
        otp_et4.setVisibility(View.INVISIBLE);
        otp_et3.setVisibility(View.INVISIBLE);
        otp_et2.setVisibility(View.INVISIBLE);
        otp_et1.setVisibility(View.INVISIBLE);

        signupwithemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
            }
        });

        nextarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnumber=usernameT.getText().toString().trim();
                Log.d("call",""+callnumber);
                String MobilePattern = "[0-9]{10}";
                if(firstnameT.getText().toString().trim().length()==0)
                {
                    firstnameT.requestFocus();
                    firstnameT.setError("Please enter first name!");
                }
                else if(lastnameT.getText().toString().trim().length()==0)
                {
                    lastnameT.requestFocus();
                    lastnameT.setError("Please enter first name!");
                }
                else if(!(usernameT.getText().toString().trim().matches(MobilePattern)))
                {
                    usernameT.requestFocus();
                    usernameT.setError("Please enter valid phone number!");
                }
                else if((radiogroup.getCheckedRadioButtonId() == -1))
                {
                    radiogroup.requestFocus();
                    other.setError("Please select one of the gender options!");
                }
                else
                {

                    successfulphoneregistration();
                }


            }
        });

        verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=""+otp_et1+otp_et2+otp_et3+otp_et4+otp_et5+otp_et6;
                verifyCode(code);
            }
        });

    }


    void successfulphoneregistration()
    {
        firstname=firstnameT.getText().toString().trim();
        lastname=lastnameT.getText().toString().trim();
        gender=male.isSelected()?"Male":female.isSelected()?"Female":"Other";
        username=usernameT.getText().toString().trim();
        tophonenumber.setText("username");
        bdate=birthdate.getDayOfMonth()+"-"+birthdate.getMonth()+"-"+birthdate.getYear();
        String QRC="0";
        user1=new User(firstnameT.getText().toString().trim(),lastnameT.getText().toString().trim(),male.isSelected()?"Male":female.isSelected()?"Female":"Not mentionable",username,bdate,QRC);




        Log.d("sms","Verification code sent!!");
        plus91.setVisibility(View.INVISIBLE);
        other.setVisibility(View.INVISIBLE);
        female.setVisibility(View.INVISIBLE);
        male.setVisibility(View.INVISIBLE);
        usernameT.setVisibility(View.INVISIBLE);
        birthdate.setVisibility(View.INVISIBLE);
        birthdateimage.setVisibility(View.INVISIBLE);
        genderimage.setVisibility(View.INVISIBLE);
        phonenumberimage.setVisibility(View.INVISIBLE);
        firstnameT.setVisibility(View.INVISIBLE);
        lastnameT.setVisibility(View.INVISIBLE);
        gendertitle.setVisibility(View.INVISIBLE);
        birthdatetitle.setVisibility(View.INVISIBLE);
        nameimage.setVisibility(View.INVISIBLE);
        signupwithemail.setVisibility(View.INVISIBLE);
        verificationcodeheading.setVisibility(View.VISIBLE);
        verificationcodetitle.setVisibility(View.VISIBLE);
        verifycode.setVisibility(View.VISIBLE);
        verificationcodeimage.setVisibility(View.VISIBLE);
        tophonenumber.setVisibility(View.VISIBLE);
        otp_et6.setVisibility(View.VISIBLE);
        otp_et5.setVisibility(View.VISIBLE);
        otp_et4.setVisibility(View.VISIBLE);
        otp_et3.setVisibility(View.VISIBLE);
        otp_et2.setVisibility(View.VISIBLE);
        otp_et1.setVisibility(View.VISIBLE);
        otp_et1.requestFocus();
        nextarrow.setVisibility(View.INVISIBLE);
        otpflag=1;
        gotonext.setVisibility(View.VISIBLE);
        tophonenumber.setText("+91 "+username);


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+callnumber,
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,mCallbacks);

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
                    dbUsers= FirebaseDatabase.getInstance().getReference("Users");
                    FirebaseUser fuser = mAuth.getCurrentUser();
                    String id=fuser.getUid();
                    dbUsers.child(id).setValue(user1);

                    Intent i=new Intent(getApplicationContext(),FamilyHistory.class);
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