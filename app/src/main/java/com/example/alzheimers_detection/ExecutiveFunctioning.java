//added images to firebase
package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ExecutiveFunctioning extends AppCompatActivity {
    ImageView nature;
    public FirebaseAuth mAuth;
    int num;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String urlnature,username,uid;
    MediaPlayer mysong;
    OnSwipeTouchListener onSwipeTouchListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.executive_functioning);
        /*final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");

        FirebaseUser fuser;
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(uid).getValue(User.class);
                username=user.getFirstname();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }


        });*/

        mAuth = FirebaseAuth.getInstance();

        final String stage_name="ExecutiveFunctioning";

        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.ExecutiveFunctioning),stage_name);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mysong = MediaPlayer.create(ExecutiveFunctioning.this, R.raw.summer);
        mysong.start();
        final ImageView image = findViewById(R.id.nature);
        final Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        //please try adding a delay of 2 second here before start of animation

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                image.startAnimation(animation1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //change_activity(Play);
                        fuser = mAuth.getCurrentUser();
                        uid=fuser.getUid();

                        final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");

                        FirebaseUser fuser;
                        mAuth = FirebaseAuth.getInstance();
                        fuser = mAuth.getCurrentUser();
                        uid=fuser.getUid();
                        userDBRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.child(uid).getValue(User.class);
                                int num=user.getNumOfScores();
                                num++;


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("UserListActivity", "Error occured");
                            }


                        });

                        final Intent mainIntent = new Intent(ExecutiveFunctioning.this, ExecutiveFunctioningPart2.class);
                        startActivity(mainIntent);
                        ExecutiveFunctioning.this.finish();
                    }
                }, 5500);
            }
        }, 1500);
    }


    @Override
    protected  void onPause()
    {
        super.onPause();
        mysong.release();
        finish();
    }
}