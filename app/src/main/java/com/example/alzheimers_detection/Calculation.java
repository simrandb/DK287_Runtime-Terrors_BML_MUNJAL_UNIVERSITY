package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Calculation extends AppCompatActivity {
    int score=0;
double seconds;

    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    String description;
    OnSwipeTouchListener onSwipeTouchListener;
    String stage_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation);

         description = "\n\nFly and enjoy through three \nlevels of sky. \n\nTap on the cloud with greater value to burst it and move to a higher level in the sky.";

        stage_name="Calculation";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Calculation),stage_name);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");
            new CountDownTimer(1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    PopUp_PlayGame p = new PopUp_PlayGame();
                    p.showPopUp(Calculation.this,description);
                }
            }.start();


    }

    void decrese_size(final View view)
    {
        ImageView imageButton =(ImageView) view;
        imageButton.animate().
                scaleX(0.6f).
                scaleY(0.6f).
                setDuration(100).start();

    }
    void increase_size(final View view)
    {
        ImageView imageButton =(ImageView) view;
        imageButton.animate().
                scaleX(1.5f).
                scaleY(1.5f).
                setDuration(100).start();

    }


    public void click(final View v ) {
        final int[] case_ = {0};
        final Handler handler = new Handler();
        final ImageView burst = (ImageView) v;
        increase_size(burst);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000m
                decrese_size(burst);
            }
        }, 200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000m
                burst.setVisibility(View.GONE);
            }
        }, 500);

        //add sound of burst here.

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(v.getId()==R.id.cloud1a||v.getId()==R.id.cloud1b)
                {
                    if(v.getId()==R.id.cloud1a)
                        score++;
                    case_[0] =1;
                }
                /*else if(v.getId()==R.id.cloud2a||v.getId()==R.id.cloud2b)
                {
                    if(v.getId()==R.id.cloud2b)
                        score++;
                    case_[0] =2;
                }*/
                else if(v.getId()==R.id.cloud3a||v.getId()==R.id.cloud3b)
                {
                    if(v.getId()==R.id.cloud3b)
                        score++;
                    case_[0] =3;
                }
                else if(v.getId()==R.id.cloud4a||v.getId()==R.id.cloud4b)
                {
                    if(v.getId()==R.id.cloud4a)
                        score++;
                        case_[0] =4;
                }



                final ImageView cloud1a = (ImageView) findViewById(R.id.cloud1a);
                final ImageView cloud1b = (ImageView) findViewById(R.id.cloud1b);
                //final ImageView cloud2a = (ImageView) findViewById(R.id.cloud2a);
                //final ImageView cloud2b = (ImageView) findViewById(R.id.cloud2b);
                final ImageView cloud3a = (ImageView) findViewById(R.id.cloud3a);
                final ImageView cloud3b = (ImageView) findViewById(R.id.cloud3b);
                final ImageView cloud4a = (ImageView) findViewById(R.id.cloud4a);
                final ImageView cloud4b = (ImageView) findViewById(R.id.cloud4b);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (case_[0]) {
                            case (1):
                                cloud1a.setVisibility(View.INVISIBLE);
                                cloud1b.setVisibility(View.INVISIBLE);
                                cloud3a.setVisibility(View.VISIBLE);
                                cloud3b.setVisibility(View.VISIBLE);

                               // cloud2a.setVisibility(View.VISIBLE);
                                //cloud2b.setVisibility(View.VISIBLE);
                                //stuff
                                break;

                            case (2):
                                //cloud2a.setVisibility(View.INVISIBLE);
                                //cloud2b.setVisibility(View.INVISIBLE);

                                cloud3a.setVisibility(View.VISIBLE);
                                cloud3b.setVisibility(View.VISIBLE);
                                //stuff
                                break;

                            case (3):
                                cloud3a.setVisibility(View.INVISIBLE);
                                cloud3b.setVisibility(View.INVISIBLE);

                                cloud4a.setVisibility(View.VISIBLE);
                                cloud4b.setVisibility(View.VISIBLE);
                                //stuff
                                break;

                            case(4):
                                cloud4a.setVisibility(View.INVISIBLE);
                                cloud4b.setVisibility(View.INVISIBLE);
                                 seconds=0.5;
                                final Handler handler=new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(seconds>0)
                                        {
                                            seconds=seconds-0.5;
                                            handler.postDelayed(this,500);
                                        }
                                        else
                                        {
                                            fuser = mAuth.getCurrentUser();
                                            uid=fuser.getUid();
                                            dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                                            dbUsers.child("calculation").setValue(score);
                                            Popup_aftergame panel = new Popup_aftergame();
                                            panel.showPopUp(Calculation.this, stage_name);
                                            /*Intent i=new Intent(getApplicationContext(), Orientation_Intro.class);
                                            startActivity(i);*/

                                        }
                                    }
                                });
                                break;
                        }

                    }
                }, 500);
                MediaPlayer mysong;
                mysong = MediaPlayer.create(getApplicationContext(), R.raw.burst);
                mysong.start();
            }
        }, 200);



    }
}