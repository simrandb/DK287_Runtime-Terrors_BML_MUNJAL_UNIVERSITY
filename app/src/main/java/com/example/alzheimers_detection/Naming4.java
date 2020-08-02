package com.example.alzheimers_detection;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

public class Naming4 extends AppCompatActivity {
    ProgressBar tort;
    float x_pos;
    int user_index;
    int score=1;
    String description;
    String[] user_input={" "," "," "," "," "," "," "," "};
    String[] correct_sequence={"t","o","r","t","o","i","s","e"};
    String[] Score={"zero","one","two","three"};
    OnSwipeTouchListener onSwipeTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naming4);
        description = "\nYou are in Jungle Safari and you spot four animals.\n\n\n" +
                "Tap on the jumbled alphabets to arrange them in order to form appropriate name of the animal.";


        final String stage_name="Naming";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Naming4),stage_name);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        tort = findViewById(R.id.progressBar14);
        user_index=-1;
        x_pos=40;

        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");

            new CountDownTimer(1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    PopUp_PlayGame p = new PopUp_PlayGame();
                    p.showPopUp(Naming4.this,description);
                }
            }.start();

        /* Glide.with(this)
                .load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar14.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar14.setVisibility(View.GONE);
                return false;
            }
        }).into(gify);*/
    }
    public void nextActivity(View view) {
        check();
        Intent i=new Intent(getApplicationContext(), Naming2.class);
        i.putExtra("score",Score[score]);
        startActivity(i);
    }

    private void check() {
        for(int i=0;i<correct_sequence.length;i++)
        {
            if(!user_input[i].contains(correct_sequence[i]))
                score=0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void animate(float x_position, Button b)
    {
            Path path = new Path();
            path.moveTo(x_position,320);
            ObjectAnimator anim = ObjectAnimator.ofFloat(b,View.X,View.Y ,path);
            anim.setDuration(100);
            anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void click_tort(View button) {
        user_index++;
        make_invisible(user_index+1);
        switch (button.getId())
        {
            case R.id.tort_t:
                user_input[user_index]="t";
                break;
            case R.id.tort_t2:
                user_input[user_index]="t";
                break;
            case R.id.tort_o:
                user_input[user_index]="o";
                break;
            case R.id.tort_o2:
                user_input[user_index]="o";
                break;
            case R.id.tort_r:
                user_input[user_index]="r";
                break;
            case R.id.tort_i:
                user_input[user_index]="i";
                break;
            case R.id.tort_s:
                user_input[user_index]="s";
                break;
            case R.id.tort_e:
                user_input[user_index]="e";
                break;
        }
        animate(x_pos, (Button) button);
        x_pos = x_pos +125;
    }

    private void make_invisible(int dash_number)
    {
        View v = null;
        if(dash_number==1)
            v=findViewById(R.id.Dash1);
        if(dash_number==2)
            v=findViewById(R.id.Dash2);
        if(dash_number==3)
            v=findViewById(R.id.Dash3);
        if(dash_number==4)
            v=findViewById(R.id.Dash4);
        if(dash_number==5)
            v=findViewById(R.id.Dash5);
        if(dash_number==6)
            v=findViewById(R.id.Dash6);
        if(dash_number==7)
            v=findViewById(R.id.Dash7);
        if(dash_number==8)
            v=findViewById(R.id.Dash8);
        v.setVisibility(View.GONE);
    }

    public void resetActivity(View view) {
        Intent intent = new Intent(this, Naming4.class);
        check();
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("score");
        int prevScore=0;
        for(int i=0;i<Score.length;i++)
        {
            if(stringVariableName.contains(Score[i]))
            {
                prevScore=i;
                break;
            }
        }
        intent.putExtra("score",(Score[prevScore+score]));
        startActivity(intent);
    }
}