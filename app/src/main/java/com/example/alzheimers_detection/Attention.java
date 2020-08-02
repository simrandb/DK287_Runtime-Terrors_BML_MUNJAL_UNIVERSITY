

//images added to firebase storage
package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.*;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Attention extends AppCompatActivity {
    ImageView picture,donutdecor2,donutdecor,uncle,donutstand,open;
    TextView instruction1,instruction2,instruction3;
    double seconds=1,score=0.0;
    ProgressBar progressBar2;
    String description,uid,username;
    String urldonutdecor2,urldonutdecor,urluncle,urldonutstand,urlopen;
    Button yes,no;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    OnSwipeTouchListener onSwipeTouchListener;
    public static Integer[] mThumbIds = {
            R.drawable.tree1, R.drawable.tree2,R.drawable.tree2,  R.drawable.tree3,R.drawable.tree1, R.drawable.tree1,R.drawable.tree1,R.drawable.tree6,R.drawable.tree2, R.drawable.tree6};
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention);
        final String stage_name="Attention";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Attention),stage_name);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");


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
        description = "\nYou are \n“"+username+" – The Doughnut Seller“.\n\nLook at each doughnut carefully and answer in terms of" +
                " \n“YES / NO” based on whether the previous doughnut is same as the current one.";




        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");


        mAuth = FirebaseAuth.getInstance();

        picture =  findViewById(R.id.picture);
        picture.setImageResource(mThumbIds[0]);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        yes.setEnabled(false);
        no.setEnabled(false);
        instruction1=findViewById(R.id.instruction1);
        instruction2=findViewById(R.id.instruction2);
        instruction3=findViewById(R.id.instruction3);
        open=findViewById(R.id.open);
        donutdecor=findViewById(R.id.donutdecor);
        donutdecor2=findViewById(R.id.donutdecor2);
        donutstand=findViewById(R.id.donutstand);


        uncle=findViewById(R.id.uncle);


        urldonutdecor="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/pic1.png?alt=media&token=07254029-77fd-44ff-b7c5-93a9e5e0500d";
        urldonutdecor2="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/pic2.png?alt=media&token=13ea4f0d-cd5d-4e5c-9f04-6411a5436571";
        urldonutstand="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/doughnutstand.jpg?alt=media&token=c19553cb-0fe1-434f-be9a-f28015a969eb";
        urlopen="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/open.png?alt=media&token=1a2bbba3-ff80-41d6-8ec8-720103b9a3b0";
        urluncle="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/man.gif?alt=media&token=59a1f2b5-b54e-4f49-8d4c-2d4a48266384";
        //Picasso.with(this).load(urluncle).into(uncle);

        Picasso.with(this).load(urldonutdecor).into(donutdecor);
        Picasso.with(this).load(urldonutdecor2).into(donutdecor2);
        Picasso.with(this).load(urldonutstand).into(donutstand, new Callback(){
            @Override
            public void onSuccess() {
                afterDonutLoad();
            }

            @Override
            public void onError() {

            }
        });
        Picasso.with(this).load(urlopen).into(open);
            new CountDownTimer(2000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    PopUp_PlayGame p = new PopUp_PlayGame();
                    p.showPopUp(Attention.this,description);
                }
            }.start();

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mThumbIds[i]==mThumbIds[i-1])
                    {
                        score=score+1;
                    }
                    i++;
                    if(!(i==mThumbIds.length))
                    {
                        picture.setImageResource(R.drawable.blank);
                        picture.setAlpha(0);
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
                                    picture.setImageResource(mThumbIds[i]);
                                    picture.setAlpha(255);
                                }
                            }
                        });
                    }
                    else
                    {
                        yes.setEnabled(false);
                        no.setEnabled(false);
                       // Toast.makeText(getApplicationContext(),"score:"+(score/3),Toast.LENGTH_LONG).show();

                        Intent i=new Intent(getApplicationContext(), Visuoperception_Intro.class);
                        startActivity(i);
                        Log.d("score",""+(score/3));


                    }
                }
            });


            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mThumbIds[i]!=mThumbIds[i-1])
                    {
                        score=score+1;
                    }

                    i++;
                    if(!(i==mThumbIds.length))
                    {
                        picture.setImageResource(R.drawable.blank);
                        picture.setAlpha(0);
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
                                    picture.setImageResource(mThumbIds[i]);
                                    picture.setAlpha(255);
                                }
                            }
                        });
                    }
                    else
                    {
                        yes.setEnabled(false);
                        no.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"score:"+(score/3),Toast.LENGTH_LONG).show();

                        fuser = mAuth.getCurrentUser();
                        uid=fuser.getUid();
                        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                        dbUsers.child("attention").setValue((score/3));
                        Intent i=new Intent(getApplicationContext(), Visuoperception_Intro.class);
                        startActivity(i);
                        Log.d("score",""+(score/3));
                    }
                }
            });

        }
    public void afterDonutLoad()
    {
        instruction3.setVisibility(View.INVISIBLE);
        instruction2.setVisibility(View.INVISIBLE);
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
                    picture.setImageResource(R.drawable.blank);
                    picture.setAlpha(0);
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
                                i=1;
                                picture.setImageResource(mThumbIds[1]);
                                instruction1.setVisibility(View.INVISIBLE);
                                instruction2.setVisibility(View.VISIBLE);
                                instruction3.setVisibility(View.VISIBLE);
                                picture.setAlpha(255);
                                yes.setEnabled(true);
                                no.setEnabled(true);

                            }
                        }
                    });
                }
            }
        });
    }
}