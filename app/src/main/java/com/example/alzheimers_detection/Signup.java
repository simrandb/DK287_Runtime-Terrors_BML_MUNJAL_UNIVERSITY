package com.example.alzheimers_detection;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.common.StringUtils;

import java.util.regex.Pattern;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Signup extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            //mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
            //      | View.SYSTEM_UI_FLAG_FULLSCREEN
            //    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    DatabaseReference dbUsers;
    String flag="email";

    RadioGroup radiogroup;
    TextView signupwithphonenumber;
    String username,password,confirmpassword,firstname,lastname,gender;
    RadioButton female,male,other;
    EditText usernameT,passwordT,firstnameT,lastnameT,confirmpasswordT;
    Button next;
    public FirebaseAuth mAuth;
    DatePicker birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        mVisible = true;


        radiogroup=findViewById(R.id.radiogroup);
        other=findViewById(R.id.other);
        signupwithphonenumber=findViewById(R.id.signupwithphonenumber);
        female=findViewById(R.id.female);
        male=findViewById(R.id.male);
        usernameT=findViewById(R.id.username);
        passwordT=findViewById(R.id.password);
        firstnameT=findViewById(R.id.firstname);
        lastnameT=findViewById(R.id.lastname);
        confirmpasswordT=findViewById(R.id.confirmpassword);
        next=(Button)findViewById(R.id.next);
        birthdate=findViewById(R.id.birthdate);

        signupwithphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignupPhone.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                username=usernameT.getText().toString().trim();
                password=passwordT.getText().toString();
                confirmpassword=confirmpasswordT.getText().toString();
                firstname=firstnameT.getText().toString();
                if(username.isEmpty())
                {
                    usernameT.setError("Username is required!");
                    usernameT.requestFocus();
                }
                else if(password.isEmpty())
                {
                    passwordT.setError("Password id is required!");
                    passwordT.requestFocus();
                }
                else if(password.isEmpty() && username.isEmpty())
                {
                    passwordT.setError("Password id is required!");
                    passwordT.requestFocus();
                    usernameT.setError("Email id is required!");
                    usernameT.requestFocus();
                }
                else if(!password.equals(confirmpassword))
                {
                    confirmpasswordT.setError("Confirm password doesn't match!");
                    confirmpasswordT.requestFocus();
                }

                else {

                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if(pattern.matcher(usernameT.getText().toString().trim()).matches()==true)
                    {
                        createAccount(username,password);
                    }
                    else
                    {
                        usernameT.requestFocus();
                        usernameT.setError("Please enter valid email address!");
                    }



                }
            }
        });
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.


        //-------------------Firebase-----------------------
        mAuth = FirebaseAuth.getInstance();

    }
    public void createAccount(String email, String password)
    {
        Log.d("mytag","reached createaccount!!!!!!!!");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "Signup";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fuser;

                            fuser = mAuth.getCurrentUser();
                            //updateUI(user);
                            //-----------------------------------storing data in database
                            fuser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email for verification sent.........");
                                                Toast.makeText(getApplicationContext(),"Signed up successfully! Please check your email for verification.",Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                            nextactivity(fuser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Signup failed. User with same email address must already exist.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }
    public void nextactivity(FirebaseUser fuser)
    {
        Log.d("mytag2","reached nextactivityyyyyyy!!!!!!!!");
        String QRC;
        QRC="0";
        dbUsers= FirebaseDatabase.getInstance().getReference("Users");
        String bdate=birthdate.getDayOfMonth()+"-"+birthdate.getMonth()+"-"+birthdate.getYear();

        User user1=new User(firstnameT.getText().toString().trim(),lastnameT.getText().toString().trim(),male.isSelected()?"Male":female.isSelected()?"Female":"Not mentionable",username,bdate,QRC);
        String id=fuser.getUid();
        dbUsers.child(id).setValue(user1);

        Intent i=new Intent(getApplicationContext(),FamilyHistory.class);
        i.putExtra("flag",flag);
        startActivity(i);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}