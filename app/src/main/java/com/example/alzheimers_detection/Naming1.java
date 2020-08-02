package com.example.alzheimers_detection;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

public class Naming1 extends AppCompatActivity {

    // Button button;

    ProgressBar camel ;
    float x_pos;
    int user_index;
    int score=1;
    String[] user_input={" "," "," "," "," "};
    String[] correct_sequence={"c","a","m","e","l"};
    String[] Score={"zero","one","two","three"};
    OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naming1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        camel = findViewById(R.id.progressBar11);
        final String stage_name="Naming";
        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Naming1),stage_name);

        user_index=-1;
        x_pos=110;


       /* Glide.with(this)
                .load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar11.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar11.setVisibility(View.GONE);
                return false;
            }
        }).into(gify);*/

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
    public void click_cam(View button) {
        user_index++;
        make_invisible(user_index+1);
        switch (button.getId())
        {
            case R.id.camel_c:
                user_input[user_index]="c";
                break;
            case R.id.camel_a:
                user_input[user_index]="a";
                break;
            case R.id.camel_m:
                user_input[user_index]="m";
                break;
            case R.id.camel_e:
                user_input[user_index]="e";
                break;
            case R.id.camel_l:
                user_input[user_index]="l";
                break;

        }
        animate(x_pos, (Button) button);
        x_pos = x_pos +180;
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

        v.setVisibility(View.GONE);
    }



    public void nextActivity(View view) {
        check();
        Intent intent = new Intent(this, Naming3.class);

        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("score");
       // float prevScore=Float.parseFloat(stringVariableName);
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
    public void resetActivity(View view) {

        Intent intent = new Intent(this, Naming1.class);
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
