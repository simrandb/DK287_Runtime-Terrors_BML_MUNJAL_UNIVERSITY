//Images added to firebase storage
package com.example.alzheimers_detection;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Abstraction extends AppCompatActivity {
    MediaPlayer mysong;
    double seconds=3;

    int totalscore=3,instrumentscore=0,sportscore=0,fruitscore=0,countofdroppeditems=0;
    TextView i1,i2,f1,f2,s1,s2;
    ImageView fruitsb,instrumentsb,sportsb;
    int xs,ys,xf,yf,xi,yi;
    private int xdelta,ydelta;
/*
        mAuth = FirebaseAuth.getInstance();

 fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("abstraction").setValue(totalscore);

*/

    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;

    String description;
    String stage_name;
    OnSwipeTouchListener onSwipeTouchListener;

    String urlbucket,urlavocado,urlguitar,urllamo,urlpineapple,urlgrapes,urltabla,urltrumpet,urlball,urlsoccerball,urltennis;
    ImageView chandlier1,chandlier2,chandlier3, pineapple,tennis,ball,guitar,soccerball,trumpet,grapes,tabla,avocado,fruitsbucket,instrumentsbucket,sportsbucket,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abstraction);
        stage_name="Abstraction";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Abstraction),stage_name);

        description = "\nMultiple items corresponding to three different categories namely -\n Sports, Instruments and Fruits \nare displayed on a shelf."+"\n\nDrag and drop each item into its corresponding " +
                "basket to empty\n the shelf.";

        mAuth = FirebaseAuth.getInstance();

        mysong = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mysong.setLooping(true);    //plays sound on loop
        mysong.start();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        chandlier1=findViewById(R.id.chandlier1);
        chandlier2=findViewById(R.id.chandlier2);
        chandlier3=findViewById(R.id.chandlier3);
        i1=findViewById(R.id.instrumentsmall);
        i2=findViewById(R.id.instrumentbig);
        s1=findViewById(R.id.sportsmall);
        s2=findViewById(R.id.sportbig);
        f1=findViewById(R.id.fruitsmall);
        f2=findViewById(R.id.fruitbig);
        grapes = findViewById(R.id.grapes);
        tabla = findViewById(R.id.tabla);
        guitar = findViewById(R.id.guitar);
        ball = findViewById(R.id.ball);
        soccerball = findViewById(R.id.soccerball);
        pineapple = findViewById(R.id.pineapple);
        tennis = findViewById(R.id.tennis);
        avocado = findViewById(R.id.avocado);
        trumpet = findViewById(R.id.trumpet);
        fruitsbucket = findViewById(R.id.fruitsbucket);
        sportsbucket = findViewById(R.id.sportsbucket);
        instrumentsbucket = findViewById(R.id.instrumentsbucket);
        fruitsb=findViewById(R.id.bucketfruits);
        sportsb=findViewById(R.id.bucketsports);
        instrumentsb=findViewById(R.id.bucketinstruments);
        urlavocado="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/avacado.png?alt=media&token=db93ed0c-aa99-44e2-adeb-d37a5d0423a8";
        urlball="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/volleyball.png?alt=media&token=d6857a7d-515e-4d57-bc74-b5da5143773c";
        urlgrapes="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/grapes.png?alt=media&token=820f2a7e-6553-4f3d-ad9b-ce47ca1546ba";
        urlguitar="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/guitar.png?alt=media&token=95913736-d395-41c1-a033-e65869d576c4";
        urllamo="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/lamo.png?alt=media&token=b94a52a6-e687-4d22-acb4-2dea8866b749";
        urlpineapple="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/pineapple.png?alt=media&token=c20b2ab6-e877-4e4a-bf6d-26489a156535";
        urlsoccerball="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/soccerball.png?alt=media&token=d937cc9c-2db0-4dbd-bb20-94b5128fa45f";
        urltabla="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/tabla1.png?alt=media&token=59409009-9648-4371-ac1c-e66774613116";
        urltennis="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/tennis.png?alt=media&token=3c879979-070a-4d7d-ada1-37d9258139b8";
        urltrumpet="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/saxophone.png?alt=media&token=24a88bb7-1939-472a-b5ee-6dbfe26024b2";
        urlbucket="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/bucket.png?alt=media&token=f180f368-1ff8-431d-adad-c33b78ec5f05";

        Picasso.with(this).load(urlavocado).into(avocado);
        //Picasso.with(this).load(urlball).into(ball);
        Picasso.with(this).load(urlbucket).into(fruitsbucket);
        Picasso.with(this).load(urlbucket).into(instrumentsbucket);
        Picasso.with(this).load(urlbucket).into(sportsbucket);
        Picasso.with(this).load(urlbucket).into(fruitsb);
        Picasso.with(this).load(urlbucket).into(instrumentsb);
        Picasso.with(this).load(urlbucket).into(sportsb);
        Picasso.with(this).load(urlgrapes).into(grapes);
        Picasso.with(this).load(urlguitar).into(guitar);
        Picasso.with(this).load(urllamo).into(chandlier1);
        Picasso.with(this).load(urllamo).into(chandlier2);
        Picasso.with(this).load(urllamo).into(chandlier3);
        //Picasso.with(this).load(urlpineapple).into(pineapple);
        Picasso.with(this).load(urlsoccerball).into(soccerball);
        //Picasso.with(this).load(urltabla).into(tabla);
        Picasso.with(this).load(urltennis).into(tennis);
        Picasso.with(this).load(urltrumpet).into(trumpet);


        int[] imagesFruits = new int[] {R.drawable.apple, R.drawable.pineapple};
        int[] imagesInstruments = new int[] {R.drawable.harmonium, R.drawable.tabla};
        int[] imagesSports = new int[] {R.drawable.tennis, R.drawable.volleyball};

        int imageIdInstruments = (int)(Math.random() * imagesInstruments.length);
        tabla.setBackgroundResource(imagesInstruments[imageIdInstruments]);

        int imageIdSports = (int)(Math.random() * imagesSports.length);
        ball.setBackgroundResource(imagesSports[imageIdSports]);

        int imageIdFruits = (int)(Math.random() * imagesFruits.length);
        pineapple.setBackgroundResource(imagesFruits[imageIdFruits]);



        fruitsb.setVisibility(View.INVISIBLE);
        sportsb.setVisibility(View.INVISIBLE);
        instrumentsb.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        s2.setVisibility(View.INVISIBLE);
        f2.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");

        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                PopUp_PlayGame p = new PopUp_PlayGame();
                p.showPopUp(Abstraction.this,description);
            }
        }.start();


        grapes.setOnTouchListener(onTouchListener());
        guitar.setOnTouchListener(onTouchListener());
        tabla.setOnTouchListener(onTouchListener());
        pineapple.setOnTouchListener(onTouchListener());
        tennis.setOnTouchListener(onTouchListener());
        soccerball.setOnTouchListener(onTouchListener());
        ball.setOnTouchListener(onTouchListener());
        trumpet.setOnTouchListener(onTouchListener());
        avocado.setOnTouchListener(onTouchListener());


    }
    public View.OnTouchListener onTouchListener()
    {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x=(int)event.getRawX();
                final int y=(int)event.getRawY();
//                Log.d(" "+v.getId(),"here at "+x+"  "+y);

                if(x>=400 && x<=760 && y>=1710 && y<=2200)
                {
                    ((ViewGroup) v.getParent()).removeView(v);
                    countofdroppeditems++;
                    if(v==trumpet || v==guitar || v==tabla)
                    {
                        instrumentscore++;
                    }
                    instrumentsbucket.setVisibility(View.INVISIBLE);
                    i1.setVisibility(View.INVISIBLE);
                    instrumentsb.setVisibility(View.VISIBLE);
                    i2.setVisibility(View.VISIBLE);

                    seconds=1;
                    final Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(seconds==1)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);


                            }
                            else if(seconds>0)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);
                            }
                            else
                            {
                                instrumentsbucket.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.VISIBLE);
                                instrumentsb.setVisibility(View.INVISIBLE);
                                i2.setVisibility(View.INVISIBLE);
                                if(countofdroppeditems==9)
                                {
                                    seconds=1;
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
                                                scoreAndGo();
                                            }
                                        }
                                    });


                                }
                            }
                        }
                    });
                }
                if(x>=50 && x<=384 && y>=1710 && y<=2200)
                {
                    ((ViewGroup) v.getParent()).removeView(v);
                    if(v==ball || v==tennis || v==soccerball)
                    {
                        sportscore++;
                    }
                    countofdroppeditems++;
                    sportsbucket.setVisibility(View.INVISIBLE);
                    s1.setVisibility(View.INVISIBLE);
                    sportsb.setVisibility(View.VISIBLE);
                    s2.setVisibility(View.VISIBLE);
                    seconds=1;
                    final Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(seconds==1)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);

                            }
                            else if(seconds>0)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);
                            }
                            else
                            {
                                sportsbucket.setVisibility(View.VISIBLE);
                                s1.setVisibility(View.VISIBLE);
                                sportsb.setVisibility(View.INVISIBLE);
                                s2.setVisibility(View.INVISIBLE);
                                if(countofdroppeditems==9)
                                {
                                    seconds=1;
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
                                                scoreAndGo();
                                            }
                                        }
                                    });


                                }
                            }
                        }
                    });
                }
                if(x>=771 && x<=1020 && y>=1710 && y<=2200)
                {
                    ((ViewGroup) v.getParent()).removeView(v);

                    if(v==grapes || v==avocado || v==pineapple)
                    {
                        fruitscore++;
                    }
                    countofdroppeditems++;
                    fruitsbucket.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    fruitsb.setVisibility(View.VISIBLE);
                    f2.setVisibility(View.VISIBLE);
                    seconds=1;
                    final Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(seconds==1)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);


                            }
                            else if(seconds>0)
                            {
                                seconds=seconds-1;
                                handler.postDelayed(this,300);
                            }
                            else
                            {
                                fruitsbucket.setVisibility(View.VISIBLE);
                                f1.setVisibility(View.VISIBLE);
                                fruitsb.setVisibility(View.INVISIBLE);
                                f2.setVisibility(View.INVISIBLE);
                                if(countofdroppeditems==9)
                                {
                                    seconds=1;
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
                                                scoreAndGo();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                switch(event.getAction() & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lparams=(RelativeLayout.LayoutParams)v.getLayoutParams();
                        xdelta=x-lparams.leftMargin;
                        ydelta=y-lparams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)v.getLayoutParams();
                        layoutParams.leftMargin=x-xdelta;
                        layoutParams.topMargin=y-ydelta;
                        layoutParams.rightMargin=0;layoutParams.bottomMargin=0;
                        v.setLayoutParams(layoutParams);
                        break;
                }
                return true;
            }
        };
    }
    protected  void onPause()   //pause music before switching to next activity
    {
        super.onPause();
        mysong.release();
        finish();
    }
    public void scoreAndGo()
    {
        countofdroppeditems=8;
        if(instrumentscore!=3) totalscore--;
        if(sportscore!=3)totalscore--;
        if(fruitscore!=3)totalscore--;
        Log.d("sport",""+sportscore);
        Log.d("instrument",""+instrumentscore);
        Log.d("fruit",""+fruitscore);

        Log.d("total",""+totalscore);

        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("abstraction").setValue(totalscore);



        seconds=1;
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds==1)
                {
                    seconds=seconds-1;
                    handler.postDelayed(this,1000);
                }
                else
                {
                    Popup_aftergame panel = new Popup_aftergame();
                    panel.showPopUp(Abstraction.this, stage_name);
                    //Intent i=new Intent(getApplicationContext(), Calculation_Intro.class);
                    //startActivity(i);
                }
            }
        });

    }
}