package com.example.alzheimers_detection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VI_Attention extends AppCompatActivity {
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    int check=0,error=0,score=0;


    String seq[]={"C","R","A","G","I","A","O","Q","U","P","A","L","Q"};
    int tap[]={'0','0','0','0','0','0','0','0','0','0','0','0','0'};
    int index=0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(index>=seq.length){
                    Speak tts = new Speak(VI_Attention.this);
                    tts.speakOut(getString(R.string.after_Attention));
                    check_();


                    mAuth = FirebaseAuth.getInstance();
                    fuser = mAuth.getCurrentUser();
                    uid=fuser.getUid();
                    dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                    dbUsers.child("attention").setValue(score);


                    Intent myIntent=new Intent(getApplicationContext(),VI_Calculation_intro.class);
                    startActivity(myIntent);
                    VI_Attention.this.finish();
                    onPause();
                }
                else
                {
                    final TextView text = findViewById(R.id.textViewletter);
                    text.setText(seq[index]);
                    call_letter(index);
                    final Button button = findViewById(R.id.button_check);
                    button.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(final View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    tap[index]=1;
                                    break;
                                }
                                case MotionEvent.ACTION_UP: {
                                    v.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast toast=Toast. makeText(getApplicationContext(),"Added",Toast. LENGTH_SHORT);
                                            toast. show();
                                        }
                                    }, 10);
                                    break;
                                }
                            }

                            return false;
                        }
                    });
                    index++;
                }
            } handler.postDelayed(this, 2000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_attention);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
    void call_letter(int i){
        Speak tts = new Speak(this.getApplicationContext());
        tts.speakOut(seq[i]);
    }
    void check_()
    {
        while(check<seq.length)
        {
            if((seq[check]=="A"&&tap[check]!=1)||(seq[check]!="A"&&tap[check]==1))
                error++;
            check++;
        }


        if(error<=1)
        {
            score=3;
            Toast toast=Toast. makeText(getApplicationContext(),"less tha equal to one error",Toast. LENGTH_SHORT);
            toast. show();
        }
        else if(error==2)
        {
            score=1;
            Toast toast=Toast. makeText(getApplicationContext(),"2 errors",Toast. LENGTH_SHORT);
            toast. show();
        }

    }
}