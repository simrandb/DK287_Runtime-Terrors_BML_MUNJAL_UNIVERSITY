//added images to firebase
package com.example.alzheimers_detection;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ExecutiveFunctioningPart2 extends AppCompatActivity {
    ImageView coinsack,imageView1,imageView2,imageView3,imageView4,imageViewA,imageViewB,imageViewC,imageViewD;
    int count=0,seconds=1;
    String url1,url2,url3,url4,urla,urlb,urlc,urld,urlsack;
    public FirebaseAuth mAuth;
    int num;
    User user;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    OnSwipeTouchListener onSwipeTouchListener;
    String uid;
    String description;
    int sequence[]=new int[8];
    int index=0;
    int score=1;
    String stage_name;
    String username;

    int done=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.executive_functioning_part2);
        final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");

        FirebaseUser fuser;
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();

        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(uid).getValue(User.class);
                username=user.getFirstname();
                seconds=3;
                final Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(seconds>0)
                        {
                            seconds=seconds-1;
                            handler.postDelayed(this,1000);
                        }
                        else
                        {

                        }
                    }
                });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }


        });
        if(username==null)
        {
            username="xyz";
        }

        description = "You are “ "+username+" the Thief ! ”"+"\nEach coin inside the cave has a number or alphabet engraved on it.\n\nTap these coins to " +
                "collect them into sack such that a number is followed by its corresponding alphabet, in " +
                "increasing order, making an alternate trail.";



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        stage_name="ExecutiveFunctioningPart";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.ExecutiveFunctioningPart2),stage_name);

        /*coinsack=findViewById(R.id.coinsack);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageViewA=findViewById(R.id.imageViewA);
        imageViewB=findViewById(R.id.imageViewB);
        imageViewC=findViewById(R.id.imageViewC);
        imageViewD=findViewById(R.id.imageViewD);

        urlsack="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coinsack.png?alt=media&token=418c8957-e4f2-431c-946c-17e2c8f4f79e";
        url3="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin1_3.png?alt=media&token=8a4d411d-b088-4373-b603-f0e186e62cb6";
        urlb="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin1_b.png?alt=media&token=96f3044d-5695-4aab-8287-f0241f96358c";
        urlc="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin4_c.png?alt=media&token=848eb5e9-5905-4c39-a281-7064c483b421";
        url4="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin3_4.png?alt=media&token=9f90e8ad-4d16-4be0-b6d3-7b2b55b07ab7";
        url2="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin6_2.png?alt=media&token=7bd3d180-830f-4f7a-afdf-d6f20a9d8dfe";
        urla="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin6_a.png?alt=media&token=def673d3-232a-4f91-87b1-58de1dd1faca";
        url1="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin3_1.png?alt=media&token=52b64a70-8689-416a-b56b-9e20b93b8958";
        urld="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/coin4_d.png?alt=media&token=0998483b-30cc-4a48-8f26-c4f1ba5f8415";

        Picasso.with(this).load(urlsack).into(coinsack);
        Picasso.with(this).load(url1).into(imageView1);
        Picasso.with(this).load(url2).into(imageView2);
        Picasso.with(this).load(url3).into(imageView3);
        Picasso.with(this).load(url4).into(imageView4);
        Picasso.with(this).load(urla).into(imageViewA);
        Picasso.with(this).load(urlb).into(imageViewB);
        Picasso.with(this).load(urlc).into(imageViewC);
        Picasso.with(this).load(urld).into(imageViewD);*/


        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                PopUp_PlayGame p = new PopUp_PlayGame();
                p.showPopUp(ExecutiveFunctioningPart2.this,description);
            }
        }.start();


    }
    public void OnClickA(View view)
    {
        count++;
        sequence[index]=1;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageViewA);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, true);
            play();
        }
        done();

        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {

                        change_activity(getApplicationContext(),stage_name);
                        /*Intent i=new Intent(getApplicationContext(), Naming_Intro.class);
                        startActivity(i);*/
                    }
                }
            });

        }
    }


    private void change_activity(Context C, String stage_name) {
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);

        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);
                num=user.getNumOfScores();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seconds=3;
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(seconds>0)
                {
                    seconds=seconds-1;
                    handler.postDelayed(this,1000);
                }
                else {
                    fuser = mAuth.getCurrentUser();
                    uid=fuser.getUid();
                    dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                    dbUsers.child("executiveFunctioning").setValue(score);
                    dbUsers.child("attention").setValue(0);
                    dbUsers.child("abstraction").setValue(0);
                    dbUsers.child("immediateRecall").setValue(0);
                    dbUsers.child("delayedRecall").setValue(0);
                    dbUsers.child("calculation").setValue(0);
                    dbUsers.child("orientation").setValue(0);
                    dbUsers.child("visuoperception").setValue(0);
                    dbUsers.child("fluency").setValue(0);
                    dbUsers.child("naming").setValue(0);
                    num++;
                    dbUsers.child("numOfScores").setValue(num);
                    Log.d("num",""+num);


                }

            }
        });

        Popup_aftergame panel = new Popup_aftergame();
        panel.showPopUp(ExecutiveFunctioningPart2.this, stage_name);
    }

    public void OnClickB(View view)
    {
        count++;

        sequence[index]=3;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageViewB);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }
    public void OnClickC(View view)
    {
        count++;

        sequence[index]=5;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageViewC);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 500f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }
    public void OnClickD(View view)
    {
        count++;

        sequence[index]=7;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageViewD);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }

    public void OnClick1(View view)
    {
        count++;

        sequence[index]=0;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageView1);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }
    public void OnClick2(View view)
    {
        count++;

        sequence[index]=2;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ImageView imageButton = findViewById(R.id.imageView2);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 450f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }
    public void OnClick3(View view)
    {
        count++;

        sequence[index]=4;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageView3);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }
    public void OnClick4(View view)
    {
        count++;

        sequence[index]=6;
        index++;
        done++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView imageButton =(ImageView) findViewById(R.id.imageView4);
            AfterClick clickand = new AfterClick(imageButton);
            clickand.animation(140f, 803f, 400f, 2700f, 270f, 80f, false);
            play();
        }
        done();
        if(count==8)
        {
            seconds=1;
            final Handler handler2=new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    if(seconds>0)
                    {
                        seconds=seconds-1;
                        handler2.postDelayed(this,1000);
                    }
                    else
                    {
                        change_activity(getApplicationContext(),stage_name);
                    }
                }
            });

        }
    }

    void play()
    {
        MediaPlayer mysong;
        mysong = MediaPlayer.create(getApplicationContext(), R.raw.coin);
        mysong.start();
    }

    void done()
    {
        if(done==8)
        {
            for(int i=0;i<8;i++)
            {
                if(sequence[i]!=i)
                    score=score*0;
                else
                    score=score*1;

            }
            // if(score==0)
            //{
            //  Toast toast=Toast. makeText(getApplicationContext(),"wrong",Toast. LENGTH_SHORT);
            //toast. show();
            //}
            //else
            //{
            Toast toast=Toast. makeText(getApplicationContext(),"GOOD JOB !",Toast. LENGTH_SHORT);
            toast. show();
            //}

        }

    }

}

class AfterClick
{
    private View view;


    AfterClick(View view){
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void animation( float left,float top,float right,float bottom,float startAngle,float sweepAngle,boolean forcemoveto)
    {


        Path path = new Path();
        path.arcTo(left, top, right, bottom, startAngle, sweepAngle, forcemoveto);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        animator.setDuration(2020);
        animator.start();
        compress_size(view);
        invisible(view);
    }

    void compress_size(final View view)
    {
        ImageView imageButton =(ImageView) view;
        imageButton.animate().
                scaleX(0.5f).
                scaleY(0.5f).
                setDuration(1000).start();

    }

    void invisible(final View view)
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000m
                ImageView imageview =(ImageView) view;
                imageview.setVisibility(View.GONE);
            }
        }, 2000);
    }
}